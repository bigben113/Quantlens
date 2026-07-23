# QuantLens Project Rules

These are durable project constraints. They define what QuantLens is allowed to become and how important technical decisions must be implemented.

Claude must read this file before every task.

## 1. Product Boundaries

QuantLens is initially:

- a personal AI investment research platform;
- focused on the Vietnam stock market;
- designed for research, backtesting, simulation, and virtual portfolio evaluation;
- not a public investment advisory platform;
- not an automatic live-trading system.

The system must be conservative and may return `NO_RECOMMENDATION`.

Every recommendation must include prediction, confidence, evidence, model version, and feature or dataset traceability.

## 2. Prediction Rules

Default values:

- prediction horizon: 5 trading sessions;
- confidence threshold: 60%;
- virtual portfolio capital: 100,000,000 VND.

These values must be configurable and must not be hard-coded inside domain logic.

Historical calculations must use only information available at the decision timestamp.

Future data must never leak into feature generation, training, backtesting, inference, or evaluation.

## 3. Model Lifecycle

```text
Champion
    +
Challengers
    ↓
Backtest
    ↓
Simulation
    ↓
Human approval
    ↓
Promotion
```

During MVP:

- Logistic Regression is the baseline model;
- XGBoost is the first challenger;
- model promotion requires human approval;
- automatic champion replacement is prohibited.

Backtest results must never be presented as guaranteed future performance.

## 4. Architecture

```text
React Web
    ↓ REST
Spring Boot Modular Monolith
    ├── PostgreSQL
    ├── Redis
    ├── MinIO
    └── REST → Python FastAPI AI Service
```

PostgreSQL is the system of record. Redis is disposable cache only. MinIO stores model files, datasets, reports, exports, and large immutable artifacts.

## 5. Approved Technology Stack

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
- QueryDSL when justified
- Flyway
- Spring Scheduler
- REST
- OpenAPI / Swagger

### AI Service

- Python 3.13
- FastAPI
- Pydantic
- Pandas
- NumPy
- scikit-learn
- XGBoost
- MLflow
- APScheduler only when justified

### Platform

- PostgreSQL 17
- Redis
- MinIO
- Docker
- Docker Compose
- GitHub Actions
- Prometheus
- Grafana

## 6. Prohibited Without Explicit Approval

Do not introduce:

- Kafka;
- RabbitMQ;
- Kubernetes;
- microservices;
- GraphQL;
- Elasticsearch;
- MongoDB;
- Cassandra;
- Neo4j;
- Spark;
- Airflow;
- LangChain;
- LangGraph;
- MCP;
- real-time tick processing;
- automatic live trading;
- automatic model promotion.

Do not change major platform versions without approval.

## 7. Data Provider Rules

All market-data access must be isolated behind a provider abstraction.

Business logic must not depend directly on a provider-specific SDK or response format.

Provider adapters must normalize external data, validate schemas, preserve source metadata, handle transient failures, report suspicious data, and never fabricate missing values silently.

## 8. Database Rules

- Use Flyway migrations only.
- Never edit an already-applied migration.
- Use snake_case naming in PostgreSQL.
- Use UTC instants for technical timestamps.
- Preserve local trading dates separately where needed.
- Use numeric or decimal types for prices, money, ratios, and confidence.
- Add indexes based on real query paths.
- Avoid premature partitioning.
- Use JSONB only where flexible metadata is appropriate.
- Do not use JSONB as a substitute for relational design.
- Breaking schema changes require explicit approval.

## 9. Java Rules

- Use package-by-feature.
- Use constructor injection.
- Validate API inputs.
- Use explicit transaction boundaries.
- Do not expose JPA entities through REST.
- Prefer records for immutable DTOs when appropriate.
- Use `BigDecimal` for money and decimal financial values.
- Never use `double` or `float` for monetary values.
- Return consistent API errors with a trace ID.

## 10. Python and ML Rules

- Use type hints.
- Use Pydantic at service boundaries.
- Separate provider, feature, training, inference, and evaluation concerns.
- Fix random seeds where reproducibility is required.
- Use time-series-aware data splitting.
- Prevent target leakage and look-ahead bias.
- Persist metadata needed to reproduce model results.
- Validate input schemas before training and inference.

## 11. React and TypeScript Rules

- Keep TypeScript strict.
- Prefer feature-based folders.
- Use TanStack Query for server state.
- Use Zustand only for appropriate client state.
- Avoid `any`.
- Validate external data at boundaries where practical.
- Keep components focused.
- Do not move business rules into UI components.

## 12. API Rules

- Use REST.
- Use `/api/v1/...` for public backend endpoints.
- Do not expose database entities.
- Use consistent pagination and error contracts.
- Include trace IDs in errors.
- Breaking API changes require approval.
- The frontend communicates with the Spring Boot API, not directly with the database.

## 13. Security Rules

- Never commit secrets.
- Use environment variables or secret management.
- Keep `.env.example` current without real values.
- Minimize sensitive logging.
- Validate uploaded and external data.
- Treat provider payloads as untrusted.
- Dependency additions must be explicit and justified.
- Authentication is planned through Spring Security and Keycloak.

## 14. Explicit Approval Required

Claude must request approval before:

- changing the top-level architecture;
- adding infrastructure products;
- changing ownership between Java and Python;
- changing the prediction horizon or recommendation policy;
- adding automatic trading;
- adding automatic model promotion;
- making a breaking API or database change;
- changing major technology versions;
- weakening traceability, testing, security, or data-quality safeguards.
