package com.portfolio.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String imageUrl;
    private String projectUrl;
    private String githubUrl;
    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection
    @CollectionTable(name = "project_tags", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tag")
    private java.util.List<String> tags;

    @Column(length = 2000)
    private String challenge;

    @Column(length = 2000)
    private String solution;

    @ElementCollection
    @CollectionTable(name = "project_features", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "feature")
    private java.util.List<String> features;
}
