package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    private Long id;
    private String title;
    private String description;
    private String iconUrl;
}
