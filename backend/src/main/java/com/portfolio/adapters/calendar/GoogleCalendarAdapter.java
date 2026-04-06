package com.portfolio.adapters.calendar;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.FreeBusyRequest;
import com.google.api.services.calendar.model.FreeBusyRequestItem;
import com.google.api.services.calendar.model.FreeBusyResponse;
import com.google.api.services.calendar.model.TimePeriod;
import com.portfolio.core.domain.exception.CalendarIntegrationException;
import com.portfolio.core.domain.model.Meeting;
import com.portfolio.core.domain.model.MeetingType;
import com.portfolio.core.domain.port.CalendarBookingPort;
import com.portfolio.infrastructure.config.GoogleCalendarProperties;
import com.portfolio.infrastructure.config.MeetingSchedulingProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class GoogleCalendarAdapter implements CalendarBookingPort {

    private final Calendar calendar;
    private final GoogleCalendarProperties googleProps;
    private final MeetingSchedulingProperties scheduling;

    @Override
    public boolean isSlotFree(Instant start, Instant end) {
        try {
            FreeBusyRequest req = new FreeBusyRequest();
            req.setTimeMin(new DateTime(Date.from(start)));
            req.setTimeMax(new DateTime(Date.from(end)));
            req.setItems(List.of(new FreeBusyRequestItem().setId(googleProps.getCalendarId())));

            FreeBusyResponse response = calendar.freebusy().query(req).execute();
            var calMap = response.getCalendars();
            if (calMap == null) {
                return true;
            }
            var cal = calMap.get(googleProps.getCalendarId());
            if (cal == null || cal.getBusy() == null || cal.getBusy().isEmpty()) {
                return true;
            }
            for (TimePeriod period : cal.getBusy()) {
                Instant bs = Instant.ofEpochMilli(period.getStart().getValue());
                Instant be = Instant.ofEpochMilli(period.getEnd().getValue());
                if (overlaps(start, end, bs, be)) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new CalendarIntegrationException("No se pudo consultar la disponibilidad en Google Calendar.", e);
        }
    }

    private static boolean overlaps(Instant aStart, Instant aEnd, Instant bStart, Instant bEnd) {
        return aStart.isBefore(bEnd) && bStart.isBefore(aEnd);
    }

    @Override
    public String createEvent(Meeting meeting) {
        try {
            String tz = scheduling.getTimezone();
            Event event = new Event()
                    .setSummary(buildSummary(meeting))
                    .setDescription(buildDescription(meeting))
                    .setAttendees(List.of(new EventAttendee().setEmail(meeting.getRequesterEmail())));

            EventDateTime start = new EventDateTime()
                    .setDateTime(new DateTime(Date.from(meeting.getStartAt())))
                    .setTimeZone(tz);
            EventDateTime end = new EventDateTime()
                    .setDateTime(new DateTime(Date.from(meeting.getEndAt())))
                    .setTimeZone(tz);
            event.setStart(start);
            event.setEnd(end);

            Event created =
                    calendar.events().insert(googleProps.getCalendarId(), event).setSendUpdates("all").execute();
            if (created.getId() == null) {
                throw new CalendarIntegrationException("Google Calendar no devolvió un id de evento.");
            }
            return created.getId();
        } catch (IOException e) {
            throw new CalendarIntegrationException("No se pudo crear el evento en Google Calendar.", e);
        }
    }

    private static String buildSummary(Meeting meeting) {
        return "[" + label(meeting.getMeetingType()) + "] " + meeting.getRequesterName();
    }

    private static String buildDescription(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tipo: ").append(label(meeting.getMeetingType())).append("\n");
        sb.append("Solicitante: ").append(meeting.getRequesterName()).append(" <").append(meeting.getRequesterEmail()).append(">\n");
        if (meeting.getNotes() != null && !meeting.getNotes().isBlank()) {
            sb.append("\nNotas:\n").append(meeting.getNotes().trim());
        }
        return sb.toString();
    }

    private static String label(MeetingType type) {
        if (type == null) {
            return "Reunión";
        }
        return switch (type) {
            case TECH_CONSULTING -> "Consultoría técnica";
            case JOB_INTERVIEW -> "Entrevista laboral";
            case COLLABORATION -> "Colaboración";
            case OTHER -> "Otro";
        };
    }
}
