# QuantLens AI Change Log

This log records AI-assisted repository changes. It is not a replacement for Git history.

## Unreleased

### Added
- Claude Code governance rules package.
- Initial product context.
- Initial architectural guardrails.
- Initial durable decisions.
- Initial handoff workflow.
- QL-001 repository assessment task.
- QL-002: Spring Boot API (`apps/api`) with `GET /api/v1/system/health`, calling the AI service over REST with a configurable base URL/timeout and reporting `DEGRADED` when it is unavailable; CORS configured for the web origin.
- QL-002: FastAPI AI service (`apps/ai-service`) with a typed, tested `GET /health` endpoint.
- QL-002: React web app (`apps/web`) with Ant Design 5, TanStack Query, and React Router; a "System Status" page calling only the Spring Boot API and rendering loading, error, and degraded states; vitest + Testing Library test setup.
- QL-002: root `docker-compose.yml` orchestrating all three services with health checks.
- QL-002: root `README.md` with architecture, prerequisites, run/test/build commands, service URLs, limitations, and repository structure.
- QL-002: `WEB_ORIGIN` environment variable added to `.env.example` for API-side CORS configuration.
- QL-003: PostgreSQL + HikariCP datasource configuration in `apps/api` (`DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`, `DB_POOL_MAX_SIZE`, `DB_POOL_MIN_IDLE`, `DB_POOL_CONNECTION_TIMEOUT_MS`), fully env-var driven with no hardcoded credentials.
- QL-003: Flyway integration — automatic migration on startup, baseline migration `V1__baseline_schema.sql`, migration naming convention documented in `apps/api/src/main/resources/db/migration/README.md`.
- QL-003: Infrastructure-only schema — `app_settings`, `job_execution`, `model_registry` tables (snake_case, UUID primary keys, `TIMESTAMPTZ` audit columns, indexes on realistic lookup columns).
- QL-003: Spring Data JPA foundation — `com.quantlens.api.common.BaseEntity` (UUID id, `createdAt`/`updatedAt` via `@EnableJpaAuditing`), `hibernate.jdbc.time_zone=UTC`, `ddl-auto=validate` (Flyway owns schema).
- QL-003: Repository layer only — `AppSettingRepository`, `JobExecutionRepository`, `ModelRegistryEntryRepository` (plain `JpaRepository`, no custom queries, no service layer).
- QL-003: `DatabaseHealthChecker` (validates a pooled JDBC connection) wired into `GET /api/v1/system/health`; response now includes a `database` field (`UP`/`DOWN`) alongside the existing `aiService` field; overall `status` is `DEGRADED` if either dependency is down. Database outage does not crash the API.
- QL-003: `docker-compose.yml` `postgres` service (`postgres:17-alpine`, named persistent volume `quantlens-postgres-data`, `pg_isready` healthcheck); `api` now depends on `postgres` being healthy in addition to `ai-service`.

### Changed
- Default API port changed from 8080 to **8086** because port 8080 is already bound by an unrelated local project on this development machine. Updated `apps/api/src/main/resources/application.yml`, `apps/api/Dockerfile` (`EXPOSE`), `apps/web/src/api/systemHealth.ts`, `docker-compose.yml`, `.env.example`, and `README.md` to keep the new default consistent everywhere.

### Fixed
- After the port change, `docker compose up -d` (without `--build`) reused a cached `quantlens-api` image built with the old 8080 default, so Tomcat inside the container listened on 8080 while the Compose port mapping and healthcheck expected 8086 — the container never became reachable and the frontend reported "check system status fail". Rebuilt with `docker compose up -d --build` to resolve; the API is now healthy and reachable on 8086.
- QL-003: HikariCP's default `connection-timeout` (30s) made `GET /api/v1/system/health` take ~30s to report `DEGRADED` while PostgreSQL was down. Added an explicit `DB_POOL_CONNECTION_TIMEOUT_MS` (default 3000ms) so the health check fails fast instead.

### Design decisions
- QL-003 extends the existing `SystemHealthResponse` contract (adds a `database: "UP"|"DOWN"` field) rather than replacing it with the task spec's illustrative `{"application","database","aiService"}` example shape. The existing `status`/`service`/`aiService`/`version`/`timestamp` fields established in QL-002 are preserved as-is to avoid an unannounced breaking API change (`RULES.md` §12 requires approval for breaking API changes); the spec's own wording says "Extend," not "replace." Overall `status` is now `DEGRADED` if either `database` or `aiService` is down.
- Root `README.md` was intentionally **not** updated in QL-003 — the task's Documentation section (§9) lists only `PROJECT_STATE.md`, `CODE_MAP.md`, `CHANGELOG.md`, and `TASKS.md`. `README.md` still says "No PostgreSQL ... integration yet," which is now stale; flagged here rather than silently expanding scope.

### Verified
- QL-002 full end-to-end lifecycle verified live via `docker compose`: `GET /health` (AI service) and `GET /api/v1/system/health` (API) both confirmed against the running containers; the web app confirmed serving its shell and, via the existing component test suite, correctly rendering loading/UP/DEGRADED/error states.
- Degraded flow: stopping `ai-service` (`docker compose stop ai-service`) leaves the API container up and healthy, with `GET /api/v1/system/health` returning `status: DEGRADED`, `aiService.status: DOWN`.
- Recovery flow: restarting `ai-service` (`docker compose start ai-service`) returns the full system to `status: UP`, `aiService.status: UP`.
- Full backend/AI/frontend/Docker validation suite re-run and passing: `./mvnw test` (4/4), `./mvnw package` (BUILD SUCCESS), `pytest` (2/2), `npm run lint`, `npm run test -- --run` (4/4), `npm run build`, `docker compose config`.
- QL-003 verified live via `docker compose up --build`: Flyway applied `V1__baseline_schema.sql` on startup (confirmed in `flyway_schema_history`); `app_settings`, `job_execution`, `model_registry` tables exist with expected columns/indexes/constraints (confirmed via `psql \d`); Hibernate `ddl-auto=validate` passed (app started successfully) and `GET /api/v1/system/health` returned `database: "UP"`.
- QL-003 degraded/recovery flow verified live: `docker compose stop postgres` → API container stayed `Up`/`healthy`, `GET /api/v1/system/health` returned `status: DEGRADED`, `database: "DOWN"` in ~3s; `docker compose start postgres` → full system returned to `status: UP`, `database: "UP"`.

### Known limitations
- No Redis, MinIO, or authentication integration yet (out of scope for QL-003 by design).
- `apps/web` has no charting, state-management (Zustand), form, or validation libraries yet — none were needed for the QL-002 slice and QL-003 did not touch the frontend.
- Port 8000 (AI service) may still be occupied by another unrelated local project on this machine; override `AI_SERVICE_PORT` in `.env` if needed. The committed `.env.example` defaults are otherwise correct for a clean machine.
- No interactive browser/display is available in this environment; the React status page was verified via HTTP-level checks (correct HTML shell served) plus the existing automated component test suite (loading/UP/DEGRADED/error rendering), not a live browser session.
- No repository-layer automated tests (e.g. `@DataJpaTest`) were added — doing so would require a new test-scope dependency (H2 or Testcontainers) not requested by the QL-003 spec and not otherwise justified; persistence correctness was instead verified live against the real PostgreSQL container (migration, schema, and Hibernate validation all confirmed, see Verified above).
