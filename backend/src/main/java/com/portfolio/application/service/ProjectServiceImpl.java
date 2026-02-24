package com.portfolio.application.service;

import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.repository.ProjectRepository;
import com.portfolio.core.usecase.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    @Transactional
    public Project createProject(Project project) {
        // Here we could add business logic validations
        return projectRepository.save(project);
    }

    @Override
    @Transactional
    public Optional<Project> updateProject(Long id, Project updated) {
        return projectRepository.findById(id).map(existing -> {
            updated.setId(existing.getId());
            return projectRepository.save(updated);
        });
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
