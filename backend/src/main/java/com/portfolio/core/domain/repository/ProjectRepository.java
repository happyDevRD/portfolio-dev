package com.portfolio.core.domain.repository;

import com.portfolio.core.domain.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    Project save(Project project);

    void deleteById(Long id);
}
