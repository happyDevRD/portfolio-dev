package com.portfolio.adapters.calendar;

import com.portfolio.core.domain.exception.CalendarIntegrationException;
import com.portfolio.core.domain.model.Meeting;
import com.portfolio.core.domain.port.CalendarBookingPort;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Activo cuando {@code app.google.calendar.enabled=false} (por defecto en local).
 */
@Component
@ConditionalOnProperty(prefix = "app.google.calendar", name = "enabled", havingValue = "false", matchIfMissing = true)
public class DisabledCalendarBookingAdapter implements CalendarBookingPort {

    private static final String MSG =
            "La integración con Google Calendar no está habilitada. Configura app.google.calendar en el servidor.";

    @Override
    public boolean isSlotFree(Instant start, Instant end) {
        throw new CalendarIntegrationException(MSG);
    }

    @Override
    public String createEvent(Meeting meeting) {
        throw new CalendarIntegrationException(MSG);
    }
}
