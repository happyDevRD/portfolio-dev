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
    private LocalDate startDate;
    private LocalDate endDate;

    @ElementCollection
    private java.util.List<String> tags;

    @Column(length = 2000)
    private String challenge;

    @Column(length = 2000)
    private String solution;

    @ElementCollection
    private java.util.List<String> features;
}
