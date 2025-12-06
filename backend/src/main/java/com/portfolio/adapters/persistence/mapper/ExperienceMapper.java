package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ExperienceEntity;
import com.portfolio.core.domain.model.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {
    public Experience toDomain(ExperienceEntity entity) {
        if (entity == null)
            return null;
        return Experience.builder()
                .id(entity.getId())
                .company(entity.getCompany())
                .role(entity.getRole())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .currentInfo(entity.isCurrentInfo())
                .build();
    }

    public ExperienceEntity toEntity(Experience domain) {
        if (domain == null)
            return null;
        return ExperienceEntity.builder()
                .id(domain.getId())
                .company(domain.getCompany())
                .role(domain.getRole())
                .description(domain.getDescription())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .currentInfo(domain.isCurrentInfo())
                .build();
    }
}
