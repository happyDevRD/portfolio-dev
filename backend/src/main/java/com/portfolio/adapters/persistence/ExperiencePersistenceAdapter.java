package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.ExperienceMapper;
import com.portfolio.adapters.persistence.repository.JpaExperienceRepository;
import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.domain.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExperiencePersistenceAdapter implements ExperienceRepository {
    private final JpaExperienceRepository jpaExperienceRepository;
    private final ExperienceMapper experienceMapper;

    @Override
    public List<Experience> findAll() {
        return jpaExperienceRepository.findAll().stream()
                .map(experienceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Experience> findById(Long id) {
        if (id == null)
            return Optional.empty();
        return jpaExperienceRepository.findById(id)
                .map(experienceMapper::toDomain);
    }

    @Override
    public Experience save(Experience experience) {
        var entity = experienceMapper.toEntity(experience);
        if (entity == null) {
            throw new IllegalArgumentException("Experience cannot be null");
        }
        var savedEntity = jpaExperienceRepository.save(entity);
        return experienceMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            jpaExperienceRepository.deleteById(id);
        }
    }
}
