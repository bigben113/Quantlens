# Engineering Task Specification

# QL-003 --- Database Foundation

## Prerequisites (Mandatory)

Before starting this task, read the following documents in order:

1.  `CLAUDE.md`
2.  `docs/ai/RULES.md`
3.  `docs/ai/WORKFLOW.md`
4.  `docs/ai/PROJECT_STATE.md`
5.  `docs/ai/CODE_MAP.md`
6.  `docs/ai/TASKS.md`

After reading, summarize your understanding in 5--10 bullet points
before writing any code.

------------------------------------------------------------------------

## Context

QL-002 has been completed and verified.

The objective of QL-003 is to establish a production-ready database
foundation. Do not implement any business logic or stock-market
features.

Architecture must remain:

React ↓ Spring Boot ↓ PostgreSQL

Python AI Service is unchanged.

------------------------------------------------------------------------

## Scope

### 1. PostgreSQL

-   Configure PostgreSQL as the primary database.
-   Environment-variable driven configuration.
-   HikariCP connection pool.
-   No hardcoded credentials.

### 2. Flyway

Integrate Flyway.

Requirements:

-   Automatic migration on startup.
-   Baseline migration.
-   Migration folder structure.
-   Document migration naming convention.

Create the initial migration.

### 3. Initial Schema

Create only infrastructure tables.

Required:

#### app_settings

General application configuration.

#### job_execution

Track background jobs.

Suggested fields:

-   id
-   job_name
-   started_at
-   finished_at
-   status
-   message

#### model_registry

Track AI models.

Suggested fields:

-   id
-   model_name
-   version
-   type
-   status
-   created_at

No prediction logic.

### 4. JPA

Configure:

-   Spring Data JPA
-   auditing
-   UTC timestamps
-   UUID primary keys

### 5. Base Entity

Reusable BaseEntity:

-   id
-   createdAt
-   updatedAt

UUID based.

### 6. Repository Layer

Create repositories only.

No services.

No business logic.

### 7. Health Endpoint

Extend:

`/api/v1/system/health`

Example:

``` json
{
  "application": "UP",
  "database": "UP",
  "aiService": "UP"
}
```

Database DOWN must not crash the application.

### 8. Docker

Update docker-compose:

-   postgres
-   persistent volume
-   healthcheck
-   startup ordering

### 9. Documentation

Update:

-   docs/ai/PROJECT_STATE.md
-   docs/ai/CODE_MAP.md
-   docs/ai/CHANGELOG.md
-   docs/ai/TASKS.md

Move:

-   QL-002 → Completed
-   QL-003 → Current

### 10. Validation

Verify:

-   Flyway executed
-   Tables created
-   Spring Boot started
-   Docker Compose healthy
-   Health endpoint reports DB status

------------------------------------------------------------------------

## Constraints

Do NOT:

-   implement market data
-   create stock tables
-   create prediction tables
-   add business services
-   change architecture
-   add unnecessary libraries

Stay within infrastructure scope only.

------------------------------------------------------------------------

## Deliverables

Return:

1.  Architecture summary
2.  Files changed
3.  Validation results
4.  Flyway execution result
5.  Docker Compose status
6.  git diff --stat
7.  Suggested commit message

Do NOT commit.

Stop after QL-003 is complete.
