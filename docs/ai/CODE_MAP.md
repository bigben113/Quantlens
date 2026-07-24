# QuantLens Code Map

Status: reflects the repository after QL-002 (full-stack foundation vertical slice).

## Actual structure

```text
quantlens/
├── apps/
│   ├── api/                          # Spring Boot modular monolith (Java 25, Spring Boot 3.5)
│   │   ├── src/main/java/com/quantlens/api/
│   │   │   ├── QuantlensApiApplication.java
│   │   │   └── system/
│   │   │       ├── SystemHealthController.java      # GET /api/v1/system/health
│   │   │       ├── SystemHealthResponse.java         # stable API response contract
│   │   │       ├── AiServiceClient.java               # REST client -> AI service /health
│   │   │       ├── AiServiceHealthResponse.java
│   │   │       ├── AiServiceProperties.java           # quantlens.ai-service.* config
│   │   │       └── WebCorsConfig.java                 # CORS allow-list for the web origin
│   │   ├── src/main/resources/application.yml
│   │   ├── src/test/java/com/quantlens/api/system/    # controller + client tests
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   └── mvnw / mvnw.cmd
│   ├── ai-service/                    # FastAPI AI service (Python 3.13)
│   │   ├── app/
│   │   │   ├── main.py                # GET /health
│   │   │   ├── schemas.py              # HealthResponse (Pydantic)
│   │   │   └── config.py               # Settings (AI_SERVICE_* env prefix)
│   │   ├── tests/test_health.py
│   │   ├── pyproject.toml
│   │   ├── requirements.txt / requirements-dev.txt
│   │   └── Dockerfile
│   └── web/                           # React 19 + TypeScript + Vite
│       ├── src/
│       │   ├── api/systemHealth.ts     # typed fetch client -> Spring Boot API only
│       │   ├── pages/SystemStatusPage.tsx (+ .test.tsx)
│       │   ├── App.tsx                 # React Router routes
│       │   ├── main.tsx                # QueryClient + Router + AntD providers
│       │   └── test/setup.ts           # vitest + jsdom + matchMedia polyfill
│       ├── package.json
│       ├── vite.config.ts
│       └── Dockerfile
├── docs/
│   └── ai/                            # AI governance and handoff state
├── infrastructure/
│   └── docker/                        # reserved; empty until a task needs it
├── packages/                          # reserved; empty until a shared contract is genuinely needed
├── scripts/                           # reserved; empty until repository automation is needed
├── docker-compose.yml                  # orchestrates api, ai-service, web
├── .env.example
├── CLAUDE.md
└── README.md
```

## Module ownership

- **apps/api** owns the public REST contract (`/api/v1/...`), calls the AI service, and will own future business workflows, persistence coordination, and virtual portfolio logic.
- **apps/ai-service** owns AI/ML concerns: currently only a health endpoint; will later own provider adapters, feature engineering, training, inference, and evaluation.
- **apps/web** owns the user-facing UI; calls only the Spring Boot API, never the AI service directly.

No PostgreSQL, Redis, MinIO, or authentication integration exists yet.

## Important entry points

- API: `apps/api/src/main/java/com/quantlens/api/QuantlensApiApplication.java`, health endpoint `GET /api/v1/system/health`.
- AI service: `apps/ai-service/app/main.py`, health endpoint `GET /health`.
- Web: `apps/web/src/main.tsx` → `App.tsx` → `pages/SystemStatusPage.tsx`.

## Build and run commands

See the root `README.md` for the full list. Summary:

```bash
docker compose up --build          # all three services together

cd apps/api && ./mvnw test && ./mvnw package
cd apps/ai-service && pytest
cd apps/web && npm run lint && npm run test -- --run && npm run build
```
