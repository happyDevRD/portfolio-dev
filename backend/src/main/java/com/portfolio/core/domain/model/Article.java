package com.portfolio.core.domain.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String content; // Markdown or HTML
    private String coverImageUrl;
    private List<String> tags;
    private LocalDate publishedAt;
    private Integer readingTimeMinutes;
}
