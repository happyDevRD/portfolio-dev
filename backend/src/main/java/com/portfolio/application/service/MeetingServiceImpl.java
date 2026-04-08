package com.portfolio.application.service;

import com.portfolio.core.domain.exception.MeetingConflictException;
import com.portfolio.core.domain.exception.MeetingNotFoundException;
import com.portfolio.core.domain.model.Meeting;
import com.portfolio.core.domain.port.CalendarBookingPort;
import com.portfolio.core.domain.repository.MeetingRepository;
import com.portfolio.core.usecase.MeetingService;
import com.portfolio.infrastructure.config.MeetingSchedulingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private static final DateTimeFormatter HH_MM = DateTimeFormatter.ofPattern("HH:mm");
    private final Map<String, AvailabilityCacheEntry> availabilityCache = new ConcurrentHashMap<>();

    private final MeetingRepository meetingRepository;
    private final CalendarBookingPort calendarBookingPort;
    private final MeetingSchedulingProperties scheduling;
    private final Clock clock;

    @Override
    @Transactional
    public Meeting scheduleMeeting(Meeting meeting) {
        validateSchedulingRules(meeting);

        if (!calendarBookingPort.isSlotFree(meeting.getStartAt(), meeting.getEndAt())) {
            throw new MeetingConflictException("Ese horario ya no está disponible. Elige otro intervalo.");
        }

        String eventId = calendarBookingPort.createEvent(meeting);
        meeting.setGoogleEventId(eventId);
        Meeting saved = meetingRepository.save(meeting);
        invalidateAvailabilityCache();
        return saved;
    }

    @Override
    @Transactional
    public void cancelMeeting(Long meetingId) {
        Meeting existing = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingNotFoundException(meetingId));

        String googleEventId = existing.getGoogleEventId();
        if (googleEventId != null && !googleEventId.isBlank()) {
            calendarBookingPort.deleteEvent(googleEventId);
        }
        meetingRepository.deleteById(meetingId);
        invalidateAvailabilityCache();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAvailableSlots(LocalDate date, int durationMinutes) {
        int slotMinutes = requireValidSlotMinutes();
        if (durationMinutes != slotMinutes) {
            throw new IllegalArgumentException("La duración debe ser de " + slotMinutes + " minutos.");
        }
        long cacheTtlMs = Math.max(0, scheduling.getAvailabilityCacheSeconds()) * 1000L;
        String cacheKey = buildAvailabilityCacheKey(date, durationMinutes);
        if (cacheTtlMs > 0) {
            AvailabilityCacheEntry cached = availabilityCache.get(cacheKey);
            if (cached != null && !cached.isExpired(clock.instant(), cacheTtlMs)) {
                return cached.slots();
            }
        }

        ZoneId zone = ZoneId.of(scheduling.getTimezone());
        Instant now = clock.instant();
        ZonedDateTime nowLocal = now.atZone(zone);
        LocalDate minDate = nowLocal.toLocalDate();
        LocalDate maxDate = minDate.plusDays(scheduling.getMaxDaysAhead());
        if (date.isBefore(minDate) || date.isAfter(maxDate)) {
            return List.of();
        }

        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return List.of();
        }

        List<LocalTime> slotStarts = buildDailySlotStarts();
        if (slotStarts.isEmpty()) {
            return List.of();
        }

        Instant minStart = now.plusSeconds(scheduling.getMinNoticeHours() * 3600L);
        List<String> available = new ArrayList<>();
        for (LocalTime start : slotStarts) {
            ZonedDateTime localStart = ZonedDateTime.of(date, start, zone);
            Instant startInstant = localStart.toInstant();
            if (!startInstant.isAfter(minStart)) {
                continue;
            }
            Instant endInstant = startInstant.plus(Duration.ofMinutes(durationMinutes));
            if (calendarBookingPort.isSlotFree(startInstant, endInstant)) {
                available.add(start.format(HH_MM));
            }
        }
        List<String> result = List.copyOf(available);
        if (cacheTtlMs > 0) {
            availabilityCache.put(cacheKey, new AvailabilityCacheEntry(clock.instant(), result));
        }
        return result;
    }

    private void validateSchedulingRules(Meeting meeting) {
        if (meeting.getStartAt() == null || meeting.getEndAt() == null) {
            throw new IllegalArgumentException("La fecha de inicio y fin son obligatorias.");
        }
        if (!meeting.getEndAt().isAfter(meeting.getStartAt())) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior al inicio.");
        }

        ZoneId zone = ZoneId.of(scheduling.getTimezone());
        Instant now = clock.instant();
        if (!meeting.getStartAt().isAfter(now.plusSeconds(scheduling.getMinNoticeHours() * 3600L))) {
            throw new IllegalArgumentException(
                    "Debes agendar con al menos " + scheduling.getMinNoticeHours() + " horas de anticipación.");
        }

        Instant maxHorizon = now.plusSeconds(scheduling.getMaxDaysAhead() * 24L * 3600L);
        if (meeting.getStartAt().isAfter(maxHorizon)) {
            throw new IllegalArgumentException(
                    "No se pueden agendar reuniones a más de " + scheduling.getMaxDaysAhead() + " días.");
        }

        ZonedDateTime startLocal = meeting.getStartAt().atZone(zone);
        ZonedDateTime endLocal = meeting.getEndAt().atZone(zone);

        DayOfWeek day = startLocal.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Las reuniones solo están disponibles de lunes a viernes.");
        }
        if (!startLocal.toLocalDate().equals(endLocal.toLocalDate())) {
            throw new IllegalArgumentException("La reunión debe comenzar y terminar el mismo día.");
        }

        LocalTime s = startLocal.toLocalTime();
        int slotMinutes = requireValidSlotMinutes();
        if (startLocal.getSecond() != 0 || startLocal.getNano() != 0) {
            throw new IllegalArgumentException(
                    "La hora de inicio debe estar segmentada en bloques de " + slotMinutes + " minutos.");
        }
        List<LocalTime> slotStarts = buildDailySlotStarts();
        if (!slotStarts.contains(s)) {
            throw new IllegalArgumentException(
                    "Horario no válido. Usa bloques de 1 hora entre 09:00-12:00 y 14:00-17:00.");
        }

        long durationMinutes = Duration.between(meeting.getStartAt(), meeting.getEndAt()).toMinutes();
        if (durationMinutes != slotMinutes) {
            throw new IllegalArgumentException(
                    "La duración debe ser de " + slotMinutes + " minutos.");
        }
    }

    private int requireValidSlotMinutes() {
        int slotMinutes = scheduling.getSlotMinutes();
        if (slotMinutes <= 0) {
            throw new IllegalArgumentException("La configuración de agenda es inválida: slot-minutes debe ser mayor que 0.");
        }
        return slotMinutes;
    }

    private List<LocalTime> buildDailySlotStarts() {
        int slotMinutes = requireValidSlotMinutes();
        List<LocalTime> starts = new ArrayList<>();
        appendSegmentSlots(starts, scheduling.getMorningHourStart(), scheduling.getMorningHourEnd(), slotMinutes);
        appendSegmentSlots(starts, scheduling.getAfternoonHourStart(), scheduling.getAfternoonHourEnd(), slotMinutes);
        return starts;
    }

    private static void appendSegmentSlots(List<LocalTime> starts, int segmentStartHour, int segmentEndHour, int slotMinutes) {
        int segmentStartMinutes = segmentStartHour * 60;
        int segmentEndMinutes = segmentEndHour * 60;
        if (segmentEndMinutes <= segmentStartMinutes) {
            return;
        }
        for (int start = segmentStartMinutes; start + slotMinutes <= segmentEndMinutes; start += slotMinutes) {
            starts.add(LocalTime.of(start / 60, start % 60));
        }
    }

    private void invalidateAvailabilityCache() {
        availabilityCache.clear();
    }

    private static String buildAvailabilityCacheKey(LocalDate date, int durationMinutes) {
        return date + "|" + durationMinutes;
    }

    private record AvailabilityCacheEntry(Instant createdAt, List<String> slots) {
        boolean isExpired(Instant now, long ttlMs) {
            return Duration.between(createdAt, now).toMillis() >= ttlMs;
        }
    }
}


