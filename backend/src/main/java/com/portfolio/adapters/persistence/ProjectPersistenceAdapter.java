package com.portfolio.adapters.persistence;

import com.portfolio.adapters.persistence.mapper.ProjectMapper;
import com.portfolio.adapters.persistence.repository.JpaProjectRepository;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements ProjectRepository {
    private final JpaProjectRepository jpaProjectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public List<Project> findAll() {
        return jpaProjectRepository.findAll().stream()
                .map(projectMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Project> findById(Long id) {
        return jpaProjectRepository.findById(id)
                .map(projectMapper::toDomain);
    }

    @Override
    public Project save(Project project) {
        var entity = projectMapper.toEntity(project);
        var savedEntity = jpaProjectRepository.save(entity);
        return projectMapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        jpaProjectRepository.deleteById(id);
    }
}
