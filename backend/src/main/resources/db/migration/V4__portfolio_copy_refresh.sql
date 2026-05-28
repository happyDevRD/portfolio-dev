-- Copy del portafolio: menos repetición, más señal para reclutadores (idempotente por título/slug/empresa).

UPDATE portfolio_services SET description = 'APIs REST con Spring Boot: contratos estables, dominio claro y despliegue en producción.'
WHERE title = 'Desarrollo de APIs';

UPDATE portfolio_services SET description = 'Angular con rutas lazy y estado predecible, alineado al dominio de negocio.'
WHERE title = 'Arquitectura Frontend';

UPDATE portfolio_services SET description = 'Salida gradual de monolitos Java EE: módulos acotados, regresión automatizada y releases sin parar el negocio.'
WHERE title = 'Modernización Legacy';

UPDATE portfolio_services SET description = 'JasperReports y PL/SQL para volúmenes altos y PDFs auditables en entornos críticos.'
WHERE title = 'Reportería y Datos';

UPDATE projects SET description = '200+ reportes Oracle Reports → JasperReports; validación en paralelo y reglas extraídas desde PL/SQL.'
WHERE title = 'Modernización de reportes — Ministerio de Hacienda';

UPDATE projects SET description = 'Integración core bancario ↔ app móvil: SOAP/REST, IBM Cloud y latencia controlada en operaciones críticas.'
WHERE title = 'Integración core bancario y canal móvil';

UPDATE projects SET description = 'Gateway Spring Cloud + Redis + Resilience4j para picos de tráfico sin saturar servicios internos.'
WHERE title = 'API Gateway para picos de tráfico';

UPDATE projects SET description = 'Portafolio full stack open source: Spring Boot, Angular, Docker y CI/CD. Código en GitHub.'
WHERE title = 'Portafolio elgarcia.org';

UPDATE experiences SET description = 'Full Stack (Spring Boot + Angular). APIs REST y modernización Jakarta EE → microservicios.'
WHERE company = 'MayBlue, Caribe';

UPDATE experiences SET description = 'Integración core bancario con canal móvil (SOAP/REST, IBM Cloud, message brokers).'
WHERE company = 'Institución Financiera (Remoto)';

UPDATE experiences SET description = 'Migración masiva de reporting Oracle→Jasper y mantenimiento del sistema financiero (Java/Jakarta EE).'
WHERE company = 'Ministerio de Hacienda de la Rep. Dominicana';

UPDATE experiences SET description = 'Sistemas IT, automatización de procesos e infraestructura para operación diaria.'
WHERE company = 'ASES Manufacturing';

UPDATE articles SET summary = 'Migrar monolitos Java EE sin reescribir todo: Strangler Fig, tests de caracterización y extracción de PL/SQL.'
WHERE slug = 'modernizing-legacy-systems';

UPDATE articles SET summary = 'Signals en Angular 17: estado local más simple y cuándo seguir usando RxJS para HTTP y streams.'
WHERE slug = 'angular-17-signals';

UPDATE articles SET summary = 'Multi-stage builds e imágenes distroless para recortar tamaño y tiempo de despliegue en CI.'
WHERE slug = 'optimizing-docker-builds';

UPDATE articles SET summary = 'Capas de servicio, compilación en arranque y tips para PDFs consistentes con JasperReports.'
WHERE slug = 'mastering-jasperreports';

UPDATE articles SET summary = 'Paquetes core/usecase/adapters en Spring Boot con ejemplos de inversión de dependencias.'
WHERE slug = 'clean-arch-spring';
