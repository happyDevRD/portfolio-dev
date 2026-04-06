package com.portfolio.core.domain.exception;

/**
 * El intervalo solicitado no está disponible en el calendario.
 */
public class MeetingConflictException extends RuntimeException {

    public MeetingConflictException(String message) {
        super(message);
    }
}
