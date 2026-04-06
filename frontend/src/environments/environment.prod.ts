/**
 * siteUrl: origen del frontend (canonical / redes).
 * apiUrl: base del backend REST (Google Cloud Run).
 */
export const environment = {
  production: true,
  siteUrl: 'https://elgarcia.org',
  apiUrl: 'https://portfolio-backend-827714540382.us-central1.run.app/api',
  defaultOgImagePath: '/assets/og-default.png',
  /** Sitio en https://cloud.umami.is — pega el Website ID aquí para activar analítica (sin cookies de terceros agresivas). */
  umamiWebsiteId: ''
};
