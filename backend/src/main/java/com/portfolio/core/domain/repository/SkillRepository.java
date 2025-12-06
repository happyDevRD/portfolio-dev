package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Skill;
import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    List<Skill> findAll();

    Optional<Skill> findById(Long id);

    Skill save(Skill skill);

    void deleteById(Long id);
}
