package com.portfolio.core.domain.port;

import com.portfolio.core.domain.model.Meeting;

import java.time.Instant;

/**
 * Puerto de salida para crear eventos y consultar disponibilidad en el calendario externo.
 */
public interface CalendarBookingPort {

    /**
     * Indica si el intervalo [start, end) está libre de conflictos en el calendario configurado.
     */
    boolean isSlotFree(Instant start, Instant end);

    /**
     * Crea el evento y devuelve el identificador en Google Calendar.
     */
    String createEvent(Meeting meeting);
}
