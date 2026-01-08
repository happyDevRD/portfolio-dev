package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ProjectEntity;
import com.portfolio.core.domain.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public Project toDomain(ProjectEntity entity) {
        if (entity == null)
            return null;
        return Project.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getImageUrl())
                .projectUrl(entity.getProjectUrl())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .tags(entity.getTags())
                .challenge(entity.getChallenge())
                .solution(entity.getSolution())
                .features(entity.getFeatures())
                .build();
    }

    public ProjectEntity toEntity(Project domain) {
        if (domain == null)
            return null;
        return ProjectEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .imageUrl(domain.getImageUrl())
                .projectUrl(domain.getProjectUrl())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .tags(domain.getTags())
                .challenge(domain.getChallenge())
                .solution(domain.getSolution())
                .features(domain.getFeatures())
                .build();
    }
}
