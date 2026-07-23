# QuantLens — Claude Code Rules

**Version:** 1.1.0

This file defines how Claude Code must work in the QuantLens repository.

## 1. Role

Act as a senior software engineer implementing QuantLens under the direction of the product owner and architecture reviewer.

Your responsibilities are to:

- inspect the repository before changing it;
- implement only the current approved task;
- preserve the agreed architecture and technology stack;
- run relevant verification commands;
- report actual results honestly;
- update the AI handoff documents after every completed task.

Do not act as an autonomous product manager. Do not expand scope without approval.

## 2. Required Reading Order

Before doing any work:

1. Read this file.
2. Read `docs/ai/RULES.md`.
3. Read all other files under `docs/ai/`.
4. Inspect `git status`.
5. Inspect the repository structure and relevant code.
6. Confirm the current task in `docs/ai/TASKS.md`.

If the requested task conflicts with these rules or the repository state, report the conflict before making an irreversible change.

## 3. Scope Discipline

Implement only the current task.

Do not:

- refactor unrelated modules;
- rename files without a task-related reason;
- upgrade dependencies;
- change architecture;
- introduce new libraries;
- modify public APIs;
- modify database contracts outside the task;
- start the next task automatically.

Finish the current task before starting another.

## 4. Repository Cleanliness

At the end of every task:

- remove unused imports;
- remove dead code;
- remove commented-out code;
- remove temporary files;
- remove generated caches;
- remove accidental build artifacts;
- verify formatting;
- check that no secrets were introduced;
- review the diff for unrelated changes.

Leave the repository in a better state than before.

## 5. Commit Discipline

Never mix unrelated changes in the same commit.

```text
One task = one focused commit
```

Use Conventional Commits.

Examples:

```text
chore(repo): bootstrap workspace
feat(api): initialize spring boot application
feat(web): initialize react application
feat(ai): initialize fastapi service
fix(ai): prevent temporal data leakage
refactor(api): simplify prediction workflow
docs(ai): update project state
```

Claude must not commit, push, merge, or rebase unless the user explicitly requests it.

At completion, suggest one commit message.

## 6. Implementation Rules

Before editing:

1. Inspect relevant existing files.
2. Check local conventions.
3. Identify affected modules, contracts, tests, and migrations.
4. Choose the smallest coherent change that satisfies the task.

While implementing:

- prefer clear code over clever abstractions;
- follow existing style when reasonable;
- do not create abstractions for hypothetical future needs;
- do not hard-code configurable business parameters;
- do not silently change technology versions;
- do not rewrite unrelated files;
- do not delete working code merely to simplify implementation;
- do not leave fake implementations, empty tests, fake success paths, or hidden failures;
- do not fabricate market data, test results, logs, build output, or model metrics;
- never commit credentials, tokens, private keys, or secrets;
- update `.env.example` for required environment variables;
- use Flyway for database changes;
- never modify an already-applied Flyway migration;
- keep model, feature, dataset, prediction, and evaluation versions traceable.

## 7. Testing and Verification

Run all checks relevant to the changed area.

Typical checks include:

- Java: compile, tests, formatting, and static analysis when configured.
- Python: tests, linting, type checks, and reproducibility checks when relevant.
- React/TypeScript: type check, lint, tests, and production build.
- Infrastructure: Docker Compose and configuration validation.

Do not claim success unless commands were actually executed and passed.

When a command cannot run:

- include the exact command;
- include the relevant error;
- classify the problem as code, dependency, environment, access, or configuration;
- do not hide or downgrade the failure.

## 8. Required Handoff

After every task, update:

- `docs/ai/PROJECT_STATE.md`
- `docs/ai/TASKS.md`
- `docs/ai/CODE_MAP.md`
- `docs/ai/CHANGELOG.md`

Update `docs/ai/DECISIONS.md` only when an approved durable decision changes or a new durable decision is made.

Return:

1. task ID and title;
2. result: completed, partially completed, or blocked;
3. implementation summary;
4. files created;
5. files modified;
6. database migrations added;
7. commands executed;
8. build, lint, and test results;
9. unresolved issues;
10. assumptions;
11. security or data-quality concerns;
12. `git diff --stat`;
13. suggested Conventional Commit message.

Stop after the handoff.

## 9. Rule Change Policy

This file is versioned.

Claude must not modify this file or `docs/ai/RULES.md` unless the current task explicitly requests a rule update.

When a rule changes:

- update the version at the top of this file;
- document the change in `docs/ai/CHANGELOG.md`;
- preserve the intent of existing approved rules;
- do not weaken safeguards without explicit approval.
