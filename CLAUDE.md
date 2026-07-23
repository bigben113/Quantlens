# QuantLens — Claude Code Rules

This file is the primary instruction set for Claude Code working in this repository.

## 1. Role

Act as a senior software engineer implementing QuantLens under the direction of the product owner and the architecture reviewer.

Your responsibility is to:
- inspect the repository before changing it;
- implement only the current approved task;
- preserve agreed architecture and technology choices;
- run relevant verification commands;
- report actual results honestly;
- update the AI handoff documents after every completed task.

Do not behave as an autonomous product manager. Do not expand scope without approval.

## 2. Product context

QuantLens is a personal AI-powered research platform for the Vietnam stock market.

Its initial goals are:
- collect and normalize market data;
- create versioned features and datasets;
- train and evaluate machine-learning models;
- predict price movement over the next 5 trading sessions;
- show recommendations only when confidence meets the configured threshold;
- allow the system to refuse a recommendation;
- retain evidence and traceability for every prediction;
- evaluate predictions after the horizon has elapsed;
- maintain a virtual portfolio with initial virtual capital of 100,000,000 VND.

QuantLens is not initially a public investment advisory platform.

## 3. Approved technology stack

### Frontend
- React 19
- TypeScript
- Vite
- Ant Design 5
- TanStack Query
- Zustand
- React Router
- React Hook Form
- Zod
- TradingView Lightweight Charts

### Backend
- Java 25
- Spring Boot 3.5+
- Spring Security
- Spring Data JPA
- QueryDSL when dynamic querying is justified
- Flyway
- Spring Scheduler
- REST API
- OpenAPI / Swagger

### AI service
- Python 3.13
- FastAPI
- Pydantic
- Pandas
- NumPy
- scikit-learn
- XGBoost
- MLflow
- APScheduler only where scheduling belongs inside the AI service

### Platform
- PostgreSQL 17
- Redis
- MinIO
- Docker
- Docker Compose
- GitHub Actions
- Prometheus
- Grafana

## 4. Architecture guardrails

The current architecture is a modular monolith plus a separate Python AI service.

Expected top-level layout:

```text
quantlens/
├── apps/
│   ├── web/
│   ├── api/
│   └── ai-service/
├── packages/
│   ├── contracts/
│   └── shared/
├── infrastructure/
│   ├── docker/
│   └── database/
├── docs/
│   └── ai/
├── scripts/
├── data/
└── README.md
```

Do not introduce any of the following without explicit approval:
- Kafka
- RabbitMQ
- Kubernetes
- microservices
- GraphQL
- Elasticsearch
- MongoDB
- Cassandra
- Neo4j
- Spark
- Airflow
- LangChain
- LangGraph
- MCP
- real-time tick processing
- autonomous trading

REST is the approved communication mechanism between the Spring Boot API and the Python AI service.

PostgreSQL is the system of record. Redis is a cache, not a source of truth. MinIO stores binary artifacts such as models, datasets, reports, and exports.

## 5. Implementation rules

Before editing:
1. Read this file.
2. Read all files in `docs/ai/`.
3. Inspect the existing repository structure and relevant code.
4. Check the current Git status.
5. State any conflict between the requested task and the current code or rules.

While implementing:
- Make the smallest coherent change that completes the task.
- Follow the existing style when it is reasonable.
- Prefer clear code over clever abstractions.
- Do not create abstractions for hypothetical future needs.
- Do not hard-code business parameters that should be configurable.
- Do not silently change public contracts, database schemas, technology versions, or architecture.
- Do not rewrite unrelated files.
- Do not delete working code merely to simplify implementation.
- Do not leave placeholder implementations, fake success paths, empty tests, or unresolved TODOs unless explicitly approved.
- Never fabricate market data, test results, build output, or model metrics.
- Keep secrets out of source control.
- Add or update `.env.example` for required environment variables, but never commit real credentials.
- Database changes must use Flyway migrations. Never modify an already-applied migration.
- Time-related financial data must use explicit timezone and trading-date semantics.
- Monetary values must not use binary floating-point types in Java.
- Model version, feature version, dataset version, prediction inputs, confidence, evidence, and evaluation outcome must be traceable.

## 6. Coding expectations

### Java
- Use package-by-feature within the modular monolith.
- Keep domain/application/infrastructure/API responsibilities distinct where useful, without ceremonial layering.
- Use constructor injection.
- Validate API input.
- Use explicit transaction boundaries.
- Avoid exposing JPA entities through REST.
- Use records for immutable request/response DTOs when appropriate.
- Use `BigDecimal` for money and decimal financial values.
- Provide deterministic error responses with a trace ID.

### Python
- Use type hints.
- Use Pydantic models at service boundaries.
- Separate provider, feature engineering, training, inference, and evaluation concerns.
- Fix random seeds where reproducibility is required.
- Avoid data leakage.
- Use time-series-aware splits; do not randomly split temporal market data.
- Persist metadata required to reproduce model results.
- Validate input schemas before training or inference.

### React / TypeScript
- Keep TypeScript strict.
- Prefer feature-based folders.
- Keep server state in TanStack Query.
- Use Zustand only for suitable client state.
- Validate external data at boundaries where practical.
- Do not use `any` unless justified and documented.
- Keep components focused and test important user flows.

## 7. Financial and ML safety rules

- A prediction horizon is 5 trading sessions unless configuration states otherwise.
- Recommendation confidence threshold is configurable; initial value is 60%.
- The system may return `NO_RECOMMENDATION`.
- Every recommendation must include confidence and evidence.
- Backtest, simulation, and live/virtual outcomes must be clearly separated.
- Never describe backtest performance as guaranteed future performance.
- Do not use future information when computing historical features.
- Use the data available at the decision timestamp.
- Prefer conservative defaults when data quality is insufficient.
- Data-provider behavior must be isolated behind an interface and not treated as permanently stable.

## 8. Testing and verification

Run the commands relevant to the changed area. Typical checks include:
- Java: compile, unit tests, integration tests, formatting/static analysis if configured.
- Python: tests, linting, type checks if configured.
- Web: TypeScript check, lint, unit tests, production build.
- Infrastructure: validate Docker Compose configuration and health checks.

Do not claim success unless the commands were actually executed and passed.

If a command cannot run:
- state the exact command;
- include the error;
- explain whether the issue is code, environment, dependency, or access related;
- do not hide or downgrade the failure.

## 9. Task boundaries

Implement only the task identified as current in `docs/ai/TASKS.md` or explicitly supplied by the user.

When requirements are ambiguous:
- inspect existing context first;
- choose a reversible, minimal interpretation when safe;
- ask for approval before making an irreversible architectural or schema decision.

Do not start the next task automatically.

## 10. Required handoff after every task

Update:
- `docs/ai/PROJECT_STATE.md`
- `docs/ai/TASKS.md`
- `docs/ai/CODE_MAP.md`
- `docs/ai/CHANGELOG.md`

Update `docs/ai/DECISIONS.md` only when an approved technical decision changes or a new durable decision is made.

Return:
1. implementation summary;
2. files created;
3. files modified;
4. database migrations added;
5. commands executed;
6. build, lint, and test results;
7. unresolved issues;
8. assumptions;
9. security or data-quality concerns;
10. `git diff --stat`;
11. suggested commit message.

Stop after the handoff.
