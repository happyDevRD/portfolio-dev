-- Mejora descripciones de proyectos: métricas al frente, contexto después.
UPDATE projects
SET description = '−60% en tiempo de generación de PDFs. 200+ reportes migrados de Oracle Reports a JasperReports con validación salida a salida para el Ministerio de Hacienda.'
WHERE title = 'Modernización de reportes — Ministerio de Hacienda';

UPDATE projects
SET description = 'p99 < 300ms con 50k+ transacciones/día en producción. Puente SOAP/REST entre core bancario y canal móvil sobre IBM Cloud con zero downtime en migración.'
WHERE title = 'Integración core bancario y canal móvil';

UPDATE projects
SET description = 'Rate limiting distribuido con Redis y circuit breakers Resilience4j. Gateway Spring Cloud diseñado para absorber picos de miles de peticiones sin degradar servicios internos.'
WHERE title = 'API Gateway para picos de tráfico';
