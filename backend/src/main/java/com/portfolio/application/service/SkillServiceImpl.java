package com.portfolio.application.service;

import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.domain.repository.SkillRepository;
import com.portfolio.core.usecase.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    @Transactional
    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    @Transactional
    public Optional<Skill> updateSkill(Long id, Skill updated) {
        return skillRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return skillRepository.save(updated);
        });
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
