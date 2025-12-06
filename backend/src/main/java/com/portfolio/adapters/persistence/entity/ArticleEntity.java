package com.portfolio.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true)
    private String slug;

    @Column(length = 500)
    private String summary;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private String coverImageUrl;

    @ElementCollection
    private List<String> tags;

    private LocalDate publishedAt;

    private Integer readingTimeMinutes;
}
