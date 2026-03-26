# Portfolio Dev — Eleazar Garcia

Portfolio profesional Full Stack construido con **Spring Boot 3** y **Angular 17**, siguiendo Clean Architecture y con CI/CD automatizado hacia Render.

[![CI — Build & Test](https://github.com/happyDevRD/portfolio-dev/actions/workflows/ci.yml/badge.svg)](https://github.com/happyDevRD/portfolio-dev/actions/workflows/ci.yml)

---

## Arquitectura

### Backend (Spring Boot 3 / Java 17)
- **Patrón**: Clean Architecture (Domain → Use Cases → Adapters → Infrastructure)
- **Stack**: Java 17, Spring Boot 3.2, Spring Data JPA, H2 (dev), PostgreSQL (prod)
- **Features**: REST APIs, OpenAPI/Swagger, validación con Bean Validation, Spring Actuator

### Frontend (Angular 17)
- **Patrón**: Standalone Components, Lazy Loading por ruta
- **Stack**: Angular 17, TypeScript, SCSS, RxJS
- **Features**: Formularios reactivos, CV imprimible, grid de proyectos con filtros dinámicos, blog con Markdown

### DevOps
- **CI**: GitHub Actions — build + test en cada push
- **CD**: Deploy automático a Render cuando el CI pasa
- **Contenedores**: Docker Compose con PostgreSQL para entorno local completo

---

## Inicio rápido

### Requisitos previos
- Java 17+
- Node.js 20+ y npm
- (Opcional) Docker & Docker Compose

### Opción 1 — Script local (Windows)

```powershell
.\start.ps1
```

Esto levanta el backend en `http://localhost:8080` y el frontend en `http://localhost:4200`.

> **Nota**: edita `start.ps1` con las rutas de Java y Maven correctas para tu máquina.

### Opción 2 — Docker Compose (entorno completo con PostgreSQL)

```bash
docker-compose up --build
```

Servicios disponibles:

| Servicio   | URL                                  |
|------------|--------------------------------------|
| Frontend   | http://localhost:80                  |
| Backend    | http://localhost:8080/api            |
| Swagger UI | http://localhost:8080/swagger-ui.html|
| H2 Console | http://localhost:8080/h2-console (solo perfil dev) |

### Opción 3 — Manual

**Backend:**
```bash
cd backend
mvn spring-boot:run
```

**Frontend:**
```bash
cd frontend
npm install
npm start
```

---

## Variables de entorno

### Backend — perfil `prod`

| Variable              | Descripción                             |
|-----------------------|-----------------------------------------|
| `DATABASE_URL`        | JDBC URL de PostgreSQL                  |
| `DATABASE_USERNAME`   | Usuario de la base de datos             |
| `DATABASE_PASSWORD`   | Contraseña de la base de datos          |
| `CORS_ALLOWED_ORIGINS`| Orígenes del frontend, separados por coma. Ej.: `https://elgarcia.org,https://www.elgarcia.org` |
| `ADMIN_API_KEY`       | **Obligatorio en prod.** Clave para `POST`/`PUT`/`DELETE` en `/api/projects`, `/api/skills`, `/api/experiences`. Enviar cabecera `X-API-Key`. |

En desarrollo local (`spring.profiles.active` distinto de `prod`) las escrituras no exigen API key salvo que configures `app.security.require-api-key-for-writes=true`.

En **Render**, añade `ADMIN_API_KEY` en el panel de Environment del servicio backend (valor secreto y largo).

**Documentación API:** en **prod**, Swagger UI y `/v3/api-docs` están **desactivados** por seguridad. En local (`http://localhost:8080/swagger-ui.html`) puedes usar el botón *Authorize* y la cabecera `X-API-Key` para probar `POST`/`PUT`/`DELETE` cuando actives la API key.

**Métricas:** con el perfil por defecto (dev), Actuator expone también Prometheus en `/actuator/prometheus`. En **prod** solo se exponen `health` e `info` (sin `/actuator/prometheus` público).

### Frontend — `environment` / `environment.prod.ts`

- **`siteUrl`**: URL pública del frontend (canonical, Open Graph). Debe coincidir con el dominio desplegado.
- **`defaultOgImagePath`**: ruta bajo el sitio para la imagen por defecto al compartir enlaces (`src/assets/og-default.png`); puedes sustituirla por tu propio diseño (recomendado ~1200×630 px).
- **`umamiWebsiteId`**: opcional. Si pones el ID de [Umami](https://umami.is), el `AppComponent` carga el script de `cloud.umami.is` (la CSP de `nginx.conf` ya lo permite).

### Base de datos — perfil `prod`

- **Flyway** ejecuta los scripts en `backend/src/main/resources/db/migration/`. La primera migración crea el esquema completo en PostgreSQL.
- Con `prod`, Hibernate usa `ddl-auto: validate` (no modifica el esquema; debe coincidir con lo aplicado por Flyway).
- En desarrollo (H2), Flyway está **desactivado** y se sigue usando `ddl-auto: create-drop`.
- Si tu PostgreSQL de producción se creó antes solo con Hibernate (`update`) y nunca con Flyway, puede hacer falta un **baseline** manual o ajustar el esquema antes de desplegar; las instalaciones nuevas aplican `V1__initial_schema.sql` solas.

### GitHub Actions — Secrets requeridos

| Secret                        | Descripción                          |
|-------------------------------|--------------------------------------|
| `RENDER_BACKEND_DEPLOY_HOOK`  | URL del deploy hook de Render (backend)  |
| `RENDER_FRONTEND_DEPLOY_HOOK` | URL del deploy hook de Render (frontend) |

> Los secrets se configuran en **Settings → Secrets and variables → Actions** del repositorio de GitHub.

---

## Tests

```bash
# Backend
cd backend && mvn test

# Frontend
cd frontend && ng test --watch=false
```

El test `FlywayPostgresIntegrationTest` levanta PostgreSQL con **Testcontainers** y valida Flyway + Hibernate en un entorno cercano a producción. **Requiere Docker** (en CI suele estar disponible; sin Docker, ese test se omite automáticamente).

---

## Estructura del proyecto

```
portfolio-dev/
├── backend/                  # Spring Boot — Clean Architecture
│   ├── src/main/java/com/portfolio/
│   │   ├── core/             # Dominio: modelos, interfaces, casos de uso
│   │   ├── application/      # Implementaciones de casos de uso
│   │   ├── adapters/         # Persistencia JPA
│   │   └── infrastructure/   # Controladores REST, configuración
│   └── Dockerfile
├── frontend/                 # Angular 17 — Standalone Components
│   ├── src/app/
│   │   ├── core/             # Modelos, servicios, animaciones
│   │   ├── features/         # Páginas: home, blog, projects, resume, contact
│   │   └── shared/           # Componentes reutilizables
│   └── Dockerfile
├── .github/workflows/
│   ├── ci.yml                # Build + test en cada push
│   └── cd.yml                # Deploy a Render si CI pasa
├── docker-compose.yml
└── start.ps1                 # Script de inicio local (Windows)
```

---

## Autor

**Eleazar Garcia** — Senior Full Stack Java Developer  
[hola@elgarcia.org](mailto:hola@elgarcia.org) · [LinkedIn](https://www.linkedin.com/in/garciaeleazar/) · [GitHub](https://github.com/happyDevRD)
