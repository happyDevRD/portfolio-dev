package com.portfolio.infrastructure.rest.mapper;

import java.util.Collections;
import java.util.List;

import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.model.Skill;
import com.portfolio.infrastructure.rest.dto.ExperienceWriteRequest;
import com.portfolio.infrastructure.rest.dto.ProjectWriteRequest;
import com.portfolio.infrastructure.rest.dto.SkillWriteRequest;

public final class RestDtoMapper {

    private RestDtoMapper() {}

    public static Project toProject(ProjectWriteRequest r) {
        return Project.builder()
                .title(r.getTitle())
                .description(r.getDescription())
                .imageUrl(blankToNull(r.getImageUrl()))
                .projectUrl(blankToNull(r.getProjectUrl()))
                .githubUrl(blankToNull(r.getGithubUrl()))
                .startDate(r.getStartDate())
                .endDate(r.getEndDate())
                .tags(listOrEmpty(r.getTags()))
                .challenge(blankToNull(r.getChallenge()))
                .solution(blankToNull(r.getSolution()))
                .features(listOrEmpty(r.getFeatures()))
                .build();
    }

    public static Skill toSkill(SkillWriteRequest r) {
        return Skill.builder()
                .name(r.getName())
                .proficiency(r.getProficiency())
                .iconUrl(blankToNull(r.getIconUrl()))
                .category(r.getCategory())
                .build();
    }

    public static Experience toExperience(ExperienceWriteRequest r) {
        return Experience.builder()
                .company(r.getCompany())
                .role(r.getRole())
                .description(blankToNull(r.getDescription()))
                .startDate(r.getStartDate())
                .endDate(r.getEndDate())
                .currentInfo(r.isCurrentInfo())
                .highlights(listOrEmpty(r.getHighlights()))
                .build();
    }

    private static String blankToNull(String s) {
        if (s == null || s.isBlank()) {
            return null;
        }
        return s;
    }

    private static List<String> listOrEmpty(List<String> list) {
        return list == null ? Collections.emptyList() : list;
    }
}
