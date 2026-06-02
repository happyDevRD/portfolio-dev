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

export type CvProfile = 'programador' | 'analista';

export interface CvProfileConfig {
  type: CvProfile;
  label: string;
  icon: string;
  heroSubtitle: string;
  summary: string;
  printTagline: string;
  /** Categorías de habilidades ordenadas por relevancia para este perfil. */
  skillPriority: string[];
  /** Orden preferido de empresas en la timeline (coincide con `company` del API). */
  experienceOrder: string[];
  /** Orden de certificados por título (`ResumeCertificate.title`). */
  certificateOrder: string[];
  continuingEducation: string;
}

export const CV_PROFILES: Record<CvProfile, CvProfileConfig> = {
  programador: {
    type: 'programador',
    label: 'Programador',
    icon: 'fas fa-code',
    heroSubtitle: 'Desarrollo de software · Java, Spring Boot, Angular',
    summary:
      '5 años construyendo y modernizando aplicaciones empresariales en sector público y financiero (RD). ' +
      'Entrego APIs y front de punta a punta: −40% latencia en APIs críticas (Redis), 3 módulos de monolito a microservicios (Spring Boot 3), ' +
      'CI/CD de 2h a 15min y integraciones bancarias con 50k+ transacciones/día (p99 < 300ms). ' +
      'Remoto, código en producción y portfolio open source verificable.',
    printTagline: 'Programador — Java, Spring Boot, Angular, APIs REST, CI/CD, integraciones',
    skillPriority: ['Backend', 'Frontend', 'DevOps', 'Database', 'Quality', 'Tools', 'Reporting'],
    experienceOrder: [
      'MayBlue, Caribe',
      'Institución Financiera (Remoto)',
      'Ministerio de Hacienda de la Rep. Dominicana',
      'ASES Manufacturing',
    ],
    certificateOrder: ['Software Engineer Certificate', 'SQL (Advanced)'],
    continuingEducation:
      'Arquitectura de software, Spring Boot, Angular y despliegue cloud — aplicado en proyectos reales.',
  },
  analista: {
    type: 'analista',
    label: 'Analista de datos',
    icon: 'fas fa-chart-line',
    heroSubtitle: 'Datos y reporting · SQL, Oracle, JasperReports',
    summary:
      '5 años entre datos, reporting y reglas de negocio en entornos gubernamentales y financieros. ' +
      'SQL avanzado certificado (HackerRank); migración de 200+ reportes Oracle→Jasper con −60% en tiempo de generación PDF; ' +
      'modelado y lógica en PL/SQL con validación salida a salida para auditoría. ' +
      'Enfoque en datos confiables, reportes reproducibles y trazabilidad para negocio y cumplimiento.',
    printTagline: 'Analista de datos — SQL avanzado, Oracle PL/SQL, JasperReports, reporting empresarial',
    skillPriority: ['Database', 'Reporting', 'Backend', 'Tools', 'Quality', 'DevOps', 'Frontend'],
    experienceOrder: [
      'Ministerio de Hacienda de la Rep. Dominicana',
      'MayBlue, Caribe',
      'Institución Financiera (Remoto)',
      'ASES Manufacturing',
    ],
    certificateOrder: ['SQL (Advanced)', 'Software Engineer Certificate'],
    continuingEducation:
      'SQL avanzado, modelado de datos y reporting empresarial — certificaciones HackerRank y práctica en misión crítica.',
  },
};

export const CV_PROFILE_LIST: CvProfile[] = ['programador', 'analista'];

export const RESUME = {
  heroTitle: 'Currículum vitae',

  degreeTitle: 'Ingeniería en Sistemas',
  schoolName: 'Universidad Dominicana O&M',
  graduationYear: '2021',

  languages: 'Español (nativo); inglés profesional escrito — documentación, tickets, code review y comunicación asíncrona con equipos internacionales.',

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
