package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.ProjectMapper;
import com.portfolio.adapters.persistence.repository.JpaProjectRepository;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements ProjectRepository {
    private final JpaProjectRepository jpaProjectRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Project> findAll() {
        return jpaProjectRepository.findAll().stream()
                .map(projectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findById(Long id) {
        if (id == null)
            return Optional.empty();
        return jpaProjectRepository.findById(id)
                .map(projectMapper::toDomain);
    }

    @Override
    @Transactional
    public Project save(Project project) {
        var entity = projectMapper.toEntity(project);
        if (entity == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        var savedEntity = jpaProjectRepository.save(entity);
        return projectMapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id != null) {
            jpaProjectRepository.deleteById(id);
        }
    }
}
