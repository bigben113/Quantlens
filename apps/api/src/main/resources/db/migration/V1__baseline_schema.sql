-- Baseline schema: infrastructure tables only.
-- No market-data, prediction, or business tables (see docs/ai/tasks/QL-003_DATABASE_FOUNDATION.md).

CREATE TABLE app_settings (
    id              UUID PRIMARY KEY,
    setting_key     VARCHAR(255) NOT NULL,
    setting_value   TEXT,
    description     TEXT,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_app_settings_setting_key UNIQUE (setting_key)
);

CREATE TABLE job_execution (
    id              UUID PRIMARY KEY,
    job_name        VARCHAR(255) NOT NULL,
    started_at      TIMESTAMPTZ NOT NULL,
    finished_at     TIMESTAMPTZ,
    status          VARCHAR(50) NOT NULL,
    message         TEXT,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_job_execution_job_name ON job_execution (job_name);
CREATE INDEX idx_job_execution_status ON job_execution (status);

CREATE TABLE model_registry (
    id              UUID PRIMARY KEY,
    model_name      VARCHAR(255) NOT NULL,
    model_version   VARCHAR(100) NOT NULL,
    model_type      VARCHAR(100) NOT NULL,
    status          VARCHAR(50) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_model_registry_name_version UNIQUE (model_name, model_version)
);

CREATE INDEX idx_model_registry_model_name ON model_registry (model_name);
CREATE INDEX idx_model_registry_status ON model_registry (status);
