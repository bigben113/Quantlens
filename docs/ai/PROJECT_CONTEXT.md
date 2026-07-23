# QuantLens Project Context

## Product mission

QuantLens is a personal AI research platform for the Vietnam stock market. It is designed to collect evidence, train models, compare model performance, generate conservative predictions, and evaluate decisions over time.

The purpose of the first releases is research and virtual portfolio simulation, not public investment advice or automatic trade execution.

## Core prediction behavior

- Prediction horizon: 5 trading sessions.
- Initial virtual capital: 100,000,000 VND.
- Initial recommendation confidence threshold: 60%.
- The confidence threshold must be configurable.
- The system must be able to return no recommendation.
- Every prediction must retain:
  - instrument;
  - decision timestamp;
  - target horizon;
  - model and model version;
  - feature and dataset versions;
  - input snapshot or reproducible reference;
  - prediction;
  - confidence;
  - evidence;
  - eventual outcome;
  - evaluation result.

## Model lifecycle

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

A challenger must never replace the champion automatically in the MVP.

## Initial implementation path

```text
Market data provider
    ↓
Historical daily prices
    ↓
Normalization
    ↓
Feature engineering
    ↓
Logistic Regression baseline
    ↓
Prediction storage
    ↓
Evaluation after 5 trading sessions
    ↓
Dashboard
```

XGBoost is the first challenger after the Logistic Regression baseline works end to end.

## Initial provider

The first implementation may use Vnstock, but all provider access must be behind a `DataProvider` abstraction because provider APIs and behavior may change.

## Primary product modules

- Market Data
- Feature Engineering
- Prediction
- Model Registry
- Training and Experimentation
- Evaluation
- Virtual Portfolio
- Research Dashboard
- Authentication and Administration

## Non-goals for MVP

- Automatic live trading
- Public financial advice
- Intraday/tick-level analytics
- Social/community features
- Multi-tenant SaaS
- Distributed event-driven architecture
- Advanced LLM agents
