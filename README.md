# Portfolio Dev — Eleazar Garcia

Portfolio profesional Full Stack construido con **Spring Boot 3** y **Angular 17**, siguiendo Clean Architecture y con CI en GitHub Actions.

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
- **CI**: GitHub Actions — build + test en cada push (`ci.yml`)
- **CD**: GitHub Actions — backend a **Google Cloud Run** (`backend-deploy.yml`) y frontend a **Firebase Hosting** (`frontend-deploy.yml`) solo en ramas `master`/`main` y cuando cambian `backend/**` o `frontend/**`
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

Spring Boot lee la configuración típica de datasource con estas variables (equivalentes a `spring.datasource.*`):

| Variable                    | Descripción                             |
|-----------------------------|-----------------------------------------|
| `SPRING_DATASOURCE_URL`     | JDBC URL de PostgreSQL (p. ej. Supabase) |
| `SPRING_DATASOURCE_USERNAME`| Usuario de la base de datos             |
| `SPRING_DATASOURCE_PASSWORD`| Contraseña de la base de datos          |
| `CORS_ALLOWED_ORIGINS`      | Orígenes del frontend, separados por coma. Ej.: `https://elgarcia.org,https://www.elgarcia.org,https://portfolio-eleazar-garcia.web.app` |
| `ADMIN_API_KEY`             | **Obligatorio en prod.** Clave para `POST`/`PUT`/`DELETE` en `/api/projects`, `/api/skills`, `/api/experiences`. Enviar cabecera `X-API-Key`. |

### Backend — perfil `local` y Google Calendar (opcional)

Con `application-local.yml`, la agenda puede usar **ADC** (sin JSON en el repo): en tu máquina ejecuta `gcloud auth application-default login` y define el calendario con variables de entorno si hace falta:

| Variable | Descripción |
|----------|-------------|
| `GOOGLE_CALENDAR_ENABLED` | En local el valor por defecto es **activar** calendario (`true`); pon `false` si no tienes ADC y quieres arrancar sin Google Calendar. |
| `GOOGLE_CALENDAR_ID` | `primary` o el correo completo del calendario (en **Google Workspace**, si aparece 403 al crear eventos, prueba el correo en lugar de `primary`). |
| `GOOGLE_CALENDAR_APP_NAME` | Opcional; nombre que verás en la consola de Google (por defecto `portfolio-backend`). |

En **Cloud Run**, el workflow `backend-deploy.yml` también fija `SPRING_PROFILES_ACTIVE=prod,supabase` (seguridad + pool Hikari para Postgres remoto).

En desarrollo local (`spring.profiles.active` distinto de `prod`) las escrituras no exigen API key salvo que configures `app.security.require-api-key-for-writes=true`.

En producción (p. ej. **Google Cloud Run**), define `ADMIN_API_KEY` como variable de entorno del servicio (valor secreto y largo).

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

### GitHub Actions — secrets para CD

Configura en **Settings → Secrets and variables → Actions** (valores nunca en el código):

| Secreto | Uso |
|---------|-----|
| `GCP_PROJECT_ID` | ID del proyecto en Google Cloud (p. ej. `portafolio-492015`). |
| `GCP_SA_KEY` | JSON de la cuenta de servicio de GCP con permisos para Cloud Build, Artifact Registry y Cloud Run (desde IAM → Service accounts → Keys). |
| `SPRING_DATASOURCE_URL` | JDBC completo hacia Postgres (Supabase u otro), p. ej. `jdbc:postgresql://....pooler.supabase.com:6543/postgres?sslmode=require`. |
| `SPRING_DATASOURCE_USERNAME` | Usuario JDBC (en Supabase suele ser `postgres.xxxx` con pooler). |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos. |
| `CORS_ALLOWED_ORIGINS` | Lista separada por comas, sin espacios problemáticos al pegar: `https://elgarcia.org,https://www.elgarcia.org,https://portfolio-eleazar-garcia.web.app`. |
| `ADMIN_API_KEY` | Misma clave larga que usarás en el panel admin del sitio (`X-API-Key`). |
| `GOOGLE_CALENDAR_ENABLED` | **Obligatorio para la agenda en prod:** `true`. Si no creas este secret, el despliegue envía `false` y `/api/meetings` responderá 503 (“calendario no habilitado”). |
| `GOOGLE_CALENDAR_ID` | *(Opcional)* ID del calendario destino (p. ej. `primary` o el correo del calendario compartido con la cuenta de servicio de Cloud Run). Si no existe, se usa `primary`. |
| `FIREBASE_SERVICE_ACCOUNT_PORTFOLIO_ELEAZAR_GARCIA` | JSON de la cuenta de servicio de Firebase (Hosting). Debe coincidir con el nombre usado en `frontend-deploy.yml`. Puedes generar o enlazar el repo con `firebase init hosting:github` desde `frontend/` o crear la clave en la consola de Firebase/Google Cloud. |

**GCP (una vez):** crea en Artifact Registry un repositorio Docker llamado `backend-repo` en `us-central1` (o cambia `AR_REPO` en `backend-deploy.yml`). La imagen publicada es `us-central1-docker.pkg.dev/<GCP_PROJECT_ID>/backend-repo/portfolio-backend:latest`.

**Seguridad:** si alguna clave llegó a filtrarse (chat, issue, commit), **rótala** en GCP/Firebase/GitHub y vuelve a guardar el secreto.

**Agenda y 503:** En **Cloud Run**, si ves “Google Calendar no está habilitada”, define el secret `GOOGLE_CALENDAR_ENABLED`=`true` y redepliega. Si ves “No se pudo consultar la disponibilidad”, en **Google Cloud Console** habilita **Google Calendar API** en el mismo proyecto que usa el backend, y comparte el calendario con el **email de la cuenta de servicio** del servicio Cloud Run (IAM → cuenta de servicio del servicio → copiar email). En local, con `gcloud auth application-default login`, el mismo proyecto debe tener la API habilitada; revisa el cuerpo del 503 con `app.error.expose-details=true` (por defecto en local) para ver el código HTTP de Google.

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
│   ├── ci.yml                 # Build + test en cada push
│   ├── backend-deploy.yml     # CD: Cloud Run (solo cambios en backend/)
│   └── frontend-deploy.yml    # CD: Firebase Hosting (solo cambios en frontend/)
├── docker-compose.yml
└── start.ps1                 # Script de inicio local (Windows)
```

---

## Autor

**Eleazar Garcia** — Senior Full Stack Java Developer  
[hola@elgarcia.org](mailto:hola@elgarcia.org) · [LinkedIn](https://www.linkedin.com/in/garciaeleazar/) · [GitHub](https://github.com/happyDevRD)
