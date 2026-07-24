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
- None.

### Known limitations
- No PostgreSQL, Redis, MinIO, or authentication integration yet (out of scope for QL-002 by design).
- `apps/web` has no charting, state-management (Zustand), form, or validation libraries yet — none were needed for this slice.
- Local Docker verification on the development machine required overriding `API_PORT`/`AI_SERVICE_PORT` because unrelated local projects already occupied the default host ports 8080/8000; the committed `.env.example` defaults are unaffected.
