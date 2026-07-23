# QL-002 — Full-Stack Foundation Vertical Slice

## Objective

Create the first runnable end-to-end QuantLens vertical slice covering:

```text
React Web
    ↓ REST
Spring Boot API
    ↓ REST
FastAPI AI Service
```

The result must run locally and visibly prove communication across all three layers.

This task is foundation only. Do not implement market-data ingestion, authentication, model training, predictions, portfolio logic, Redis integration, MinIO integration, or PostgreSQL business schemas yet.

## Required Reading

Before starting:

1. Read `/CLAUDE.md`.
2. Read `/docs/ai/RULES.md`.
3. Read every file under `/docs/ai/`.
4. Inspect the repository and Git status.
5. Confirm that QL-002 is the current approved task.

## Target Repository Structure

Create or align the repository to this structure:

```text
Quantlens/
├── apps/
│   ├── api/
│   ├── web/
│   └── ai-service/
├── infrastructure/
│   └── docker/
├── packages/
│   ├── contracts/
│   └── shared/
├── scripts/
├── docs/
│   └── ai/
├── .editorconfig
├── .gitattributes
├── .gitignore
├── .env.example
├── docker-compose.yml
└── README.md
```

Do not create unnecessary placeholder files or speculative modules.

## Scope

### 1. Spring Boot API

Initialize `apps/api` using:

- Java 25
- Spring Boot 3.5+
- Maven Wrapper
- Spring Web
- Spring Validation
- Spring Boot Actuator
- OpenAPI/Swagger only if it can be added cleanly without delaying the task

Use package-by-feature.

Provide:

```http
GET /api/v1/system/health
```

The endpoint must:

- report the API service status;
- call the AI service health endpoint through REST;
- return a stable JSON contract;
- return a degraded response when the AI service is unavailable;
- use a configurable AI service base URL;
- use explicit timeouts;
- not expose framework internals.

Suggested response shape:

```json
{
  "service": "quantlens-api",
  "status": "UP",
  "aiService": {
    "status": "UP",
    "service": "quantlens-ai-service",
    "version": "0.1.0"
  },
  "version": "0.1.0",
  "timestamp": "2026-01-01T00:00:00Z"
}
```

When AI is unavailable, the API itself may remain available and report:

```json
{
  "service": "quantlens-api",
  "status": "DEGRADED",
  "aiService": {
    "status": "DOWN"
  }
}
```

Implement unit or integration tests for:

- AI service available;
- AI service unavailable;
- response contract.

Do not connect PostgreSQL, Redis, MinIO, Keycloak, JPA, or Flyway in this task.

### 2. FastAPI AI Service

Initialize `apps/ai-service` using:

- Python 3.13
- FastAPI
- Pydantic
- pytest
- a clean dependency declaration and lock strategy appropriate for the repository

Provide:

```http
GET /health
```

Suggested response:

```json
{
  "service": "quantlens-ai-service",
  "status": "UP",
  "version": "0.1.0"
}
```

Requirements:

- typed response model;
- deterministic response;
- test coverage for the endpoint;
- no ML model, provider SDK, database, MLflow, or scheduler implementation yet.

### 3. React Web

Initialize `apps/web` using:

- React 19
- TypeScript strict mode
- Vite
- Ant Design 5
- TanStack Query
- React Router

Create a minimal application shell with a page such as:

```text
System Status
```

The page must call:

```http
GET /api/v1/system/health
```

through the Spring Boot API only.

It must display:

- Web status;
- API status;
- AI service status;
- version information;
- loading state;
- error state;
- degraded state.

Do not call the AI service directly from the browser.

Add at least one meaningful frontend test for the status rendering or API state handling.

Do not add Zustand, React Hook Form, Zod, or charting libraries unless they are actually needed for this task.

### 4. Local Development

Provide a reproducible local development path.

Create either:

- one root `docker-compose.yml` that runs the API, web, and AI service; or
- a clearly documented hybrid workflow where infrastructure/services are started consistently.

Preferred result:

```bash
docker compose up --build
```

The expected local URLs should be documented, for example:

- Web: `http://localhost:5173`
- API: `http://localhost:8080`
- AI: `http://localhost:8000`
- Swagger: `http://localhost:8080/swagger-ui.html` when enabled

Add health checks where practical.

Use environment variables and add all required values to `.env.example`.

Do not add PostgreSQL, Redis, or MinIO containers in this task unless they are required by generated code. They should not be required for this vertical slice.

### 5. Repository Foundation

Create or update:

- `.gitignore`
- `.editorconfig`
- `.gitattributes`
- `.env.example`
- root `README.md`

The root README must include:

- product summary;
- current architecture;
- prerequisites;
- exact local run commands;
- exact test/build commands;
- service URLs;
- current limitations;
- repository structure.

Avoid duplicating large sections already maintained in `docs/ai`.

## API Contract

Keep the backend health response stable and simple.

If a shared contract artifact is introduced, it must be genuinely used. Do not create an unused contracts package merely to satisfy the target folder layout.

## Error Handling

- The backend must use a short timeout when checking the AI service.
- AI unavailability must not crash the backend.
- The frontend must distinguish:
  - complete success;
  - degraded API response;
  - backend unreachable.
- Do not log secrets or dump full stack traces to frontend responses.

## Out of Scope

Do not implement:

- authentication or Keycloak;
- database entities or migrations;
- Redis;
- MinIO;
- market-data providers;
- Vnstock;
- feature engineering;
- Logistic Regression;
- XGBoost;
- MLflow;
- portfolio;
- recommendation logic;
- prediction persistence;
- CI/CD beyond minimal validation if already easy to include;
- production deployment.

Do not create fake implementations for these areas.

## Verification Requirements

Claude must run all applicable commands and report the actual results.

Expected verification includes:

### Backend

```bash
cd apps/api
./mvnw test
./mvnw package
```

Use the Windows equivalent when necessary.

### AI service

Use the selected dependency tool, then run tests, for example:

```bash
cd apps/ai-service
pytest
```

Also run configured lint or type checks when present.

### Frontend

```bash
cd apps/web
npm install
npm run lint
npm run test -- --run
npm run build
```

Adapt only to the scripts actually configured.

### Docker

```bash
docker compose config
docker compose up --build
```

After startup, verify:

- AI `/health`;
- API `/api/v1/system/health`;
- Web loads;
- API correctly reports AI status.

If Docker cannot run in the environment, report that honestly and still validate configuration where possible.

## Acceptance Criteria

- [ ] Repository contains runnable `apps/api`, `apps/web`, and `apps/ai-service`.
- [ ] AI service exposes a tested `/health` endpoint.
- [ ] Backend exposes a tested `/api/v1/system/health` endpoint.
- [ ] Backend health calls the AI service using configurable REST communication.
- [ ] Backend reports `DEGRADED` rather than crashing when AI is unavailable.
- [ ] Frontend displays Web, API, and AI statuses.
- [ ] Frontend calls only the Spring Boot API.
- [ ] All three services can run locally using documented commands.
- [ ] `.env.example` contains all required environment variables without secrets.
- [ ] Relevant tests and builds pass.
- [ ] No unrelated technology or business feature is introduced.
- [ ] `PROJECT_STATE.md`, `TASKS.md`, `CODE_MAP.md`, and `CHANGELOG.md` are updated.
- [ ] Claude returns the required handoff report.
- [ ] Claude stops after QL-002.

## Required Handoff

Follow the exact handoff format in `docs/ai/WORKFLOW.md`.

Additionally include:

- exact Java, Spring Boot, Node.js, npm, Python, and dependency versions selected;
- exact service URLs;
- a compact end-to-end verification table;
- any difference between the requested architecture and the implementation;
- `git diff --stat`;
- suggested commit message:

```text
feat(platform): bootstrap full-stack health vertical slice
```

Do not commit or push unless the user explicitly asks.
