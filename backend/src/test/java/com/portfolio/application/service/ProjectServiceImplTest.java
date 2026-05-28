package com.portfolio.application.service;

import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void getAllProjects_returnsAllProjects() {
        List<Project> projects = List.of(
                Project.builder().id(1L).title("Alpha").build(),
                Project.builder().id(2L).title("Beta").build()
        );
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Project::getTitle).containsExactly("Alpha", "Beta");
    }

    @Test
    void getProjectById_returnsProject_whenFound() {
        Project project = Project.builder().id(1L).title("Test").build();
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<Project> result = projectService.getProjectById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    @Test
    void getProjectById_returnsEmpty_whenNotFound() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThat(projectService.getProjectById(99L)).isEmpty();
    }

    @Test
    void createProject_savesAndReturnsProject() {
        Project input = Project.builder().title("New").build();
        Project saved = Project.builder().id(1L).title("New").build();
        when(projectRepository.save(input)).thenReturn(saved);

        Project result = projectService.createProject(input);

        assertThat(result.getId()).isEqualTo(1L);
        verify(projectRepository).save(input);
    }

    @Test
    void updateProject_preservesIdFromExistingRecord() {
        Project existing = Project.builder().id(5L).title("Old Title").build();
        Project incoming = Project.builder().title("New Title").build();
        Project saved = Project.builder().id(5L).title("New Title").build();

        when(projectRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(Project.class))).thenReturn(saved);

        Optional<Project> result = projectService.updateProject(5L, incoming);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(5L);
        assertThat(result.get().getTitle()).isEqualTo("New Title");
        // El id viene del registro existente, no del body del request
        verify(projectRepository).save(argThat(p -> Long.valueOf(5L).equals(p.getId())));
    }

    @Test
    void updateProject_returnsEmpty_whenProjectNotFound() {
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Project> result = projectService.updateProject(99L, Project.builder().build());

        assertThat(result).isEmpty();
        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteProject_delegatesToRepository() {
        projectService.deleteProject(1L);

        verify(projectRepository).deleteById(1L);
    }
}
