package com.portfolio.core.usecase;

import com.portfolio.core.domain.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();

    Optional<Project> getProjectById(Long id);

    Project createProject(Project project);

    void deleteProject(Long id);
}
