# QuantLens Project State

Last updated: 2026-07-24 (QL-003 — Database Foundation).

## Current phase

QL-003 complete: PostgreSQL + Flyway + Spring Data JPA database foundation established. Infrastructure-only schema (`app_settings`, `job_execution`, `model_registry`); no business, market-data, or prediction logic.

## Current status

The repository URL is:

```text
https://github.com/bigben113/Quantlens
```

`apps/api`, `apps/ai-service`, and `apps/web` are initialized and runnable, individually and via `docker compose up --build`. `apps/api` now connects to PostgreSQL via HikariCP, runs Flyway migrations automatically on startup, and exposes JPA repositories for three infrastructure tables. `GET /api/v1/system/health` now also reports database status and degrades gracefully (without crashing) when PostgreSQL is unavailable. No cache, object storage, authentication, market data, or ML model has been introduced yet.

## Completed

- Product direction agreed.
- Technology stack agreed.
- Architecture guardrails agreed.
- Claude working rules prepared.
- QL-001 — repository assessment and governance bootstrap.
- QL-002 — full-stack health vertical slice (Web → API → AI Service), with tests passing and Docker Compose orchestration verified end to end, including the degraded/recovery lifecycle.
- QL-003 — database foundation: PostgreSQL, Flyway baseline migration, Spring Data JPA with UUID `BaseEntity` and auditing, `app_settings`/`job_execution`/`model_registry` tables and repositories, `/api/v1/system/health` extended with a `database` status, `docker-compose.yml` `postgres` service with persistent volume and healthcheck. Verified live: Flyway migration applied, tables created, app started, Docker Compose healthy, and the health endpoint correctly reports `DEGRADED`/`database: DOWN` (without crashing) when PostgreSQL is stopped, then recovers to `UP` when it restarts.

## In progress

- None. Awaiting architecture reviewer approval of the next task.

## Blocked

- None. Port 8080 (API) was already bound by an unrelated local project on this machine, so the default API port is **8086** (documented previously). Port 8000 (AI service) may also be occupied by another unrelated local project; override `AI_SERVICE_PORT` in `.env` if needed. Port 5432 (PostgreSQL) was free on this machine; override `DB_PORT` in `.env` if it later conflicts.

## Next milestone

Architecture reviewer to approve the next task (candidates: Spring Boot API foundation hardening, FastAPI provider groundwork, or authentication foundation — see `TASKS.md`).

## Approved baseline versions

Exact versions selected and verified in this environment:

- Java: 25.0.3 (Eclipse Temurin)
- Maven: 3.9.9 (via Maven Wrapper)
- Spring Boot: 3.5.16
- Spring Data JPA / Hibernate ORM: 6.6.53.Final (via spring-boot-starter-data-jpa 3.5.16)
- Flyway: flyway-core + flyway-database-postgresql (versions managed by Spring Boot 3.5.16 BOM)
- PostgreSQL JDBC driver: managed by Spring Boot 3.5.16 BOM
- PostgreSQL server: 17 (`postgres:17-alpine` image), confirmed `PostgreSQL 17.10` at runtime
- springdoc-openapi: 2.8.17
- Python: 3.13.2
- FastAPI: 0.139.2
- Pydantic: 2.13.4
- pydantic-settings: 2.14.2
- Node.js: 20.19.4
- npm: 10.8.2
- React: 19.2.7
- Vite: 8.1.1 (web app)
- Ant Design: 5.29.3
- @tanstack/react-query: 5.101.4
- react-router-dom: 7.18.1
- Docker: 29.6.2 / Docker Compose: v5.3.1

## Known product defaults

- Prediction horizon: 5 trading sessions.
- Recommendation confidence threshold: 60%, configurable.
- Virtual capital: 100,000,000 VND.
- Baseline model: Logistic Regression.
- First challenger: XGBoost.
