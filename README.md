# QuantLens

QuantLens là một nền tảng nghiên cứu, giả lập và huấn luyện các mô hình AI đầu tư cho thị trường chứng khoán Việt Nam. Sản phẩm mang giá trị cốt lõi nằm ở khả năng lọc thông tin, phân tích tác động, đưa ra dự báo có độ tin cậy, chạy danh mục giả lập và tự đánh giá hiệu quả theo thời gian.

QuantLens is a personal AI investment research and virtual simulation platform focused on the Vietnam stock market. It is built for research, backtesting, and virtual portfolio evaluation — it is not a public advisory service and does not perform automatic live trading.

Durable product and architecture decisions live under [`docs/ai/`](docs/ai/); this README covers what exists today and how to run it.

## Current architecture

```text
React Web (apps/web)
    ↓ REST
Spring Boot API (apps/api)
    ↓ REST
FastAPI AI Service (apps/ai-service)
```

This is the QL-002 foundation vertical slice: a health/status check flowing through all three layers. There is no database, cache, object storage, authentication, market data, or ML model wired up yet — see [Current limitations](#current-limitations).

## Prerequisites

- Docker and Docker Compose (recommended path), or:
  - Java 25 + Maven Wrapper (bundled) for `apps/api`
  - Python 3.13 for `apps/ai-service`
  - Node.js 20.19+ and npm for `apps/web`

Copy the environment template before running anything:

```bash
cp .env.example .env
```

## Run everything with Docker Compose

```bash
docker compose up --build
```

This builds and starts all three services together. Configuration comes from `.env` (see `.env.example` for every supported variable); Compose falls back to the same defaults if `.env` is absent.

## Run services individually (hybrid workflow)

### AI service (FastAPI)

```bash
cd apps/ai-service
python -m venv .venv
.venv/Scripts/activate        # Windows; use .venv/bin/activate on macOS/Linux
pip install -r requirements.txt -r requirements-dev.txt
uvicorn app.main:app --reload --port 8000
```

### API (Spring Boot)

```bash
cd apps/api
./mvnw spring-boot:run
```

Uses `AI_SERVICE_BASE_URL` (default `http://localhost:8000`) to reach the AI service.

### Web (React)

```bash
cd apps/web
npm install
npm run dev
```

Uses `VITE_API_BASE_URL` (default `http://localhost:8080`) to reach the Spring Boot API. The browser never calls the AI service directly.

## Test and build commands

```bash
# AI service
cd apps/ai-service
pytest

# API
cd apps/api
./mvnw test
./mvnw package

# Web
cd apps/web
npm run lint
npm run test -- --run
npm run build
```

## Service URLs

| Service    | URL                                      |
|------------|-------------------------------------------|
| Web        | http://localhost:5173                      |
| API        | http://localhost:8080                      |
| API health | http://localhost:8080/api/v1/system/health |
| Swagger    | http://localhost:8080/swagger-ui.html      |
| AI service | http://localhost:8000                      |
| AI health  | http://localhost:8000/health               |

Ports are configurable via `.env` (`WEB_PORT`, `API_PORT`, `AI_SERVICE_PORT`).

## Current limitations

- No PostgreSQL, Redis, or MinIO integration yet.
- No authentication.
- No market-data providers, feature engineering, or ML models.
- No prediction, evaluation, or virtual portfolio logic.
- The only end-to-end behavior is the health/status check across Web → API → AI service.

## Repository structure

```text
quantlens/
├── apps/
│   ├── api/          # Spring Boot modular monolith (Java 25, Spring Boot 3.5)
│   ├── ai-service/    # FastAPI AI service (Python 3.13)
│   └── web/           # React 19 + TypeScript + Vite web app
├── docs/
│   └── ai/            # AI governance rules and project handoff state
├── infrastructure/
│   └── docker/         # Reserved for future infrastructure assets
├── packages/           # Reserved for shared contracts/assets, introduced only when needed
├── scripts/            # Reserved for repository automation
├── docker-compose.yml
├── .env.example
└── CLAUDE.md
```
