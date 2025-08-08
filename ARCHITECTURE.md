# Architecture Overview

## Style: Modular Monolith (Java 21 + Spring Boot 3)
- Modules: common, user, expense, i18n, app
- Dependencies: feature modules depend only on common; app depends on all

## Key Decisions
- Domain separation: user vs expense bounded contexts
- DTOs in common for inter-module contracts
- Security via JWT; method-level enforcement possible
- Persistence with JPA; dev H2, prod Postgres via Flyway
- API docs via springdoc; coverage via JaCoCo aggregate

## Build & Run
- `mvn -f expense-platform/pom.xml clean verify`
- Swagger UI: `/swagger-ui/index.html`
- Actuator: `/actuator` (prod profile controls exposure)

## Future Enhancements
- Testcontainers for integration testing
- Caching (Redis), rate limiting
- Observability: metrics, tracing