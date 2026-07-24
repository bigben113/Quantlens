# QuantLens Project State

Last updated: 2026-07-23 (QL-002 — Full-Stack Foundation Vertical Slice).

## Current phase

QL-002 complete: a runnable, tested, end-to-end health/status vertical slice across Web → API → AI Service.

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
- QL-002 — full-stack health vertical slice (Web → API → AI Service), with tests passing and Docker Compose orchestration verified end to end.

## In progress

- None. Awaiting architecture reviewer approval of the next task.

## Blocked

- None. Note for future Docker verification on this machine: ports 8000 and 8080 may already be bound by unrelated local projects; `.env` port variables (`API_PORT`, `AI_SERVICE_PORT`, `WEB_PORT`) can be overridden to avoid conflicts without changing the repository defaults.

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
