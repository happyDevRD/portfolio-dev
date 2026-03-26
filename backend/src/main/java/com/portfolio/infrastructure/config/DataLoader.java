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
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner, Ordered {

        @Override
        public int getOrder() {
                return 5;
        }

        private final ProjectService projectService;
        private final SkillService skillService;
        private final ExperienceService experienceService;
        private final ArticleRepository articleRepository;
        private final com.portfolio.core.usecase.GetServicesUseCase getServicesUseCase;

        @Override
        public void run(String... args) throws Exception {
                if (!projectService.getAllProjects().isEmpty()) {
                        return;
                }

                // --- SERVICES ---
                createService("Desarrollo de APIs",
                                "Diseño e implementación de APIs REST con Spring Boot 3, contratos estables y capas alineadas a Clean Architecture.",
                                "api-icon");
                createService("Arquitectura Frontend",
                                "Aplicaciones Angular mantenibles: rutas lazy, estado claro y UI coherente con tu dominio de negocio.",
                                "web-icon");
                createService("Modernización Legacy",
                                "Estrategias para salir de monolitos Java EE: extracción por módulos, pruebas de regresión y despliegues graduales.",
                                "migration-icon");
                createService("Reportería y Datos",
                                "Reporting empresarial con JasperReports y lógica en PL/SQL cuando el volumen y la precisión lo exigen.",
                                "report-icon");


                // --- SKILLS ---
                // Backend
                createSkill("Java", "Backend", 100);
                createSkill("Spring Boot", "Backend", 100);
                createSkill("Quarkus", "Backend", 88);
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
                                .title("Modernización de reportes — Ministerio de Hacienda")
                                .description("Migración de más de 200 reportes desde Oracle Reports hacia JasperReports, con validación en paralelo y traspaso de reglas desde PL/SQL.")
                                .imageUrl("https://images.unsplash.com/photo-1551288049-bebda4e38f71?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl(null)
                                .githubUrl(null)
                                .startDate(LocalDate.of(2022, 1, 1))
                                .endDate(LocalDate.of(2024, 1, 1))
                                .tags(List.of("Oracle", "PL/SQL", "Jasper Reports", "Migration", "Backend"))
                                .challenge("Documentación incompleta, tolerancia casi cero a errores en PDFs críticos y necesidad de convivencia entre sistema viejo y nuevo.")
                                .solution("Ejecución en paralelo, scripts de comparación salida a salida y extracción gradual de reglas de negocio hacia servicios Java donde correspondía.")
                                .features(List.of("PDFs consistentes entre entornos", "Suite de validación automatizada",
                                                "Refactor de reglas PL/SQL"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Integración core bancario y canal móvil")
                                .description("Capa de integración entre core bancario legado y aplicación móvil: exposición segura vía SOAP/REST e infraestructura en IBM Cloud.")
                                .imageUrl("https://images.unsplash.com/photo-1563986768609-322da13575f3?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl(null)
                                .githubUrl(null)
                                .startDate(LocalDate.of(2024, 6, 1))
                                .endDate(LocalDate.of(2024, 10, 1))
                                .tags(List.of("Java", "SOAP", "REST", "IBM Cloud", "Backend", "Integration"))
                                .challenge("Latencia ajustada para operaciones críticas, alta disponibilidad y compatibilidad con contratos SOAP rígidos del core.")
                                .solution("Orquestación intermedia, transformación SOAP→REST optimizada y estrategias de caché donde el negocio lo permitía.")
                                .features(List.of("Puente SOAP/REST con latencia controlada", "Patrones de alta disponibilidad",
                                                "Intercambio seguro de tokens"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("API Gateway para picos de tráfico")
                                .description("Gateway de APIs con Spring Cloud Gateway, Redis y Resilience4j para soportar picos de ventas sin saturar los servicios internos.")
                                .imageUrl("https://images.unsplash.com/photo-1558494949-ef010cbdcc31?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl(null)
                                .githubUrl(null)
                                .startDate(LocalDate.of(2023, 11, 1))
                                .endDate(LocalDate.of(2024, 2, 1))
                                .tags(List.of("Java", "Spring Cloud", "Redis", "Resilience4j", "DevOps"))
                                .challenge("Picos de miles de peticiones por segundo en campañas de venta, sin degradar el resto del ecosistema.")
                                .solution("I/O reactivo, rate limiting distribuido con Redis y circuit breakers configurados por ruta.")
                                .features(List.of("Rate limiting coordinado", "Circuit breaker dinámico",
                                                "Métricas en tiempo real"))
                                .build());

                projectService.createProject(Project.builder()
                                .title("Portafolio elgarcia.org")
                                .description("Este sitio: backend Spring Boot con seguridad, Flyway y tests; frontend Angular standalone; despliegue con Docker y CI/CD. Código abierto en GitHub.")
                                .imageUrl("https://images.unsplash.com/photo-1507238691740-187a5b1d37b8?q=80&w=1000&auto=format&fit=crop")
                                .projectUrl("https://elgarcia.org")
                                .githubUrl("https://github.com/happyDevRD/portfolio-dev")
                                .startDate(LocalDate.now().minusMonths(1))
                                .tags(List.of("Spring Boot", "Angular", "Docker", "DevOps", "Full Stack"))
                                .challenge("Mostrar un portafolio vivo y profesional sin sacrificar buenas prácticas de arquitectura, seguridad y pruebas.")
                                .solution("Clean Architecture en el backend, rutas lazy en Angular, validación en API, integración Flyway + PostgreSQL y pipeline de despliegue.")
                                .features(List.of("Arquitectura limpia", "CI/CD automatizado", "UI responsive y accesible"))
                                .build());

                // Seed Articles
                Article a1 = Article.builder()
                                .title("Modernizando Sistemas Legacy")
                                .slug("modernizing-legacy-systems")
                                .summary("Estrategias para migrar aplicaciones Java EE monolíticas a microservicios con Spring Boot 3.")
                                .content("# Modernizando Sistemas Legacy\n\n## El desafío del código heredado\n\nMigrar de **Java EE** a **Spring Boot** es uno de los proyectos más retadores en el mundo empresarial. Los sistemas legacy suelen carecer de documentación, tener lógica de negocio embebida en procedimientos almacenados y una cobertura de tests cercana a cero.\n\n## Estrategia de migración por fases\n\nLa clave es no intentar reescribir todo a la vez. El patrón **Strangler Fig** permite migrar incrementalmente:\n\n1. **Identificar bounded contexts**: mapea los módulos del monolito a dominios independientes.\n2. **Crear el nuevo servicio en paralelo**: el nuevo microservicio convive con el monolito.\n3. **Redirigir el tráfico gradualmente**: usa un API Gateway para ir enviando porcentajes del tráfico al nuevo servicio.\n4. **Eliminar el código viejo**: una vez validado, se retira el módulo del monolito.\n\n## Extrayendo lógica de PL/SQL a Java\n\nUno de los mayores retos es la lógica embebida en procedimientos almacenados Oracle. El proceso recomendado:\n\n```java\n// 1. Mapear el SP a un caso de uso\npublic class CalcularLiquidacionUseCase {\n    public Liquidacion ejecutar(Empleado empleado, Periodo periodo) {\n        // Lógica extraída del SP sp_calcular_liquidacion\n        BigDecimal salarioBase = empleado.getSalario();\n        long diasTrabajados = ChronoUnit.DAYS.between(periodo.getInicio(), periodo.getFin());\n        return new Liquidacion(salarioBase.multiply(BigDecimal.valueOf(diasTrabajados)).divide(BigDecimal.valueOf(30)));\n    }\n}\n```\n\n## Lecciones aprendidas\n\n- **Tests de caracterización primero**: antes de tocar el código, escribe tests que documentan el comportamiento actual (aunque sea incorrecto).\n- **Paciencia con los datos**: las bases de datos legacy tienen constraints implícitas que solo se descubren en producción.\n- **Comunicación con negocio**: muchas reglas de negocio solo existen en la cabeza de los usuarios. Las entrevistas son tan importantes como el código.")
                                .publishedAt(LocalDate.now().minusDays(5))
                                .readingTimeMinutes(7)
                                .tags(List.of("Java", "Architecture", "Microservices"))
                                .build();

                Article a2 = Article.builder()
                                .title("Angular 17 Signals: El Futuro de la Reactividad")
                                .slug("angular-17-signals")
                                .summary("Cómo los Signals simplifican la gestión de estado y mejoran el rendimiento de la detección de cambios en Angular.")
                                .content("# Angular 17 Signals: El Futuro de la Reactividad\n\n## ¿Qué son los Signals?\n\nLos **Signals** son el nuevo primitivo reactivo de Angular, introducido de forma estable en Angular 17. Un Signal es un wrapper alrededor de un valor que notifica a los consumidores cuando ese valor cambia.\n\n```typescript\nimport { signal, computed, effect } from '@angular/core';\n\n// Signal básico\nconst count = signal(0);\n\n// Computed: se recalcula automáticamente\nconst doubled = computed(() => count() * 2);\n\n// Effect: efecto secundario reactivo\neffect(() => console.log(`El valor es: ${count()}`));\n\n// Mutación\ncount.set(5);      // establece valor\ncount.update(v => v + 1);  // transforma el valor actual\n```\n\n## Ventajas sobre RxJS para estado de componente\n\n| Aspecto | RxJS (BehaviorSubject) | Signals |\n|---|---|---|\n| Sintaxis en template | `{{ value$ \\| async }}` | `{{ value() }}` |\n| Lectura en código | `.getValue()` | `value()` |\n| Detección de cambios | Zone.js (toda la app) | Granular (solo lo que cambió) |\n| Curva de aprendizaje | Alta | Baja |\n\n## Cuándo seguir usando RxJS\n\nLos Signals no reemplazan a RxJS para **operaciones asíncronas complejas**: llamadas HTTP, streams de eventos, combinación de múltiples fuentes. La combinación ideal es usar `toSignal()` para conectar ambos mundos:\n\n```typescript\nexport class ProductsComponent {\n  private service = inject(ProductService);\n\n  // Convierte un Observable en Signal\n  products = toSignal(this.service.getProducts(), { initialValue: [] });\n}\n```\n\nEl template queda limpio: `@for (p of products(); track p.id)` sin necesidad de `async` pipe.")
                                .publishedAt(LocalDate.now().minusDays(12))
                                .readingTimeMinutes(5)
                                .tags(List.of("Angular", "Frontend"))
                                .build();

                Article a3 = Article.builder()
                                .title("Optimizando Builds de Docker")
                                .slug("optimizing-docker-builds")
                                .summary("Reduce el tamaño de tus imágenes de contenedor un 60% usando multi-stage builds e imágenes distroless.")
                                .content("# Optimizando Builds de Docker\n\n## El problema de las imágenes pesadas\n\nUno de los errores más comunes al trabajar con Docker es construir imágenes innecesariamente grandes. Una imagen que pesa 1 GB en lugar de 100 MB significa despliegues lentos, mayor costo de almacenamiento y una mayor superficie de ataque.\n\n## Multi-Stage Builds\n\nLa técnica más efectiva es el **multi-stage build**. La idea es simple: usar una imagen con todas las herramientas de compilación para construir el artefacto, y luego copiar solo el resultado a una imagen mínima.\n\n```dockerfile\n# Etapa 1: Build\nFROM maven:3.9-eclipse-temurin-17 AS builder\nWORKDIR /app\nCOPY pom.xml .\nRUN mvn dependency:go-offline\nCOPY src ./src\nRUN mvn package -DskipTests\n\n# Etapa 2: Runtime\nFROM eclipse-temurin:17-jre-alpine\nWORKDIR /app\nCOPY --from=builder /app/target/*.jar app.jar\nENTRYPOINT [\"java\", \"-jar\", \"app.jar\"]\n```\n\nCon esta técnica pasamos de ~600 MB (imagen con JDK + Maven) a ~180 MB (solo JRE Alpine).\n\n## Imágenes Distroless\n\nGoogle mantiene las imágenes **distroless**, que no contienen shell ni utilidades del sistema operativo. Son ideales para producción:\n\n```dockerfile\nFROM gcr.io/distroless/java17-debian11\nCOPY --from=builder /app/target/*.jar /app.jar\nENTRYPOINT [\"java\", \"-jar\", \"/app.jar\"]\n```\n\n## Buenas prácticas adicionales\n\n- **Ordena el `COPY` estratégicamente**: copia el `pom.xml` y descarga dependencias antes de copiar el código fuente. Así Docker cachea la capa de dependencias.\n- **Usa `.dockerignore`**: excluye `target/`, `.git`, `*.md` para no enviar archivos innecesarios al daemon.\n- **Fija las versiones de las imágenes base**: evita usar `latest` en producción.\n\nSiguiendo estas prácticas, el tiempo de build en CI/CD puede reducirse hasta un 70% gracias al caché de capas.")
                                .publishedAt(LocalDate.now().minusDays(20))
                                .readingTimeMinutes(4)
                                .tags(List.of("DevOps", "Docker"))
                                .build();

                Article a4 = Article.builder()
                                .title("Dominando JasperReports")
                                .slug("mastering-jasperreports")
                                .summary("Generar PDFs perfectos no tiene que ser doloroso. Tips y patrones para reporting empresarial con JasperReports.")
                                .content("# Dominando JasperReports\n\n## ¿Por qué JasperReports?\n\nEn el mundo empresarial, los reportes en PDF siguen siendo un requisito crítico. JasperReports es la solución open-source más madura para generación de reportes en Java, con soporte para fuentes de datos JDBC, beans Java, XML y más.\n\n## Arquitectura recomendada\n\nEl error más común es mezclar la lógica de reporte con la capa de presentación. La arquitectura correcta separa:\n\n1. **Diseño del reporte** (archivo `.jrxml`): estructura visual, definida con Jaspersoft Studio.\n2. **Servicio de compilación**: compila el `.jrxml` a `.jasper` (binario optimizado) en el arranque.\n3. **Servicio de llenado**: recibe los datos y genera el `JasperPrint`.\n4. **Exportador**: convierte el `JasperPrint` a PDF, Excel, HTML, etc.\n\n```java\n@Service\npublic class ReportService {\n    private JasperReport compiledReport;\n\n    @PostConstruct\n    public void init() throws JRException {\n        InputStream template = getClass().getResourceAsStream(\"/reports/invoice.jrxml\");\n        compiledReport = JasperCompileManager.compileReport(template);\n    }\n\n    public byte[] generatePdf(Map<String, Object> params, JRDataSource dataSource) throws JRException {\n        JasperPrint print = JasperFillManager.fillReport(compiledReport, params, dataSource);\n        return JasperExportManager.exportReportToPdf(print);\n    }\n}\n```\n\n## Tips para PDFs pixel-perfect\n\n- **Fuentes embedidas**: registra las fuentes en `jasperreports_extension.properties` para garantizar consistencia entre entornos.\n- **Subreportes**: divide reportes complejos en subreportes reutilizables.\n- **Expresiones Java**: usa expresiones Java en los campos para formatear datos directamente en el template.\n- **Parámetros de locale**: pasa siempre `REPORT_LOCALE` y `REPORT_TIME_ZONE` para evitar inconsistencias de fechas en servidores en distintos husos horarios.")
                                .publishedAt(LocalDate.now().minusDays(25))
                                .readingTimeMinutes(6)
                                .tags(List.of("Reporting", "Java"))
                                .build();

                Article a5 = Article.builder()
                                .title("Clean Architecture con Spring Boot")
                                .slug("clean-arch-spring")
                                .summary("Guía práctica para implementar Clean Architecture en proyectos Spring Boot, con ejemplos reales de código.")
                                .content("# Clean Architecture con Spring Boot\n\n## ¿Qué es Clean Architecture?\n\nPropuesta por Robert C. Martin (Uncle Bob), la **Clean Architecture** organiza el código en capas concéntricas donde las dependencias solo apuntan hacia adentro. El dominio no conoce nada del mundo exterior.\n\n## Estructura de paquetes\n\n```\ncom.example\n├── core/\n│   ├── domain/model/       # Entidades puras (sin anotaciones de framework)\n│   ├── domain/repository/  # Interfaces (puertos de salida)\n│   └── usecase/            # Casos de uso (lógica de negocio)\n├── application/service/    # Implementaciones de casos de uso\n├── adapters/\n│   └── persistence/        # Implementaciones JPA de los repositorios\n└── infrastructure/\n    ├── rest/               # Controladores HTTP\n    └── config/             # Configuración Spring\n```\n\n## El principio clave: inversión de dependencias\n\nEl dominio define la **interfaz** del repositorio. La capa de persistencia la **implementa**. Spring inyecta la implementación correcta en tiempo de ejecución.\n\n```java\n// En core/domain/repository — el dominio NO conoce JPA\npublic interface ProjectRepository {\n    List<Project> findAll();\n    Project save(Project project);\n}\n\n// En adapters/persistence — JPA implementa el contrato\n@Repository\npublic class ProjectRepositoryAdapter implements ProjectRepository {\n    private final ProjectJpaRepository jpa;\n\n    @Override\n    public List<Project> findAll() {\n        return jpa.findAll().stream()\n            .map(ProjectMapper::toDomain)\n            .toList();\n    }\n}\n```\n\n## Beneficios en la práctica\n\n- **Testabilidad**: los casos de uso se testean sin levantar Spring ni base de datos.\n- **Intercambiabilidad**: cambiar de H2 a PostgreSQL no toca el dominio.\n- **Claridad**: al leer el paquete `usecase` entiendes qué hace la aplicación sin saber nada de HTTP ni SQL.")
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
                                                + "Diseño de APIs RESTful siguiendo Clean Architecture. "
                                                + "Colaboración en modernización de sistemas heredados (Jakarta EE) a microservicios.")
                                .startDate(LocalDate.of(2024, 1, 1))
                                .currentInfo(true)
                                .highlights(List.of(
                                        "Reducción del 40% en tiempo de respuesta de APIs críticas mediante caching con Redis",
                                        "Migración de 3 módulos monolíticos a microservicios con Spring Boot 3 y Clean Architecture",
                                        "Implementación de CI/CD con GitHub Actions, reduciendo el tiempo de despliegue de 2h a 15min"
                                ))
                                .build());

                // 2. Integrations Specialist (Contract)
                experienceService.createExperience(Experience.builder()
                                .company("Institución Financiera (Remoto)")
                                .role("Especialista en Integraciones (Contrato)")
                                .description("Integración de Core Bancario con Plataforma Móvil. Exposición segura de datos mediante SOAP/REST, IBM Cloud y Message Brokers.")
                                .startDate(LocalDate.of(2024, 6, 1))
                                .endDate(LocalDate.of(2024, 10, 30))
                                .currentInfo(false)
                                .highlights(List.of(
                                        "Integración exitosa en producción con latencia p99 < 300ms",
                                        "Diseño de capa de orquestación SOAP/REST atendiendo 50k+ transacciones/día",
                                        "Zero downtime durante migración de sistema de pagos crítico"
                                ))
                                .build());

                // 3. Min Hacienda
                experienceService.createExperience(Experience.builder()
                                .company("Ministerio de Hacienda de la Rep. Dominicana")
                                .role("Java Software Developer")
                                .description("Lideré la migración de 200+ reportes (Oracle -> Jasper). "
                                                + "Traslado de lógica de negocio compleja (PL/SQL). "
                                                + "Mantenimiento de aplicaciones del Sistema de Administración Financiera (Java/Jakarta EE).")
                                .startDate(LocalDate.of(2020, 1, 1))
                                .endDate(LocalDate.of(2024, 1, 1))
                                .currentInfo(false)
                                .highlights(List.of(
                                        "Migración de 200+ reportes Oracle a JasperReports con validación pixel-perfect automatizada",
                                        "Reducción del 60% en tiempo de generación de reportes PDF críticos",
                                        "Mantenimiento de sistema financiero con disponibilidad 99.9% para 400+ usuarios"
                                ))
                                .build());

                // 4. ASES Manufacturing
                experienceService.createExperience(Experience.builder()
                                .company("ASES Manufacturing")
                                .role("Encargado de Sistemas Informáticos")
                                .description("Gestión y mantenimiento de sistemas informáticos. Automatización de procesos y coordinación de hardware/software.")
                                .startDate(LocalDate.of(2020, 5, 1))
                                .endDate(LocalDate.of(2021, 11, 30))
                                .currentInfo(false)
                                .highlights(List.of(
                                        "Automatización de procesos manuales reduciendo tiempo operativo en 30%",
                                        "Gestión de infraestructura IT para 50+ usuarios sin interrupciones críticas"
                                ))
                                .build());
        }

        private static final String DI = "https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons";

        private static final java.util.Map<String, String> SKILL_ICONS = java.util.Map.ofEntries(
                java.util.Map.entry("Java",          DI + "/java/java-original.svg"),
                java.util.Map.entry("Spring Boot",   DI + "/spring/spring-original.svg"),
                java.util.Map.entry("Jakarta EE",    DI + "/java/java-original.svg"),
                java.util.Map.entry("PL/SQL",        DI + "/oracle/oracle-original.svg"),
                java.util.Map.entry("Oracle",        DI + "/oracle/oracle-original.svg"),
                java.util.Map.entry("SQL",           DI + "/mysql/mysql-original.svg"),
                java.util.Map.entry("PostgreSQL",    DI + "/postgresql/postgresql-original.svg"),
                java.util.Map.entry("Angular",       DI + "/angular/angular-original.svg"),
                java.util.Map.entry("TypeScript",    DI + "/typescript/typescript-original.svg"),
                java.util.Map.entry("Docker",        DI + "/docker/docker-original.svg"),
                java.util.Map.entry("Jenkins",       DI + "/jenkins/jenkins-original.svg"),
                java.util.Map.entry("Git",           DI + "/git/git-original.svg"),
                java.util.Map.entry("Jasper Reports",DI + "/java/java-original.svg"),
                java.util.Map.entry("Oracle Reports",DI + "/oracle/oracle-original.svg"),
                java.util.Map.entry("SonarQube",     DI + "/sonarqube/sonarqube-original.svg")
        );

        private void createSkill(String name, String category, int proficiency) {
                String iconUrl = SKILL_ICONS.getOrDefault(name, DI + "/devicon/devicon-original.svg");
                skillService.createSkill(Skill.builder()
                                .name(name)
                                .category(category)
                                .proficiency(proficiency)
                                .iconUrl(iconUrl)
                                .build());
        }

        private void createService(String title, String description, String icon) {
                getServicesUseCase.createService(com.portfolio.core.domain.model.Service.builder()
                                .title(title)
                                .description(description)
                                .iconUrl(icon)
                                .build());
        }

}
