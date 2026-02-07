# Grab Resale - Backend API

Spring Boot 4.0.2 / Java 21 backend for the Grab Resale portal.

## Tech Stack

| Category       | Technology                          |
|----------------|-------------------------------------|
| Framework      | Spring Boot 4.0.2, Spring Framework 7 |
| Language       | Java 21                             |
| Database       | PostgreSQL 17                       |
| ORM            | Hibernate 7 / Spring Data JPA       |
| Migrations     | Flyway                              |
| Security       | Spring Security 7 (stateless)       |
| API Docs       | springdoc-openapi 3.0.1 / Swagger UI |
| HTTP Client    | Spring Cloud OpenFeign              |
| Build          | Maven                               |

## Getting Started

### Prerequisites

- Java 21+
- Docker & Docker Compose
- Maven (or use the included `./mvnw` wrapper)

### Run Locally

```bash
# 1. Start PostgreSQL
docker compose up -d

# 2. Copy and edit environment variables (optional — defaults match compose)
cp .env.example .env

# 3. Run the application with dev profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Swagger UI

Once running, open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Project Structure

```
src/main/java/com/vonnue/grab_resale/
├── common/              Utilities (constants, security, slug)
├── config/              Spring configuration classes
├── domain/              JPA entities
├── exception/           Custom exceptions + global handler
├── repository/          Spring Data JPA repositories
├── service/             Business logic
│   ├── facade/          Facade services
│   └── impl/            Service implementations
└── web/
    ├── controller/      REST controllers
    └── dto/             Request/response DTOs
```

## Configuration

### Profiles

| Profile | Activation                            | Purpose                                    |
|---------|---------------------------------------|--------------------------------------------|
| (none)  | default                               | Base config with sensible local defaults   |
| `dev`   | `-Dspring-boot.run.profiles=dev`      | SQL logging, DEBUG level, full actuator    |
| `prod`  | `SPRING_PROFILES_ACTIVE=prod`         | Larger pool, WARN logging, restricted actuator |
| `test`  | `@ActiveProfiles("test")`             | Separate DB, `create-drop`, Flyway off     |

### Environment Variables

| Variable               | Default                  | Description                |
|------------------------|--------------------------|----------------------------|
| `DB_HOST`              | `localhost`              | PostgreSQL host            |
| `DB_PORT`              | `5432`                   | PostgreSQL port            |
| `DB_NAME`              | `grab_resale-db`         | Database name              |
| `DB_USERNAME`          | `postgres`               | Database user              |
| `DB_PASSWORD`          | `postgres`               | Database password          |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000`  | Comma-separated origins    |

## Date/Time Conventions

| Use Case                          | Type             | Reason                                      |
|-----------------------------------|------------------|---------------------------------------------|
| Audit fields (createdAt, updatedAt) | `Instant`       | Always UTC, maps to `TIMESTAMP_UTC`         |
| User-visible timestamps           | `OffsetDateTime` | Preserves the offset context                |
| Date-only (birthday, expiry)      | `LocalDate`      | Maps to SQL `DATE`                          |
| Time-only (recurring schedule)    | `LocalTime`      | Maps to SQL `TIME`                          |
| DTOs / API responses              | `Instant`        | Clean ISO-8601 output                       |

Avoid `LocalDateTime` for anything that crosses timezone boundaries.

All timestamps are stored in UTC. Jackson 3 serializes `java.time` types as ISO-8601 strings by default.

## DTO Conventions

| Layer          | Approach | Reason                              |
|----------------|----------|-------------------------------------|
| `web/dto/`     | Records  | Immutable, no JPA constraints       |
| `domain/`      | Lombok   | JPA needs mutability + no-arg ctor  |
| Value objects   | Records  | Pure data carriers                  |

### Standard Response Wrappers

- `ApiResponse<T>` — single-item response with optional message and timestamp
- `PageResponse<T>` — paginated response wrapping Spring's `Page<T>`

## Error Handling

All errors follow RFC 7807 (`ProblemDetail`):

| Exception                          | HTTP Status | When                        |
|------------------------------------|-------------|-----------------------------|
| `ResourceNotFoundException`        | 404         | Entity not found by ID      |
| `BadRequestException`              | 400         | Invalid business logic      |
| `MethodArgumentNotValidException`  | 400         | Bean validation failure     |
