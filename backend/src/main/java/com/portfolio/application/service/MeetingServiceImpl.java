package com.portfolio.application.service;

import com.portfolio.core.domain.exception.MeetingConflictException;
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
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {

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
        return meetingRepository.save(meeting);
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

        LocalTime open = LocalTime.of(scheduling.getBusinessHourStart(), 0);
        LocalTime close = LocalTime.of(scheduling.getBusinessHourEnd(), 0);
        LocalTime s = startLocal.toLocalTime();
        LocalTime e = endLocal.toLocalTime();
        if (s.isBefore(open) || e.isAfter(close) || !e.isAfter(s)) {
            throw new IllegalArgumentException(
                    "El horario debe estar entre las "
                            + scheduling.getBusinessHourStart()
                            + ":00 y las "
                            + scheduling.getBusinessHourEnd()
                            + ":00 ("
                            + scheduling.getTimezone()
                            + ").");
        }
    }
}
