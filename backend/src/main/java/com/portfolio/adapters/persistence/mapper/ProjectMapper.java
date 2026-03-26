package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ProjectEntity;
import com.portfolio.core.domain.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
                .githubUrl(entity.getGithubUrl())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .tags(copyList(entity.getTags()))
                .challenge(entity.getChallenge())
                .solution(entity.getSolution())
                .features(copyList(entity.getFeatures()))
                .build();
    }

    private static List<String> copyList(List<String> from) {
        return from == null ? null : new ArrayList<>(from);
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
                .githubUrl(domain.getGithubUrl())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .tags(domain.getTags())
                .challenge(domain.getChallenge())
                .solution(domain.getSolution())
                .features(domain.getFeatures())
                .build();
    }
}
