# QuantLens Durable Decisions

Claude must not alter these decisions without explicit approval.

## D-001 — Product purpose

QuantLens begins as a personal AI investment research and virtual simulation platform for the Vietnam stock market.

## D-002 — Architecture

Use a modular monolith for the Java business API plus a separate Python AI service.

## D-003 — Communication

The web calls the Spring Boot API through REST. The Spring Boot API calls the Python AI service through REST.

## D-004 — Primary database

Use PostgreSQL 17 as the system of record.

## D-005 — Cache

Use Redis only as a disposable cache or narrowly justified coordination mechanism.

## D-006 — Object storage

Use MinIO for model files, datasets, reports, and exports.

## D-007 — Frontend

Use React 19, TypeScript, Vite, Ant Design 5, TanStack Query, Zustand, and React Router.

## D-008 — Backend

Use Java 25, Spring Boot 3.5+, Spring Data JPA, Flyway, REST, and OpenAPI.

## D-009 — AI service

Use Python 3.13, FastAPI, Pandas, NumPy, scikit-learn, XGBoost, and MLflow.

## D-010 — Prediction policy

Default horizon is 5 trading sessions. Default recommendation confidence threshold is 60% and configurable. The system can refuse to recommend.

## D-011 — Initial models

Logistic Regression is the baseline champion candidate. XGBoost is the first challenger.

## D-012 — Human promotion

Model promotion requires human approval during the MVP.

## D-013 — Traceability

Every prediction must be reproducible or traceable to model version, feature version, dataset/input snapshot, confidence, evidence, and eventual evaluation.

## D-014 — Provider abstraction

Market-data provider access must be isolated behind a provider contract. Initial provider implementation is replaceable.

## D-015 — Deferred technologies

Do not use Kafka, RabbitMQ, Kubernetes, microservices, GraphQL, Elasticsearch, Airflow, LangChain, LangGraph, or MCP in the MVP without a new approved decision.
