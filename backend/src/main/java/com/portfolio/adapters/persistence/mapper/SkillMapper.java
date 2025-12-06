package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.SkillEntity;
import com.portfolio.core.domain.model.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
    public Skill toDomain(SkillEntity entity) {
        if (entity == null)
            return null;
        return Skill.builder()
                .id(entity.getId())
                .name(entity.getName())
                .proficiency(entity.getProficiency())
                .iconUrl(entity.getIconUrl())
                .category(entity.getCategory())
                .build();
    }

    public SkillEntity toEntity(Skill domain) {
        if (domain == null)
            return null;
        return SkillEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .proficiency(domain.getProficiency())
                .iconUrl(domain.getIconUrl())
                .category(domain.getCategory())
                .build();
    }
}
