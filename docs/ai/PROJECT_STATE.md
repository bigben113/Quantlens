# QuantLens Project State

Last updated: 2026-07-24 (QL-002 — full end-to-end verification, including degraded-flow and recovery).

## Current phase

QL-002 complete: a runnable, tested, end-to-end health/status vertical slice across Web → API → AI Service. Full lifecycle verified via Docker Compose: UP → AI service stopped (API reports DEGRADED, stays available) → AI service restarted (full system returns to UP).

## Current status

The repository URL is:

```text
https://github.com/bigben113/Quantlens
```

`apps/api`, `apps/ai-service`, and `apps/web` are initialized and runnable, individually and via `docker compose up --build`. No database, cache, object storage, authentication, market data, or ML model has been introduced — this remains strictly a foundation vertical slice.

## Completed

- Product direction agreed.
- Technology stack agreed.
- Architecture guardrails agreed.
- Claude working rules prepared.
- QL-001 — repository assessment and governance bootstrap.
- QL-002 — full-stack health vertical slice (Web → API → AI Service), with tests passing and Docker Compose orchestration verified end to end, including the degraded/recovery lifecycle (AI service stopped → API reports DEGRADED and stays available → AI service restarted → full system returns to UP).

## In progress

- None. Awaiting architecture reviewer approval of the next task.

## Blocked

- None. Port 8080 was already bound by an unrelated local project on this machine, so the default API port was changed to **8086** (`apps/api/src/main/resources/application.yml`, `apps/web/src/api/systemHealth.ts`, `docker-compose.yml`, `.env.example`, `README.md` all updated to match). Port 8000 may still be bound by another unrelated local project; override `AI_SERVICE_PORT` in `.env` if needed.

## Next milestone

Architecture reviewer to approve the next task (candidates: PostgreSQL/Flyway baseline, Spring Boot API health slice hardening, or FastAPI provider groundwork — see `TASKS.md`).

## Approved baseline versions

Exact versions selected and verified in this environment:

- Java: 25.0.3 (Eclipse Temurin)
- Maven: 3.9.9 (via Maven Wrapper)
- Spring Boot: 3.5.16
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
- PostgreSQL: not yet introduced
- Docker: 29.6.2 / Docker Compose: v5.3.1

## Known product defaults

- Prediction horizon: 5 trading sessions.
- Recommendation confidence threshold: 60%, configurable.
- Virtual capital: 100,000,000 VND.
- Baseline model: Logistic Regression.
- First challenger: XGBoost.
