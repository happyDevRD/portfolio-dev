package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Meeting;

import java.util.Optional;

public interface MeetingRepository {
    Meeting save(Meeting meeting);

    Optional<Meeting> findById(Long id);

    void deleteById(Long id);
}
