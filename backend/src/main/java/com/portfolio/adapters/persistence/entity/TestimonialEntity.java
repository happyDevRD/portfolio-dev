package com.portfolio.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testimonials")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;
    private String authorRole;
    @Column(length = 1000)
    private String content;
    private String avatarUrl;
}
