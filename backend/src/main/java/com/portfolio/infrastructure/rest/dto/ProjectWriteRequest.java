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
public class ProjectWriteRequest {

    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @Size(max = 2048)
    private String imageUrl;

    @Size(max = 2048)
    private String projectUrl;

    @Size(max = 2048)
    private String githubUrl;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private List<String> tags;

    @Size(max = 2000)
    private String challenge;

    @Size(max = 2000)
    private String solution;

    private List<String> features;
}
