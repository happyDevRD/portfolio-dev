package com.portfolio.infrastructure.config;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.domain.repository.ArticleRepository;
import com.portfolio.core.domain.repository.ExperienceRepository;
import com.portfolio.core.domain.repository.ProjectRepository;
import com.portfolio.core.domain.repository.SkillRepository;
import com.portfolio.core.usecase.ArticleService;
import com.portfolio.core.usecase.ExperienceService;
import com.portfolio.core.usecase.ProjectService;
import com.portfolio.core.usecase.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

        private final ProjectService projectService;
        private final SkillService skillService;
        private final ExperienceService experienceService;
        private final ArticleService articleService;
        private final ExperienceRepository experienceRepository;
        private final ArticleRepository articleRepository;

        @Override
        public void run(String... args) throws Exception {
                if (!projectService.getAllProjects().isEmpty()) {
                        return;
                }

                // --- SKILLS ---
                // Backend
                createSkill("Java", "Backend", 100);
                createSkill("Spring Boot", "Backend", 100);
                createSkill("Jakarta EE", "Backend", 90);
                createSkill("PL/SQL", "Backend", 95);
                createSkill("Oracle", "Database", 95);
                createSkill("SQL", "Database", 100);
                createSkill("PostgreSQL", "Database", 90);
                // Frontend
                createSkill("Angular", "Frontend", 90);
                createSkill("TypeScript", "Frontend", 90);
                // Tools & DevOps & Reports
                createSkill("Docker", "DevOps", 85);
                createSkill("Jenkins", "DevOps", 80);
                createSkill("Git", "Tools", 95);
                createSkill("Jasper Reports", "Reporting", 95);
                createSkill("Oracle Reports", "Reporting", 90);
                createSkill("SonarQube", "Quality", 85);

                // --- PROJECTS ---
                projectService.createProject(Project.builder()
                                .title("Modernización de Reportes - Min. Hacienda")
                                .description("Lideré la migración crítica de más de 200 reportes de Oracle Reports a Jasper Reports. "
                                                +
                                                "Tecnologías: Oracle Forms, PL/SQL, JasperSoft. Logré asegurar la continuidad operativa y validar rigurosamente cada reporte migrado.")
                                .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_Opj80d1xJq4iZz-bJvK9qXqqE1c&usqp=CAU") // Placeholder
                                .projectUrl("#")
                                .startDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2024, 1, 1))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Integración Core Bancario")
                                .description("Lideré la integración técnica entre el core bancario y una plataforma móvil. "
                                                +
                                                "Desarrollo de servicios SOAP y REST utilizando IBM Cloud Connectivity y Message Brokers.")
                                .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqXqXqE1c&usqp=CAU") // Placeholder
                                .projectUrl("#")
                                .startDate(LocalDate.of(2024, 6, 1))
                                .endDate(LocalDate.of(2024, 10, 1))
                                .build());

                // Seed Articles
                Article a1 = Article.builder()
                                .title("Modernizing Legacy Systems with Spring Boot 3")
                                .slug("modernizing-legacy-systems")
                                .summary("A deep dive into strategies for migrating monolithic Java EE applications to microservices architecture using Spring Boot 3 avoiding common pitfalls.")
                                .content("# Modernizing Legacy Systems\n\nMigrating from **Java EE** to **Spring Boot** is not just about changing frameworks; it's about shifting mindsets.\n\n## The Strangler Pattern\nOne of the most effective strategies is the Strangler Fig Pattern. Instead of a big bang rewrite, you slowly replace pieces of functionality with new microservices.\n\n### Key Benefits\n- Reduced Risk\n- Incremental Value\n- Team Upskilling")
                                .publishedAt(LocalDate.now().minusDays(5))
                                .readingTimeMinutes(7)
                                .tags(List.of("Java", "Architecture", "Microservices"))
                                .build();

                Article a2 = Article.builder()
                                .title("Angular 17 Signals: A Game Changer")
                                .slug("angular-17-signals")
                                .summary("Exploring the new reactivity model in Angular. How Signals simplify state management and improve change detection performance.")
                                .content("# Angular Signals\n\nSignals are the new reactive primitive in Angular. They provide a way to track value changes and notify consumers.\n\n```typescript\nconst count = signal(0);\ncount.set(1);\n```\n\nThis changes everything about how we handle state.")
                                .publishedAt(LocalDate.now().minusDays(12))
                                .readingTimeMinutes(5)
                                .tags(List.of("Angular", "Frontend", "Performance"))
                                .build();

                Article a3 = Article.builder()
                                .title("Optimizing Docker Builds")
                                .slug("optimizing-docker-builds")
                                .summary("Tips and tricks to reduce your container image sizes by 60% using multi-stage builds and distroless images.")
                                .content("...")
                                .publishedAt(LocalDate.now().minusDays(20))
                                .readingTimeMinutes(4)
                                .tags(List.of("DevOps", "Docker"))
                                .build();

                Article a4 = Article.builder()
                                .title("The Art of JasperReports")
                                .slug("art-of-jasperreports")
                                .summary("Making pixel-perfect PDFs shouldn't be painful. Here is how to master JasperReports in 2024.")
                                .content("...")
                                .publishedAt(LocalDate.now().minusDays(25))
                                .readingTimeMinutes(6)
                                .tags(List.of("Reporting", "Java"))
                                .build();

                articleRepository.save(a1);
                articleRepository.save(a2);
                articleRepository.save(a3);
                articleRepository.save(a4);

                projectService.createProject(Project.builder()
                                .title("Full Stack Portfolio")
                                .description("Este portafolio. Construido con Clean Architecture, Spring Boot 3, Angular 17 y Docker. "
                                                +
                                                "Demuestra capacidades Full Stack modernas y principios SOLID.")
                                .imageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_Opj80d1xJq4iZz-bJvK9qXqqE1c&usqp=CAU") // Placeholder
                                .projectUrl("https://github.com/eleazargarcia/portfolio")
                                .startDate(LocalDate.now().minusMonths(1))
                                .build());

                // --- EXPERIENCE ---
                // 1. MayBlue
                experienceService.createExperience(Experience.builder()
                                .company("MayBlue, Caribe")
                                .role("Senior Full Stack Java Developer")
                                .description("Desarrollo de aplicaciones web empresariales Full Stack (Java Spring Boot + Angular). "
                                                +
                                                "Diseño de APIs RESTful siguiendo Clean Architecture. " +
                                                "Colaboración en modernización de sistemas heredados (Jakarta EE) a microservicios.")
                                .startDate(LocalDate.of(2024, 1, 1))
                                .currentInfo(true)
                                .build());

                // 2. Integrations Specialist (Contract)
                experienceService.createExperience(Experience.builder()
                                .company("Institución Financiera (Remoto)")
                                .role("Especialista en Integraciones (Contrato)")
                                .description("Integración de Core Bancario con Plataforma Móvil. Exposición segura de datos mediante SOAP/REST, IBM Cloud y Message Brokers.")
                                .startDate(LocalDate.of(2024, 6, 1))
                                .endDate(LocalDate.of(2024, 10, 30))
                                .currentInfo(false)
                                .build());

                // 3. Min Hacienda
                experienceService.createExperience(Experience.builder()
                                .company("Ministerio de Hacienda de la Rep. Dominicana")
                                .role("Java Software Developer")
                                .description("Lideré la migración de 200+ reportes (Oracle -> Jasper). " +
                                                "Traslado de lógica de negocio compleja (PL/SQL). " +
                                                "Mantenimiento de aplicaciones del Sistema de Administración Financiera (Java/Jakarta EE).")
                                .startDate(LocalDate.of(2020, 1, 1))
                                .endDate(LocalDate.of(2024, 1, 1))
                                .currentInfo(false)
                                .build());

                // 4. ASES Manufacturing
                experienceService.createExperience(Experience.builder()
                                .company("ASES Manufacturing")
                                .role("Encargado de Sistemas Informáticos")
                                .description("Gestión y mantenimiento de sistemas informáticos. Automatización de procesos y coordinación de hardware/software.")
                                .startDate(LocalDate.of(2020, 5, 1))
                                .endDate(LocalDate.of(2021, 11, 30))
                                .currentInfo(false)
                                .build());
        }

        private void createSkill(String name, String category, int proficiency) {
                skillService.createSkill(Skill.builder()
                                .name(name)
                                .category(category)
                                .proficiency(proficiency)
                                .iconUrl("assets/icons/" + name.toLowerCase().replace(" ", "-") + ".svg") // Auto-generate
                                                                                                          // icon path
                                                                                                          // logic
                                .build());
        }
}
