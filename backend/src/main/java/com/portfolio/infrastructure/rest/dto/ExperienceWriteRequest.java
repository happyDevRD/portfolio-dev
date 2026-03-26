package com.portfolio.infrastructure.rest.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceWriteRequest {

    @NotBlank
    @Size(max = 255)
    private String company;

    @NotBlank
    @Size(max = 255)
    private String role;

    @Size(max = 2000)
    private String description;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean currentInfo;

    private List<String> highlights;
}
