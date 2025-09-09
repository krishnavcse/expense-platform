# Expense Platform (Modular Monolith)

A multi-module Spring Boot 3 application (Java 21) for expense tracking. The codebase is organized into modules for user management, expense management, shared components, internationalization, and the application bootstrap.

## Key Features

- **Architecture**: Maven multi-module modular monolith
- **Runtime**: Spring Boot 3.3.x, Java 21
- **Persistence**: Spring Data JPA
  - Dev: H2 in-memory
  - Prod: PostgreSQL (via Docker Compose)
- **Validation**: Jakarta Bean Validation
- **Security**: JWT-based authentication (jjwt)
- **Documentation**: OpenAPI via springdoc (Swagger UI)
- **Migrations**: Flyway SQL migrations (`app/src/main/resources/db/migration`)
- **Observability**: Spring Boot Actuator + Prometheus metrics
- **Testing**: JUnit 5, Mockito
- **Coverage**: JaCoCo per-module and aggregate reports

## Modules

- **common**: Shared DTOs, validation, and utilities
- **user**: User domain (entities, repositories, services)
- **expense**: Expense domain (entities, repositories, services)
- **i18n**: Message source configuration and internationalization support
- **app**: Spring Boot application module; wires everything together

Dependency direction: `app` depends on `common`, `user`, `expense`, `i18n`. Feature modules depend only on `common`.

## Getting Started

### Prerequisites
- Java 21
- Maven 3.9+

### Build

```bash
mvn -q -f expense-platform/pom.xml clean verify
```

- Per-module tests and coverage are executed.
- Aggregated coverage report: `expense-platform/target/site/jacoco-aggregate/index.html`.

### Run

Option 1: Run from your IDE
1. Open the project.
2. Run the main class: `com.demo.expense.app.ExpensePlatformApplication`.
3. Use profile `dev` for the default H2 configuration.

Option 2: Run via Maven (dev profile)
```bash
mvn -q -f expense-platform/pom.xml -pl app spring-boot:run -Dspring-boot.run.profiles=dev
```

Option 3: Run the packaged JAR
```bash
java -jar expense-platform/app/target/app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Docker (Production-like)

Use Docker Compose to run PostgreSQL and the app (prod profile):

```bash
cd expense-platform
# Ensure JWT_SECRET_BASE64 is set to a strong Base64-encoded secret
set JWT_SECRET_BASE64=REPLACE_WITH_BASE64_SECRET
# or on Linux/macOS: export JWT_SECRET_BASE64=...

docker compose build
docker compose up
```

- App: http://localhost:8080
- Actuator (management): http://localhost:8081/actuator
- Prometheus scrape endpoint: http://localhost:8081/actuator/prometheus

## Configuration

Profiles used:
- `dev`: H2 in-memory DB, H2 console enabled, actuator endpoints exposed for local use.
- `prod`: PostgreSQL, Flyway migrations, actuator on management port 8081.

Files:
- Dev: `app/src/main/resources/application-dev.yml`
- Prod: `app/src/main/resources/application-prod.yml`

Example (prod) highlights:

```yaml
server:
  port: 8080

management:
  server:
    port: 8081
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: health,info,metrics,prometheus

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/expense
    username: expense
    password: expense

security:
  jwt:
    secret-base64: ${JWT_SECRET_BASE64}
```

## Endpoints (dev defaults)

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console
- Actuator base: http://localhost:8080/actuator
  - Health: `/actuator/health`
  - Metrics: `/actuator/metrics`
  - Prometheus: `/actuator/prometheus`

## Testing & Coverage

- Run all tests and generate coverage:
```bash
mvn -q -f expense-platform/pom.xml clean verify
```
- Per-module reports under `*/target/site/`
- Aggregate report: `expense-platform/target/site/jacoco-aggregate/index.html`

## Environment variables

- **SPRING_PROFILES_ACTIVE**: `dev` or `prod`
- **JWT_SECRET_BASE64**: Base64-encoded signing secret (required for `prod`)
- **Database (prod)**: Uses `jdbc:postgresql://postgres:5432/expense` by default in Docker Compose

## API docs and authentication

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **Authorization header**: `Authorization: Bearer <token>`

## Observability

- **Dev** (port 8080): `/actuator`, `/actuator/health`, `/actuator/metrics`, `/actuator/prometheus`
- **Prod** (management port 8081): `http://localhost:8081/actuator`
- **Build info**: `/actuator/info`

## Useful commands

- **Build all modules**:
  ```bash
  mvn -q -f expense-platform/pom.xml clean verify
  ```
- **Run (dev)**:
  ```bash
  mvn -q -f expense-platform/pom.xml -pl app spring-boot:run -Dspring-boot.run.profiles=dev
  ```
- **Run packaged (dev)**:
  ```bash
  java -jar expense-platform/app/target/app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
  ```
- **Coverage report**: `expense-platform/target/site/jacoco-aggregate/index.html`

## Troubleshooting

- **Actuator 401/403**: Ensure security allows `/actuator/health/**`, `/actuator/info`, `/actuator/metrics`, `/actuator/prometheus`.
- **H2 Console**: http://localhost:8080/h2-console; JDBC `jdbc:h2:mem:expensedb` if default is changed.
- **Docker build**: Ensure `JWT_SECRET_BASE64` is set before `docker compose build`.
- **Port conflicts**: Adjust `server.port` or `management.server.port` in `application-*.yml`.

## Minimal structure

```
expense-platform/
  app/        # Boot app, security, controllers, config
  common/     # DTOs, shared validation/util
  user/       # User domain
  expense/    # Expense domain
  i18n/       # Message sources
  docker-compose.yml
  pom.xml     # Parent multi-module POM
```

## Notes

- Flyway migration scripts reside in `app/src/main/resources/db/migration`. The baseline example is `V1__init.sql`.
- For non-local usage, ensure `JWT_SECRET_BASE64` is strong and stored securely.
