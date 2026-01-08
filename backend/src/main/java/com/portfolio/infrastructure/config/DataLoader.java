package com.portfolio.infrastructure.config;

import com.portfolio.core.domain.model.Article;
import com.portfolio.core.domain.model.Experience;
import com.portfolio.core.domain.model.Project;
import com.portfolio.core.domain.model.Skill;
import com.portfolio.core.domain.repository.ArticleRepository;

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
        private final ArticleRepository articleRepository;
        private final com.portfolio.core.usecase.GetServicesUseCase getServicesUseCase;
        private final com.portfolio.core.usecase.GetTestimonialsUseCase getTestimonialsUseCase;

        @Override
        public void run(String... args) throws Exception {
                if (!projectService.getAllProjects().isEmpty()) {
                        return;
                }

                // --- SERVICES ---
                createService("API Development",
                                "High-performance RESTful APIs using Spring Boot 3 and Clean Architecture.",
                                "api-icon");
                createService("Frontend Architecture", "Scalable SPAs with Angular 17, Signals, and reactive patterns.",
                                "web-icon");
                createService("Legacy Modernization", "Expertise in migrating Java EE monoliths to microservices.",
                                "migration-icon");
                createService("Reporting & Data", "Complex reporting solutions with JasperReports and Oracle PL/SQL.",
                                "report-icon");

                // --- TESTIMONIALS ---
                createTestimonial("John Doe", "Project Manager",
                                "Eleazar transformed our reporting system. The migration to JasperReports saved us hours of daily work.",
                                "https://ui-avatars.com/api/?name=John+Doe");
                createTestimonial("Jane Smith", "Tech Lead",
                                "His understanding of Clean Architecture made our codebase scalable and maintainable. Highly recommended!",
                                "https://ui-avatars.com/api/?name=Jane+Smith");

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
                                .description("Lideré la migración crítica de más de 200 reportes de Oracle Reports a Jasper Reports. Technologies: Oracle Forms, PL/SQL, JasperSoft.")
                                .imageUrl("https://images.unsplash.com/photo-1551288049-bebda4e38f71?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl("#")
                                .startDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2024, 1, 1))
                                .tags(List.of("Oracle", "PL/SQL", "Jasper Reports", "Migration", "Backend"))
                                .challenge("Migrating 200+ legacy Oracle Reports to a modern JasperReports solution without detailed documentation and zero downtime tolerance.")
                                .solution("Implemented a parallel run strategy, automated validation scripts comparing outputs pixel-by-pixel, and reverse-engineered PL/SQL logic into Java constraints.")
                                .features(List.of("Pixel-perfect PDF generation", "Automated Validation Suite",
                                                "Legacy PL/SQL Logic Extraction"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Integración Core Bancario")
                                .description("Lideré la integración técnica entre el core bancario y una plataforma móvil. Servicios SOAP y REST con IBM Cloud.")
                                .imageUrl("https://images.unsplash.com/photo-1563986768609-322da13575f3?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl("#")
                                .startDate(LocalDate.of(2024, 6, 1))
                                .endDate(LocalDate.of(2024, 10, 1))
                                .tags(List.of("Java", "SOAP", "REST", "IBM Cloud", "Backend", "Integration"))
                                .challenge("Integrating a legacy rigid Core Banking System with a modern mobile app requiring sub-second latency and high availability.")
                                .solution("Designed an intermediary integration layer using IBM sidecars and optimized SOAP-to-REST transformation with caching strategies.")
                                .features(List.of("Low-latency SOAP/REST Bridge", "High Availability Pattern",
                                                "Secure Token Exchange"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Enterprise API Gateway")
                                .description("A high-performance custom API Gateway designed to handle traffic spikes during Black Friday sales.")
                                .imageUrl("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl("#")
                                .startDate(LocalDate.of(2023, 11, 1))
                                .endDate(LocalDate.of(2024, 2, 1))
                                .tags(List.of("Java", "Spring Cloud", "Redis", "Resilience4j", "DevOps"))
                                .challenge("Handling 10k TPS during flash sales with zero downtime.")
                                .solution("Built a custom gateway using Spring Cloud Gateway with reactive non-blocking I/O and Redis-based rate limiting.")
                                .features(List.of("Distributed Rate Limiting", "Dynamic Circuit Breaker",
                                                "Real-time Metrics"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Full Stack Portfolio")
                                .description("Este portafolio. Construido con Clean Architecture, Spring Boot 3, Angular 17 y Docker.")
                                .imageUrl("https://images.unsplash.com/photo-1507238691740-187a5b1d37b8?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl("https://github.com/eleazargarcia/portfolio")
                                .startDate(LocalDate.now().minusMonths(1))
                                .tags(List.of("Spring Boot", "Angular", "Docker", "DevOps", "Full Stack"))
                                .challenge("Creating a portfolio that stands out while demonstrating proper software engineering principles.")
                                .solution("Applied Clean Architecture, Domain-Driven Design, and a fully automated CI/CD pipeline.")
                                .features(List.of("Clean Architecture", "Automated CI/CD", "Responsive Modern UI"))
                                .build());

                // Seed Extra Projects for Pagination Testing
                for (int i = 1; i <= 8; i++) {
                        projectService.createProject(Project.builder()
                                        .title("Proyecto Demo " + i)
                                        .description("Proyecto de demostración para probar la paginación y el grid responsive. Incluye tecnologías modernas.")
                                        .imageUrl("https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=1000&auto=format&fit=crop")
                                        .projectUrl("#")
                                        .startDate(LocalDate.now().minusMonths(i * 2))
                                        .endDate(LocalDate.now().minusMonths(i))
                                        .tags(List.of(i % 2 == 0 ? "Backend" : "Frontend", "Demo", "Full Stack"))
                                        .challenge("Demonstrating scalability.")
                                        .solution("Implemented scalable patterns.")
                                        .features(List.of("Feature A", "Feature B"))
                                        .build());
                }

                // Seed Articles
                Article a1 = Article.builder()
                                .title("Modernizing Legacy Systems")
                                .slug("modernizing-legacy-systems")
                                .summary("Strategies for migrating monolithic Java EE applications to microservices architecture using Spring Boot 3.")
                                .content("# Modernizing Legacy Systems\n\nMigrating from **Java EE** to **Spring Boot**...")
                                .publishedAt(LocalDate.now().minusDays(5))
                                .readingTimeMinutes(7)
                                .tags(List.of("Java", "Architecture", "Microservices"))
                                .build();

                Article a2 = Article.builder()
                                .title("Angular 17 Signals")
                                .slug("angular-17-signals")
                                .summary("How Signals simplify state management and improve change detection performance in Angular applications.")
                                .content("# Angular Signals\n\nSignals are the new reactive primitive...")
                                .publishedAt(LocalDate.now().minusDays(12))
                                .readingTimeMinutes(5)
                                .tags(List.of("Angular", "Frontend"))
                                .build();

                Article a3 = Article.builder()
                                .title("Optimizing Docker Builds")
                                .slug("optimizing-docker-builds")
                                .summary("Reduce your container image sizes by 60% using multi-stage builds and distroless images.")
                                .content("...")
                                .publishedAt(LocalDate.now().minusDays(20))
                                .readingTimeMinutes(4)
                                .tags(List.of("DevOps", "Docker"))
                                .build();

                Article a4 = Article.builder()
                                .title("Mastering JasperReports")
                                .slug("mastering-jasperreports")
                                .summary("Making pixel-perfect PDFs shouldn't be painful. Tips for enterprise reporting.")
                                .content("...")
                                .publishedAt(LocalDate.now().minusDays(25))
                                .readingTimeMinutes(6)
                                .tags(List.of("Reporting", "Java"))
                                .build();

                Article a5 = Article.builder()
                                .title("Clean Architecture w/ Spring")
                                .slug("clean-arch-spring")
                                .summary("A practical guide to implementing Clean Architecture in Spring Boot projects.")
                                .content("...")
                                .publishedAt(LocalDate.now().minusDays(30))
                                .readingTimeMinutes(10)
                                .tags(List.of("Architecture", "Spring Boot"))
                                .build();

                articleRepository.save(a1);
                articleRepository.save(a2);
                articleRepository.save(a3);
                articleRepository.save(a4);
                articleRepository.save(a5);

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

        private void createService(String title, String description, String icon) {
                getServicesUseCase.createService(com.portfolio.core.domain.model.Service.builder()
                                .title(title)
                                .description(description)
                                .iconUrl(icon)
                                .build());
        }

        private void createTestimonial(String authorName, String authorRole, String content, String avatarUrl) {
                getTestimonialsUseCase.createTestimonial(com.portfolio.core.domain.model.Testimonial.builder()
                                .authorName(authorName)
                                .authorRole(authorRole)
                                .content(content)
                                .avatarUrl(avatarUrl)
                                .build());
        }
}
