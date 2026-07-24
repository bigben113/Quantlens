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

### Changed
- Default API port changed from 8080 to **8086** because port 8080 is already bound by an unrelated local project on this development machine. Updated `apps/api/src/main/resources/application.yml`, `apps/api/Dockerfile` (`EXPOSE`), `apps/web/src/api/systemHealth.ts`, `docker-compose.yml`, `.env.example`, and `README.md` to keep the new default consistent everywhere.

### Fixed
- After the port change, `docker compose up -d` (without `--build`) reused a cached `quantlens-api` image built with the old 8080 default, so Tomcat inside the container listened on 8080 while the Compose port mapping and healthcheck expected 8086 — the container never became reachable and the frontend reported "check system status fail". Rebuilt with `docker compose up -d --build` to resolve; the API is now healthy and reachable on 8086.

### Verified
- QL-002 full end-to-end lifecycle verified live via `docker compose`: `GET /health` (AI service) and `GET /api/v1/system/health` (API) both confirmed against the running containers; the web app confirmed serving its shell and, via the existing component test suite, correctly rendering loading/UP/DEGRADED/error states.
- Degraded flow: stopping `ai-service` (`docker compose stop ai-service`) leaves the API container up and healthy, with `GET /api/v1/system/health` returning `status: DEGRADED`, `aiService.status: DOWN`.
- Recovery flow: restarting `ai-service` (`docker compose start ai-service`) returns the full system to `status: UP`, `aiService.status: UP`.
- Full backend/AI/frontend/Docker validation suite re-run and passing: `./mvnw test` (4/4), `./mvnw package` (BUILD SUCCESS), `pytest` (2/2), `npm run lint`, `npm run test -- --run` (4/4), `npm run build`, `docker compose config`.

### Known limitations
- No PostgreSQL, Redis, MinIO, or authentication integration yet (out of scope for QL-002 by design).
- `apps/web` has no charting, state-management (Zustand), form, or validation libraries yet — none were needed for this slice.
- Port 8000 (AI service) may still be occupied by another unrelated local project on this machine; override `AI_SERVICE_PORT` in `.env` if needed. The committed `.env.example` defaults are otherwise correct for a clean machine.
- No interactive browser/display is available in this environment; the React status page was verified via HTTP-level checks (correct HTML shell served) plus the existing automated component test suite (loading/UP/DEGRADED/error rendering), not a live browser session.
