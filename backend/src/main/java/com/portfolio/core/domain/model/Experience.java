package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    private Long id;
    private String company;
    private String role;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean currentInfo;
}
