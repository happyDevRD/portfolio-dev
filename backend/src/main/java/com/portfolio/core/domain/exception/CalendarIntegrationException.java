package com.portfolio.core.domain.exception;

/**
 * Fallo al integrar con Google Calendar o servicio deshabilitado.
 */
public class CalendarIntegrationException extends RuntimeException {

    public CalendarIntegrationException(String message) {
        super(message);
    }

    public CalendarIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
