package com.portfolio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    private List<String> highlights;
}
