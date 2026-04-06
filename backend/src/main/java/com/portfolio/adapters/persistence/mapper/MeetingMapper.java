package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.MeetingEntity;
import com.portfolio.core.domain.model.Meeting;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {

    public Meeting toDomain(MeetingEntity entity) {
        if (entity == null) {
            return null;
        }
        return Meeting.builder()
                .id(entity.getId())
                .meetingType(entity.getMeetingType())
                .startAt(entity.getStartAt())
                .endAt(entity.getEndAt())
                .requesterName(entity.getRequesterName())
                .requesterEmail(entity.getRequesterEmail())
                .notes(entity.getNotes())
                .googleEventId(entity.getGoogleEventId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public MeetingEntity toEntity(Meeting domain) {
        if (domain == null) {
            return null;
        }
        return MeetingEntity.builder()
                .id(domain.getId())
                .meetingType(domain.getMeetingType())
                .startAt(domain.getStartAt())
                .endAt(domain.getEndAt())
                .requesterName(domain.getRequesterName())
                .requesterEmail(domain.getRequesterEmail())
                .notes(domain.getNotes())
                .googleEventId(domain.getGoogleEventId())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
