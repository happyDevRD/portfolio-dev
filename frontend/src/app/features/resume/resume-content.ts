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

export const RESUME = {
  heroTitle: 'Currículum vitae',
  heroSubtitle: 'Desarrollador Full Stack · Java, Spring Boot, Angular',

  /** Resumen único (pantalla + PDF). */
  professionalSummary:
    'Desarrollador Full Stack con experiencia en APIs REST, integraciones (SOAP/REST) y reporting (JasperReports, Oracle Reports), ' +
    'y frontends Angular mantenibles. Experiencia en entornos empresariales y sector público: modernización de sistemas Java, ' +
    'integración de datos y entrega continua. Priorizo diseño por dominios, pruebas automatizadas y observabilidad.',

  degreeTitle: 'Ingeniería en Sistemas',
  schoolName: 'Universidad Dominicana O&M',
  graduationYear: '2021',

  /** Misma idea en web (tarjeta educación) e impresión. */
  continuingEducation:
    'Spring Boot, Angular, Docker y arquitectura limpia — cursos online y práctica aplicada en proyectos reales.',

  languages: 'Español (nativo); inglés (lectura técnica y documentación profesional).',

  /** Línea bajo el nombre en PDF (coherente con el hero web). */
  printTagline: 'Desarrollador Full Stack — Java, Spring Boot, Angular, integración y reporting',

  /** Certificaciones y cursos con enlace verificable (web + PDF). */
  certificates: [
    {
      title: 'SQL (Advanced)',
      issuer: 'HackerRank',
      issuedOn: '2026-02-26',
      verificationUrl: 'https://www.hackerrank.com/certificates/23897d4b34b1',
      topics:
        'Optimización de consultas, modelado de datos, índices, window functions y pivots en SQL.'
    },
    {
      title: 'Software Engineer Certificate',
      issuer: 'HackerRank',
      issuedOn: '2024-04-06',
      verificationUrl: 'https://www.hackerrank.com/certificates/72bbea8208b9',
      topics: 'Certificación de rol: resolución de problemas, SQL y APIs REST.'
    }
  ] satisfies readonly ResumeCertificate[]
} as const;
