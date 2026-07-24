# QuantLens Code Map

Status: reflects the repository after QL-003 (database foundation).

## Actual structure

```text
quantlens/
├── apps/
│   ├── api/                          # Spring Boot modular monolith (Java 25, Spring Boot 3.5)
│   │   ├── src/main/java/com/quantlens/api/
│   │   │   ├── QuantlensApiApplication.java
│   │   │   ├── common/
│   │   │   │   ├── BaseEntity.java              # UUID id, createdAt/updatedAt (JPA auditing)
│   │   │   │   └── JpaAuditingConfig.java        # @EnableJpaAuditing
│   │   │   ├── settings/
│   │   │   │   ├── AppSetting.java               # entity -> app_settings
│   │   │   │   └── AppSettingRepository.java
│   │   │   ├── jobs/
│   │   │   │   ├── JobExecution.java             # entity -> job_execution
│   │   │   │   └── JobExecutionRepository.java
│   │   │   ├── modelregistry/
│   │   │   │   ├── ModelRegistryEntry.java       # entity -> model_registry
│   │   │   │   └── ModelRegistryEntryRepository.java
│   │   │   └── system/
│   │   │       ├── SystemHealthController.java      # GET /api/v1/system/health
│   │   │       ├── SystemHealthResponse.java         # stable API response contract (+ database field)
│   │   │       ├── AiServiceClient.java               # REST client -> AI service /health
│   │   │       ├── AiServiceHealthResponse.java
│   │   │       ├── AiServiceProperties.java           # quantlens.ai-service.* config
│   │   │       ├── DatabaseHealthChecker.java         # DataSource connection validity check
│   │   │       └── WebCorsConfig.java                 # CORS allow-list for the web origin
│   │   ├── src/main/resources/application.yml         # datasource/JPA/Flyway/Hikari config
│   │   ├── src/main/resources/db/migration/
│   │   │   ├── README.md                              # Flyway naming convention
│   │   │   └── V1__baseline_schema.sql                # app_settings, job_execution, model_registry
│   │   ├── src/test/java/com/quantlens/api/system/    # controller + client + db health checker tests
│   │   ├── pom.xml
│   │   ├── Dockerfile
│   │   └── mvnw / mvnw.cmd
│   ├── ai-service/                    # FastAPI AI service (Python 3.13) — unchanged in QL-003
│   │   ├── app/
│   │   │   ├── main.py                # GET /health
│   │   │   ├── schemas.py              # HealthResponse (Pydantic)
│   │   │   └── config.py               # Settings (AI_SERVICE_* env prefix)
│   │   ├── tests/test_health.py
│   │   ├── pyproject.toml
│   │   ├── requirements.txt / requirements-dev.txt
│   │   └── Dockerfile
│   └── web/                           # React 19 + TypeScript + Vite — unchanged in QL-003
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
├── docker-compose.yml                  # orchestrates postgres, ai-service, api, web
├── .env.example
├── CLAUDE.md
└── README.md
```

## Module ownership

- **apps/api** owns the public REST contract (`/api/v1/...`), calls the AI service, owns the database connection/migrations/JPA repositories, and will own future business workflows, virtual portfolio, and prediction persistence.
  - `common` — shared JPA infrastructure (`BaseEntity`, auditing config). No business logic.
  - `settings`, `jobs`, `modelregistry` — infrastructure entities/repositories only (`app_settings`, `job_execution`, `model_registry`). No services, no business logic — repository layer only, by design (QL-003 scope).
  - `system` — system health endpoint, AI service client, and now `DatabaseHealthChecker`.
- **apps/ai-service** owns AI/ML concerns: currently only a health endpoint; will later own provider adapters, feature engineering, training, inference, and evaluation.
- **apps/web** owns the user-facing UI; calls only the Spring Boot API, never the AI service or database directly.
- **PostgreSQL** (via `apps/api`) is now the system of record for infrastructure metadata. No Redis, MinIO, or authentication integration exists yet.

## Important entry points

- API: `apps/api/src/main/java/com/quantlens/api/QuantlensApiApplication.java`, health endpoint `GET /api/v1/system/health` (now includes `database` status).
- Database: `apps/api/src/main/resources/db/migration/V1__baseline_schema.sql` (Flyway, runs automatically on startup).
- AI service: `apps/ai-service/app/main.py`, health endpoint `GET /health`.
- Web: `apps/web/src/main.tsx` → `App.tsx` → `pages/SystemStatusPage.tsx`.

## Build and run commands

See the root `README.md` for the full list. Summary:

```bash
docker compose up --build          # postgres, ai-service, api, web together

cd apps/api && ./mvnw test && ./mvnw package
cd apps/ai-service && pytest
cd apps/web && npm run lint && npm run test -- --run && npm run build
```
