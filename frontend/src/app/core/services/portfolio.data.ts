import { Project } from '../models/project.model';
import { Skill } from '../models/skill.model';
import { Experience } from '../models/experience.model';
import { Service } from '../models/service.model';
import { Testimonial } from '../models/testimonial.model';
import { Article } from '../models/article.model';

// Static content mirroring the former backend DataLoader seed data.
// No backend is deployed right now — see CLAUDE.md for the reconnection plan.

const daysAgo = (days: number): string => {
    const d = new Date();
    d.setDate(d.getDate() - days);
    return d.toISOString().slice(0, 10);
};

const monthsAgo = (months: number): string => {
    const d = new Date();
    d.setMonth(d.getMonth() - months);
    return d.toISOString().slice(0, 10);
};

export const SERVICES: Service[] = [
    { id: 1, title: 'API Development', description: 'High-performance RESTful APIs using Spring Boot 3 and Clean Architecture.', iconUrl: 'api-icon' },
    { id: 2, title: 'Frontend Architecture', description: 'Scalable SPAs with Angular 17, Signals, and reactive patterns.', iconUrl: 'web-icon' },
    { id: 3, title: 'Legacy Modernization', description: 'Expertise in migrating Java EE monoliths to microservices.', iconUrl: 'migration-icon' },
    { id: 4, title: 'Reporting & Data', description: 'Complex reporting solutions with JasperReports and Oracle PL/SQL.', iconUrl: 'report-icon' },
];

export const TESTIMONIALS: Testimonial[] = [
    { id: 1, authorName: 'John Doe', authorRole: 'Project Manager', content: 'Eleazar transformed our reporting system. The migration to JasperReports saved us hours of daily work.', avatarUrl: 'https://ui-avatars.com/api/?name=John+Doe' },
    { id: 2, authorName: 'Jane Smith', authorRole: 'Tech Lead', content: 'His understanding of Clean Architecture made our codebase scalable and maintainable. Highly recommended!', avatarUrl: 'https://ui-avatars.com/api/?name=Jane+Smith' },
];

export const SKILLS: Skill[] = [
    { id: 1, name: 'Java', category: 'Backend', proficiency: 100, iconUrl: 'assets/icons/java.svg' },
    { id: 2, name: 'Spring Boot', category: 'Backend', proficiency: 100, iconUrl: 'assets/icons/spring-boot.svg' },
    { id: 3, name: 'Jakarta EE', category: 'Backend', proficiency: 90, iconUrl: 'assets/icons/jakarta-ee.svg' },
    { id: 4, name: 'PL/SQL', category: 'Backend', proficiency: 95, iconUrl: 'assets/icons/pl-sql.svg' },
    { id: 5, name: 'Oracle', category: 'Database', proficiency: 95, iconUrl: 'assets/icons/oracle.svg' },
    { id: 6, name: 'SQL', category: 'Database', proficiency: 100, iconUrl: 'assets/icons/sql.svg' },
    { id: 7, name: 'PostgreSQL', category: 'Database', proficiency: 90, iconUrl: 'assets/icons/postgresql.svg' },
    { id: 8, name: 'Angular', category: 'Frontend', proficiency: 90, iconUrl: 'assets/icons/angular.svg' },
    { id: 9, name: 'TypeScript', category: 'Frontend', proficiency: 90, iconUrl: 'assets/icons/typescript.svg' },
    { id: 10, name: 'Docker', category: 'DevOps', proficiency: 85, iconUrl: 'assets/icons/docker.svg' },
    { id: 11, name: 'Jenkins', category: 'DevOps', proficiency: 80, iconUrl: 'assets/icons/jenkins.svg' },
    { id: 12, name: 'Git', category: 'Tools', proficiency: 95, iconUrl: 'assets/icons/git.svg' },
    { id: 13, name: 'Jasper Reports', category: 'Reporting', proficiency: 95, iconUrl: 'assets/icons/jasper-reports.svg' },
    { id: 14, name: 'Oracle Reports', category: 'Reporting', proficiency: 90, iconUrl: 'assets/icons/oracle-reports.svg' },
    { id: 15, name: 'SonarQube', category: 'Quality', proficiency: 85, iconUrl: 'assets/icons/sonarqube.svg' },
];

const baseProjects: Project[] = [
    {
        id: 1,
        title: 'Modernización de Reportes - Min. Hacienda',
        description: 'Lideré la migración crítica de más de 200 reportes de Oracle Reports a Jasper Reports. Technologies: Oracle Forms, PL/SQL, JasperSoft.',
        imageUrl: 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?q=80&w=1000&auto=format&fit=crop',
        projectUrl: '#',
        startDate: '2022-01-01',
        endDate: '2024-01-01',
        tags: ['Oracle', 'PL/SQL', 'Jasper Reports', 'Migration', 'Backend'],
        challenge: 'Migrating 200+ legacy Oracle Reports to a modern JasperReports solution without detailed documentation and zero downtime tolerance.',
        solution: 'Implemented a parallel run strategy, automated validation scripts comparing outputs pixel-by-pixel, and reverse-engineered PL/SQL logic into Java constraints.',
        features: ['Pixel-perfect PDF generation', 'Automated Validation Suite', 'Legacy PL/SQL Logic Extraction'],
    },
    {
        id: 2,
        title: 'Integración Core Bancario',
        description: 'Lideré la integración técnica entre el core bancario y una plataforma móvil. Servicios SOAP y REST con IBM Cloud.',
        imageUrl: 'https://images.unsplash.com/photo-1563986768609-322da13575f3?q=80&w=1000&auto=format&fit=crop',
        projectUrl: '#',
        startDate: '2024-06-01',
        endDate: '2024-10-01',
        tags: ['Java', 'SOAP', 'REST', 'IBM Cloud', 'Backend', 'Integration'],
        challenge: 'Integrating a legacy rigid Core Banking System with a modern mobile app requiring sub-second latency and high availability.',
        solution: 'Designed an intermediary integration layer using IBM sidecars and optimized SOAP-to-REST transformation with caching strategies.',
        features: ['Low-latency SOAP/REST Bridge', 'High Availability Pattern', 'Secure Token Exchange'],
    },
    {
        id: 3,
        title: 'Enterprise API Gateway',
        description: 'A high-performance custom API Gateway designed to handle traffic spikes during Black Friday sales.',
        imageUrl: 'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?q=80&w=1000&auto=format&fit=crop',
        projectUrl: '#',
        startDate: '2023-11-01',
        endDate: '2024-02-01',
        tags: ['Java', 'Spring Cloud', 'Redis', 'Resilience4j', 'DevOps'],
        challenge: 'Handling 10k TPS during flash sales with zero downtime.',
        solution: 'Built a custom gateway using Spring Cloud Gateway with reactive non-blocking I/O and Redis-based rate limiting.',
        features: ['Distributed Rate Limiting', 'Dynamic Circuit Breaker', 'Real-time Metrics'],
    },
    {
        id: 4,
        title: 'Full Stack Portfolio',
        description: 'Este portafolio. Construido con Clean Architecture, Spring Boot 3, Angular 17 y Docker.',
        imageUrl: 'https://images.unsplash.com/photo-1507238691740-187a5b1d37b8?q=80&w=1000&auto=format&fit=crop',
        projectUrl: 'https://github.com/happyDevRD/portfolio',
        startDate: monthsAgo(1),
        tags: ['Spring Boot', 'Angular', 'Docker', 'DevOps', 'Full Stack'],
        challenge: 'Creating a portfolio that stands out while demonstrating proper software engineering principles.',
        solution: 'Applied Clean Architecture, Domain-Driven Design, and a fully automated CI/CD pipeline.',
        features: ['Clean Architecture', 'Automated CI/CD', 'Responsive Modern UI'],
    },
];

const demoProjects: Project[] = Array.from({ length: 8 }, (_, i) => {
    const n = i + 1;
    return {
        id: 4 + n,
        title: `Proyecto Demo ${n}`,
        description: 'Proyecto de demostración para probar la paginación y el grid responsive. Incluye tecnologías modernas.',
        imageUrl: 'https://images.unsplash.com/photo-1498050108023-c5249f4df085?q=80&w=1000&auto=format&fit=crop',
        projectUrl: '#',
        startDate: monthsAgo(n * 2),
        endDate: monthsAgo(n),
        tags: [n % 2 === 0 ? 'Backend' : 'Frontend', 'Demo', 'Full Stack'],
        challenge: 'Demonstrating scalability.',
        solution: 'Implemented scalable patterns.',
        features: ['Feature A', 'Feature B'],
    };
});

export const PROJECTS: Project[] = [...baseProjects, ...demoProjects];

export const ARTICLES: Article[] = [
    {
        id: 1,
        title: 'Modernizing Legacy Systems',
        slug: 'modernizing-legacy-systems',
        summary: 'Strategies for migrating monolithic Java EE applications to microservices architecture using Spring Boot 3.',
        content: '# Modernizing Legacy Systems\n\nMigrating from **Java EE** to **Spring Boot**...',
        tags: ['Java', 'Architecture', 'Microservices'],
        publishedAt: daysAgo(5),
        readingTimeMinutes: 7,
    },
    {
        id: 2,
        title: 'Angular 17 Signals',
        slug: 'angular-17-signals',
        summary: 'How Signals simplify state management and improve change detection performance in Angular applications.',
        content: '# Angular Signals\n\nSignals are the new reactive primitive...',
        tags: ['Angular', 'Frontend'],
        publishedAt: daysAgo(12),
        readingTimeMinutes: 5,
    },
    {
        id: 3,
        title: 'Optimizing Docker Builds',
        slug: 'optimizing-docker-builds',
        summary: 'Reduce your container image sizes by 60% using multi-stage builds and distroless images.',
        content: '...',
        tags: ['DevOps', 'Docker'],
        publishedAt: daysAgo(20),
        readingTimeMinutes: 4,
    },
    {
        id: 4,
        title: 'Mastering JasperReports',
        slug: 'mastering-jasperreports',
        summary: "Making pixel-perfect PDFs shouldn't be painful. Tips for enterprise reporting.",
        content: '...',
        tags: ['Reporting', 'Java'],
        publishedAt: daysAgo(25),
        readingTimeMinutes: 6,
    },
    {
        id: 5,
        title: 'Clean Architecture w/ Spring',
        slug: 'clean-arch-spring',
        summary: 'A practical guide to implementing Clean Architecture in Spring Boot projects.',
        content: '...',
        tags: ['Architecture', 'Spring Boot'],
        publishedAt: daysAgo(30),
        readingTimeMinutes: 10,
    },
];

export const EXPERIENCES: Experience[] = [
    {
        id: 1,
        company: 'MayBlue, Caribe',
        role: 'Senior Full Stack Java Developer',
        description: 'Desarrollo de aplicaciones web empresariales Full Stack (Java Spring Boot + Angular). Diseño de APIs RESTful siguiendo Clean Architecture. Colaboración en modernización de sistemas heredados (Jakarta EE) a microservicios.',
        startDate: '2024-01-01',
        endDate: '',
        currentInfo: true,
    },
    {
        id: 2,
        company: 'Institución Financiera (Remoto)',
        role: 'Especialista en Integraciones (Contrato)',
        description: 'Integración de Core Bancario con Plataforma Móvil. Exposición segura de datos mediante SOAP/REST, IBM Cloud y Message Brokers.',
        startDate: '2024-06-01',
        endDate: '2024-10-30',
        currentInfo: false,
    },
    {
        id: 3,
        company: 'Ministerio de Hacienda de la Rep. Dominicana',
        role: 'Java Software Developer',
        description: 'Lideré la migración de 200+ reportes (Oracle -> Jasper). Traslado de lógica de negocio compleja (PL/SQL). Mantenimiento de aplicaciones del Sistema de Administración Financiera (Java/Jakarta EE).',
        startDate: '2020-01-01',
        endDate: '2024-01-01',
        currentInfo: false,
    },
    {
        id: 4,
        company: 'ASES Manufacturing',
        role: 'Encargado de Sistemas Informáticos',
        description: 'Gestión y mantenimiento de sistemas informáticos. Automatización de procesos y coordinación de hardware/software.',
        startDate: '2020-05-01',
        endDate: '2021-11-30',
        currentInfo: false,
    },
];
