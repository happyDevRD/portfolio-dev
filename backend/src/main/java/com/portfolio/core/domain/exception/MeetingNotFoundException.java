package com.portfolio.core.domain.exception;

public class MeetingNotFoundException extends RuntimeException {

    public MeetingNotFoundException(Long meetingId) {
        super("No existe una reunión con id=" + meetingId + ".");
    }
}
