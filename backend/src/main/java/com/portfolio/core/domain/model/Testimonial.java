package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Testimonial {
    private Long id;
    private String authorName;
    private String authorRole;
    private String content;
    private String avatarUrl;
}
