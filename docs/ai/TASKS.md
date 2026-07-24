# QuantLens Tasks

## Current

None. QL-002 is complete; awaiting architecture reviewer approval of the next task.

## Next

To be approved by the architecture reviewer. Likely candidates:

- [ ] QL-003 — Initialize PostgreSQL and migration baseline.
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
