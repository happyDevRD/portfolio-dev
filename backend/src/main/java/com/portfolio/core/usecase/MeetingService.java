package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {
    Meeting scheduleMeeting(Meeting meeting);

    void cancelMeeting(Long meetingId);

    List<String> getAvailableSlots(LocalDate date, int durationMinutes);
}
