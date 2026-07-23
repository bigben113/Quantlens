# Claude Execution Prompt — QL-002

Read `CLAUDE.md`, `docs/ai/RULES.md`, and every other file under `docs/ai/` before doing anything else.

Execute only:

```text
docs/ai/tasks/QL-002_FULL_STACK_FOUNDATION.md
```

The goal is one complete runnable vertical slice:

```text
React Web
    ↓ REST
Spring Boot API
    ↓ REST
FastAPI AI Service
```

Important:

- Implement the health/status flow across all three layers.
- Do not implement business features, databases, authentication, market data, or ML models.
- Keep scope exactly within QL-002.
- Run the real build and test commands.
- Verify the end-to-end flow where the environment allows.
- Update the required `docs/ai` state files.
- Return the handoff report defined in `docs/ai/WORKFLOW.md`.
- Do not commit or push.
- Stop after QL-002 and wait for review.
