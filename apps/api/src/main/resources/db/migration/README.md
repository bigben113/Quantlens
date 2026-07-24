# Flyway Migration Naming Convention

Format: `V<version>__<description>.sql`

- `<version>` — a monotonically increasing integer (`1`, `2`, `3`, ...). Never reuse or renumber a version that has already been applied to any environment.
- Two underscores (`__`) separate the version from the description (required by Flyway).
- `<description>` — lowercase snake_case, short and imperative (e.g. `baseline_schema`, `add_job_execution_index`).
- Never edit a migration once it has been applied anywhere — add a new migration instead.

## Applied migrations

- `V1__baseline_schema.sql` — infrastructure tables: `app_settings`, `job_execution`, `model_registry`.
