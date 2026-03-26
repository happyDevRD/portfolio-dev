package com.portfolio.infrastructure.rest;

import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.usecase.SkillService;
import com.portfolio.infrastructure.rest.dto.SkillWriteRequest;
import com.portfolio.infrastructure.rest.mapper.RestDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@Tag(name = "Skills", description = "Endpoints for managing skills")
@RequiredArgsConstructor

public class SkillController {
    private final SkillService skillService;

    @GetMapping
    @Operation(summary = "Get all skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get skill by ID")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        return skillService.getSkillById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new skill")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillWriteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(skillService.createSkill(RestDtoMapper.toSkill(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a skill")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillWriteRequest request) {
        return skillService.updateSkill(id, RestDtoMapper.toSkill(request))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a skill")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.noContent().build();
    }
}
