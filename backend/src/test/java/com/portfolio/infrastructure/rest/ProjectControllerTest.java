package com.portfolio.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.usecase.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @Test
    void getAllProjects_returns200WithList() throws Exception {
        when(projectService.getAllProjects()).thenReturn(List.of(
                Project.builder().id(1L).title("Project Alpha").build(),
                Project.builder().id(2L).title("Project Beta").build()
        ));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Project Alpha"))
                .andExpect(jsonPath("$[1].title").value("Project Beta"));
    }

    @Test
    void getAllProjects_returns200WithEmptyList_whenNoProjects() throws Exception {
        when(projectService.getAllProjects()).thenReturn(List.of());

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getProjectById_returns200_whenFound() throws Exception {
        Project project = Project.builder()
                .id(1L)
                .title("My Project")
                .description("A great project")
                .startDate(LocalDate.of(2024, 1, 1))
                .build();
        when(projectService.getProjectById(1L)).thenReturn(Optional.of(project));

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("My Project"))
                .andExpect(jsonPath("$.description").value("A great project"));
    }

    @Test
    void getProjectById_returns404_whenNotFound() throws Exception {
        when(projectService.getProjectById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/projects/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProject_returns201_withSavedProject() throws Exception {
        Project input = Project.builder().title("New Project").description("Description").build();
        Project saved = Project.builder().id(10L).title("New Project").description("Description").build();
        when(projectService.createProject(any(Project.class))).thenReturn(saved);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.title").value("New Project"));
    }

    @Test
    void updateProject_returns200_whenFound() throws Exception {
        Project updated = Project.builder().id(1L).title("Updated Title").build();
        when(projectService.updateProject(eq(1L), any(Project.class))).thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void updateProject_returns404_whenNotFound() throws Exception {
        when(projectService.updateProject(eq(99L), any(Project.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/projects/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Project.builder().title("X").build())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProject_returns204() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());

        verify(projectService).deleteProject(1L);
    }
}
