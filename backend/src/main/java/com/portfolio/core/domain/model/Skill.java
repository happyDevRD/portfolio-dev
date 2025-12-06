package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Skill {
    private Long id;
    private String name;
    private Integer proficiency; // 1-100 or 1-10
    private String iconUrl;
    private String category; // e.g., Backend, Frontend, DevOps
}
