package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.MeetingMapper;
import com.portfolio.adapters.persistence.repository.MeetingJpaRepository;
import com.portfolio.core.domain.model.Meeting;
import com.portfolio.core.domain.repository.MeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MeetingPersistenceAdapter implements MeetingRepository {

    private final MeetingJpaRepository meetingJpaRepository;
    private final MeetingMapper meetingMapper;

    @Override
    public Meeting save(Meeting meeting) {
        var entity = meetingMapper.toEntity(meeting);
        if (entity == null) {
            throw new IllegalArgumentException("Meeting cannot be null");
        }
        var saved = meetingJpaRepository.save(entity);
        return meetingMapper.toDomain(saved);
    }
}
