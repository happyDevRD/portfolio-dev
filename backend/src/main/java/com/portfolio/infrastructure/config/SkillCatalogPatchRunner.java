package com.portfolio.infrastructure.config;

import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.usecase.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * Asegura skills añadidas después del primer seed (p. ej. Quarkus) cuando
 * {@link DataLoader} ya no corre porque la BD tiene proyectos.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SkillCatalogPatchRunner implements CommandLineRunner, Ordered {

    private final SkillService skillService;

    @Override
    public int getOrder() {
        return 10;
    }

    @Override
    public void run(String... args) {
        boolean hasQuarkus = skillService.getAllSkills().stream()
                .anyMatch(s -> "Quarkus".equalsIgnoreCase(s.getName()));
        if (!hasQuarkus) {
            skillService.createSkill(Skill.builder()
                    .name("Quarkus")
                    .category("Backend")
                    .proficiency(88)
                    .build());
            log.info("Skill catalog: added Quarkus (Backend)");
        }
    }
}
