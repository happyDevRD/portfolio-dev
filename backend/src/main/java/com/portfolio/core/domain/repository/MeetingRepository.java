package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Meeting;

public interface MeetingRepository {
    Meeting save(Meeting meeting);
}
