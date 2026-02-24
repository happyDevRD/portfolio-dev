package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Skill;
import java.util.List;
import java.util.Optional;

public interface SkillService {
    List<Skill> getAllSkills();

    Optional<Skill> getSkillById(Long id);

    Skill createSkill(Skill skill);

    Optional<Skill> updateSkill(Long id, Skill skill);

    void deleteSkill(Long id);
}
