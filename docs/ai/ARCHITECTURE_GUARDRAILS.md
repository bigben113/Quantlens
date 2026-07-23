# Architecture Guardrails

## Current system shape

```text
React Web
    ↓ REST
Spring Boot API
    ├── PostgreSQL
    ├── Redis
    ├── MinIO
    └── REST → Python AI Service
                      ├── Data Provider
                      ├── Feature Engineering
                      ├── Training
                      ├── Inference
                      └── Evaluation
```

## Boundary rules

### Spring Boot API owns
- user-facing REST APIs;
- authentication and authorization integration;
- business workflows;
- persistence coordination;
- virtual portfolio;
- prediction records and lifecycle;
- calls to the AI service;
- exposure of stable contracts to the web application.

### Python AI service owns
- provider adapters used by AI/data pipelines where approved;
- feature generation algorithms;
- training;
- inference;
- model evaluation;
- model artifact production;
- experiment metadata integration.

### PostgreSQL owns
- durable structured records;
- business state;
- normalized market data;
- model and prediction metadata;
- evaluation results;
- portfolio state.

### MinIO owns
- model binaries;
- generated datasets where file storage is appropriate;
- reports and exports;
- large immutable artifacts.

### Redis owns
- disposable cache data;
- short-lived coordination only when justified.

Redis must never be the only location of important business or model state.

## Module dependency guidance

Modules should depend inward on contracts rather than concrete provider or storage implementations.

Approved abstractions:
- `DataProvider`
- `PredictionModel`
- `Agent`
- repository interfaces at meaningful domain boundaries

Do not create interfaces for every class.

## API rules

- Use `/api/v1/...` for public backend endpoints.
- Prefer resources and actions with explicit semantics.
- Do not expose database entities.
- Use consistent pagination and errors.
- Include a trace ID in error responses.
- Breaking contract changes require explicit approval.

## Database rules

- Flyway migrations only.
- Use snake_case database naming.
- Use UTC instants for technical timestamps.
- Preserve local trading date separately where required.
- Use numeric/decimal types for prices, ratios, money, and confidence.
- Add indexes based on actual query paths.
- Avoid premature partitioning unless current volume or task requires it.
- Store flexible evidence metadata in JSONB only when relational columns are not appropriate.
- Do not turn JSONB into a substitute for schema design.

## Security rules

- No committed passwords, tokens, private keys, or provider credentials.
- All secrets come from environment variables or secret management.
- Authentication is planned through Spring Security and Keycloak.
- Minimize sensitive logging.
- Validate uploaded or externally sourced data.
- Dependency changes must be explicit and justified.

## Change control

Claude must request approval before:
- changing the top-level architecture;
- introducing a new database or infrastructure product;
- changing Java, Node, Python, Spring Boot, React, or PostgreSQL major versions;
- changing the prediction horizon or confidence policy;
- changing ownership between Spring Boot and Python;
- creating a breaking API or database change;
- adding automatic model promotion;
- adding automatic live trading.
