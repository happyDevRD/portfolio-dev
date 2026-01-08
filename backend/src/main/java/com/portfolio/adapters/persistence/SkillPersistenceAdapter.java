package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.SkillMapper;
import com.portfolio.adapters.persistence.repository.JpaSkillRepository;
import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.domain.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SkillPersistenceAdapter implements SkillRepository {
    private final JpaSkillRepository jpaSkillRepository;
    private final SkillMapper skillMapper;

    @Override
    public List<Skill> findAll() {
        return jpaSkillRepository.findAll().stream()
                .map(skillMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Skill> findById(Long id) {
        if (id == null)
            return Optional.empty();
        return jpaSkillRepository.findById(id)
                .map(skillMapper::toDomain);
    }

    @Override
    public Skill save(Skill skill) {
        var entity = skillMapper.toEntity(skill);
        if (entity == null) {
            throw new IllegalArgumentException("Skill cannot be null");
        }
        var savedEntity = jpaSkillRepository.save(entity);
        return skillMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            jpaSkillRepository.deleteById(id);
        }
    }
}
