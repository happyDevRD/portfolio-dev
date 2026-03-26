/** URL pública del sitio (SEO, OG). Debe coincidir con el despliegue del frontend. */
export const environment = {
  production: false,
  siteUrl: 'http://localhost:4200',
  apiUrl: 'http://localhost:8080/api',
  /** Ruta bajo la raíz del sitio para og:image por defecto (compartir enlaces). */
  defaultOgImagePath: '/assets/og-default.png',
  /** Opcional: ID del sitio en Umami (https://cloud.umami.is). Si está vacío, no se carga el script. */
  umamiWebsiteId: ''
};
