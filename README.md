# Full Stack Portfolio Project

A comprehensive portfolio application showcasing Clean Architecture, modern Full Stack development, and DevOps practices.

## 🏗 Architecture

### Backend (Spring Boot 3)
- **Architecture**: Hexagonal / Clean Architecture (Domain, Use Cases, Adapters, Infrastructure).
- **Tech Stack**: Java 17, Spring Boot 3.2, Spring Data JPA, H2 Database (Dev).
- **Features**: REST APIs, OpenAPI (Swagger), Data Seeding (`DataLoader`).

### Frontend (Angular 17)
- **Architecture**: Component-based SPA using Standalone Components.
- **Tech Stack**: Angular 17, TypeScript, SCSS, RxJS.
- **Features**: Reactive Forms, Dark Mode, Print-ready Resume, Adaptive Grid.

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose (Recommended)
- OR Java 17+ and Node.js 18+ for manual run.

### Quick Start (Docker)
1. Clone the repository.
2. Run the startup script (Linux/Mac):
   ```bash
   ./start_project.sh
   ```
   Or using Docker Compose directly:
   ```bash
   docker-compose up --build
   ```
3. Access the application:
   - **Frontend**: http://localhost:4200
   - **Backend API**: http://localhost:8080/api
   - **Swagger UI**: http://localhost:8080/swagger-ui.html

## ✨ Features
- **Project Showcase**: Filterable list of projects with tag detection.
- **Virtual Resume**: Printable CV page generated from real data.
- **Clean UI**: Responsive design with Dark Mode support.
- **Contact Form**: Reactive form with validation.

## 🧪 Testing
- **Frontend**: Run `ng test` to execute Jasmine/Karma unit tests.
- **Backend**: Run `mvn test`.

## 👤 Author
**Eleazar Garcia** - Senior Full Stack Java Developer
