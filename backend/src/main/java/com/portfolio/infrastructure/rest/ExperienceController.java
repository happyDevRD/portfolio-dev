package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.usecase.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
@Tag(name = "Experiences", description = "Endpoints for managing experience entries")
@RequiredArgsConstructor

public class ExperienceController {
    private final ExperienceService experienceService;

    @GetMapping
    @Operation(summary = "Get all experiences")
    public ResponseEntity<List<Experience>> getAllExperiences() {
        return ResponseEntity.ok(experienceService.getAllExperiences());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get experience by ID")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id) {
        return experienceService.getExperienceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new experience")
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experienceService.createExperience(experience));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an experience")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @RequestBody Experience experience) {
        return experienceService.updateExperience(id, experience)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an experience")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}
