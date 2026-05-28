package com.portfolio.adapters.persistence.mapper;

import com.portfolio.adapters.persistence.entity.ProjectEntity;
import com.portfolio.core.domain.model.Project;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectMapperTest {

    private final ProjectMapper mapper = new ProjectMapper();

    @Test
    void toDomain_returnsNull_whenEntityIsNull() {
        assertThat(mapper.toDomain(null)).isNull();
    }

    @Test
    void toEntity_returnsNull_whenDomainIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toDomain_mapsAllFields() {
        ProjectEntity entity = ProjectEntity.builder()
                .id(1L)
                .title("Portfolio")
                .description("My portfolio site")
                .imageUrl("https://img.example.com/cover.png")
                .projectUrl("https://portfolio.example.com")
                .githubUrl("https://github.com/user/repo")
                .startDate(LocalDate.of(2023, 1, 1))
                .endDate(LocalDate.of(2023, 12, 31))
                .tags(List.of("Java", "Spring", "Angular"))
                .challenge("Integrating Clean Architecture")
                .solution("Ports and adapters pattern")
                .features(List.of("Blog", "CV exportable"))
                .build();

        Project domain = mapper.toDomain(entity);

        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getTitle()).isEqualTo("Portfolio");
        assertThat(domain.getDescription()).isEqualTo("My portfolio site");
        assertThat(domain.getImageUrl()).isEqualTo("https://img.example.com/cover.png");
        assertThat(domain.getProjectUrl()).isEqualTo("https://portfolio.example.com");
        assertThat(domain.getGithubUrl()).isEqualTo("https://github.com/user/repo");
        assertThat(domain.getStartDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(domain.getEndDate()).isEqualTo(LocalDate.of(2023, 12, 31));
        assertThat(domain.getTags()).containsExactly("Java", "Spring", "Angular");
        assertThat(domain.getChallenge()).isEqualTo("Integrating Clean Architecture");
        assertThat(domain.getSolution()).isEqualTo("Ports and adapters pattern");
        assertThat(domain.getFeatures()).containsExactly("Blog", "CV exportable");
    }

    @Test
    void toEntity_mapsAllFields() {
        Project domain = Project.builder()
                .id(2L)
                .title("Hotel System")
                .description("PMS for hotels")
                .imageUrl("https://img.example.com/hotel.png")
                .projectUrl("https://hotel.example.com")
                .githubUrl("https://github.com/user/hotel")
                .startDate(LocalDate.of(2024, 3, 1))
                .endDate(null)
                .tags(List.of("Spring Boot", "Angular"))
                .challenge("Real-time updates")
                .solution("WebSocket")
                .features(List.of("Reservations", "Check-in"))
                .build();

        ProjectEntity entity = mapper.toEntity(domain);

        assertThat(entity.getId()).isEqualTo(2L);
        assertThat(entity.getTitle()).isEqualTo("Hotel System");
        assertThat(entity.getDescription()).isEqualTo("PMS for hotels");
        assertThat(entity.getImageUrl()).isEqualTo("https://img.example.com/hotel.png");
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2024, 3, 1));
        assertThat(entity.getEndDate()).isNull();
        assertThat(entity.getTags()).containsExactly("Spring Boot", "Angular");
        assertThat(entity.getFeatures()).containsExactly("Reservations", "Check-in");
    }

    @Test
    void roundtrip_preservesAllFields() {
        Project original = Project.builder()
                .id(3L)
                .title("Roundtrip Project")
                .description("Testing roundtrip mapping")
                .tags(List.of("T1", "T2"))
                .features(List.of("F1", "F2"))
                .startDate(LocalDate.of(2024, 6, 15))
                .challenge("Some challenge")
                .solution("Some solution")
                .build();

        Project result = mapper.toDomain(mapper.toEntity(original));

        assertThat(result.getId()).isEqualTo(original.getId());
        assertThat(result.getTitle()).isEqualTo(original.getTitle());
        assertThat(result.getDescription()).isEqualTo(original.getDescription());
        assertThat(result.getTags()).isEqualTo(original.getTags());
        assertThat(result.getFeatures()).isEqualTo(original.getFeatures());
        assertThat(result.getStartDate()).isEqualTo(original.getStartDate());
        assertThat(result.getChallenge()).isEqualTo(original.getChallenge());
        assertThat(result.getSolution()).isEqualTo(original.getSolution());
    }

    @Test
    void toDomain_handlesNullLists() {
        ProjectEntity entity = ProjectEntity.builder()
                .id(4L)
                .title("No lists")
                .tags(null)
                .features(null)
                .build();

        Project domain = mapper.toDomain(entity);

        assertThat(domain.getTags()).isNull();
        assertThat(domain.getFeatures()).isNull();
    }
}
