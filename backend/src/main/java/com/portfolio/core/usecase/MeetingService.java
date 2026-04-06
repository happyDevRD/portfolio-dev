package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Meeting;

public interface MeetingService {
    Meeting scheduleMeeting(Meeting meeting);
}
