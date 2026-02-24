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
| `CORS_ALLOWED_ORIGINS`| Origen permitido (URL del frontend)     |

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
[contact@eleazargarcia.com](mailto:contact@eleazargarcia.com) · [LinkedIn](https://linkedin.com/in/eleazar-garcia) · [GitHub](https://github.com/eleazar-garcia)
