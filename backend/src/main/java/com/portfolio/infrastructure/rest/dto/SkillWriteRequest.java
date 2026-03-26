package com.portfolio.infrastructure.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class SkillWriteRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer proficiency;

    @Size(max = 2048)
    private String iconUrl;

    @NotBlank
    @Size(max = 100)
    private String category;
}
