/**
 * Textos del CV: una sola fuente para la vista web y la de impresión/PDF.
 */
export interface ResumeCertificate {
  title: string;
  issuer: string;
  /** Fecha ISO (YYYY-MM-DD) para el pipe de fechas. */
  issuedOn: string;
  verificationUrl: string;
  /** Una línea opcional (temario o alcance). */
  topics?: string;
}

export type CvProfile = 'java' | 'fullstack' | 'data';

export interface CvProfileConfig {
  type: CvProfile;
  label: string;
  icon: string;
  heroSubtitle: string;
  summary: string;
  printTagline: string;
  /** Categorías de habilidades ordenadas por relevancia para este perfil. */
  skillPriority: string[];
}

export const CV_PROFILES: Record<CvProfile, CvProfileConfig> = {
  java: {
    type: 'java',
    label: 'Java Enterprise',
    icon: 'fab fa-java',
    heroSubtitle: 'Java Enterprise Developer · Spring Boot, Oracle, JasperReports',
    summary:
      'Desarrollador Java Enterprise con 5 años en sistemas críticos para el sector público y financiero de República Dominicana. ' +
      'Migré 200+ reportes Oracle con validación automatizada, mantuve un sistema financiero con 99.9% de disponibilidad para 400+ usuarios, ' +
      'e integré cores bancarios procesando 50k+ transacciones/día con latencia p99 < 300ms. ' +
      'Especializado en Spring Boot, Oracle PL/SQL, JasperReports y Clean Architecture.',
    printTagline: 'Java Enterprise Developer — Spring Boot, Oracle PL/SQL, JasperReports, Clean Architecture',
    skillPriority: ['Backend', 'Database', 'Reporting', 'Quality', 'DevOps', 'Tools', 'Frontend'],
  },
  fullstack: {
    type: 'fullstack',
    label: 'Full Stack',
    icon: 'fas fa-layer-group',
    heroSubtitle: 'Full Stack Developer · Java, Spring Boot, Angular',
    summary:
      'Desarrollador Full Stack con 5 años construyendo aplicaciones web empresariales de extremo a extremo. ' +
      'En MayBlue reduje tiempos de respuesta de APIs en 40% con Redis, migré 3 módulos monolíticos a microservicios bajo Clean Architecture ' +
      'y recorté el ciclo de despliegue de 2h a 15min con GitHub Actions. ' +
      'Stack principal: Spring Boot, Angular, Docker y PostgreSQL.',
    printTagline: 'Full Stack Developer — Spring Boot, Angular, Docker, CI/CD',
    skillPriority: ['Backend', 'Frontend', 'DevOps', 'Database', 'Tools', 'Quality', 'Reporting'],
  },
  data: {
    type: 'data',
    label: 'Datos / BI',
    icon: 'fas fa-chart-bar',
    heroSubtitle: 'Data & BI Developer · SQL, Oracle, PL/SQL, JasperReports',
    summary:
      'Desarrollador con 5 años de base sólida en bases de datos relacionales, SQL avanzado (certificado HackerRank), Oracle PL/SQL ' +
      'y generación de reportes empresariales. Diseñé y migré 200+ reportes complejos reduciendo el tiempo de generación PDF en 60% ' +
      'en entornos de misión crítica gubernamentales. ' +
      'Actualmente orientando mi carrera hacia Business Intelligence y análisis de datos estructurados.',
    printTagline: 'Data & BI Developer — SQL avanzado, Oracle PL/SQL, JasperReports, análisis de datos',
    skillPriority: ['Database', 'Reporting', 'Backend', 'Tools', 'DevOps', 'Frontend', 'Quality'],
  },
};

export const CV_PROFILE_LIST: CvProfile[] = ['java', 'fullstack', 'data'];

export const RESUME = {
  heroTitle: 'Currículum vitae',

  degreeTitle: 'Ingeniería en Sistemas',
  schoolName: 'Universidad Dominicana O&M',
  graduationYear: '2021',

  continuingEducation:
    'Spring Boot, Angular, Docker y arquitectura limpia — cursos online y práctica aplicada en proyectos reales.',

  languages: 'Español (nativo); inglés (lectura técnica y documentación profesional).',

  /** Certificaciones y cursos con enlace verificable (web + PDF). */
  certificates: [
    {
      title: 'SQL (Advanced)',
      issuer: 'HackerRank',
      issuedOn: '2026-02-26',
      verificationUrl: 'https://www.hackerrank.com/certificates/23897d4b34b1',
      topics:
        'Optimización de consultas, modelado de datos, índices, window functions y pivots en SQL.',
    },
    {
      title: 'Software Engineer Certificate',
      issuer: 'HackerRank',
      issuedOn: '2024-04-06',
      verificationUrl: 'https://www.hackerrank.com/certificates/72bbea8208b9',
      topics: 'Certificación de rol: resolución de problemas, SQL y APIs REST.',
    },
  ] satisfies readonly ResumeCertificate[],
} as const;
