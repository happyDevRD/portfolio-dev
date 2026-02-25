package com.portfolio.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "experiences")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;
    private String role;

    @Column(length = 2000)
    private String description;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentInfo;

    @ElementCollection
    @CollectionTable(name = "experience_highlights", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "highlight", length = 500)
    private List<String> highlights;
}
