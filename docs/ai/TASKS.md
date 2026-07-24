# QuantLens Tasks

## Current

None. QL-003 is complete; awaiting architecture reviewer approval of the next task.

## Next

To be approved by the architecture reviewer. Likely candidates:

- [ ] QL-004 — Harden Spring Boot API foundation (error contract, tracing/logging conventions).
- [ ] QL-005 — FastAPI provider groundwork (`DataProvider` abstraction, no live provider yet).
- [ ] QL-006 — Authentication foundation (Spring Security integration point).

## Completed

- [x] **QL-001 — Assess repository and bootstrap Claude governance files**

- [x] **QL-002 — Full-Stack Foundation Vertical Slice**
  - Specification: `docs/ai/tasks/QL-002_FULL_STACK_FOUNDATION.md`
  - `apps/api` (Spring Boot), `apps/ai-service` (FastAPI), and `apps/web` (React) initialized and runnable.
  - `GET /api/v1/system/health` (API) calls `GET /health` (AI service) over REST with a configurable base URL/timeout; reports `DEGRADED` without crashing when the AI service is unavailable.
  - React "System Status" page calls only the Spring Boot API and renders Web/API/AI statuses with loading, error, and degraded states.
  - Root `docker-compose.yml` orchestrates all three services with health checks; verified end to end.
  - Degraded/recovery lifecycle verified live: stopping `ai-service` leaves the API available and reporting `DEGRADED` (`aiService.status: DOWN`); restarting `ai-service` returns the full system to `UP`.
  - All relevant tests, lint, and builds pass (see handoff report for exact commands and results).

- [x] **QL-003 — Database Foundation**
  - Specification: `docs/ai/tasks/QL-003_DATABASE_FOUNDATION.md`
  - PostgreSQL configured as the primary database via `apps/api` (HikariCP pool, fully env-var driven, no hardcoded credentials).
  - Flyway integrated: automatic migration on startup, `V1__baseline_schema.sql` baseline migration, naming convention documented in `apps/api/src/main/resources/db/migration/README.md`.
  - Infrastructure-only schema created: `app_settings`, `job_execution`, `model_registry` — no market-data, prediction, or business tables.
  - Spring Data JPA configured with auditing, UTC timestamps, and UUID primary keys via a reusable `BaseEntity`.
  - Repository layer only (`AppSettingRepository`, `JobExecutionRepository`, `ModelRegistryEntryRepository`) — no services, no business logic.
  - `GET /api/v1/system/health` extended with a `database` status field; verified live that a stopped database degrades the API to `DEGRADED` (`database: DOWN`) without crashing it, then recovers to `UP`.
  - `docker-compose.yml` extended with a `postgres` service (persistent named volume, healthcheck, startup ordering via `depends_on: condition: service_healthy`).
  - All relevant tests, lint, and builds pass (see handoff report for exact commands and results).
