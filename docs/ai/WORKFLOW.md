# Claude Implementation Workflow

## Start of task

1. Read `CLAUDE.md`.
2. Read all files in `docs/ai/`.
3. Inspect `git status`.
4. Inspect relevant code and configuration.
5. Confirm the exact current task from `TASKS.md`.
6. Identify affected modules, contracts, migrations, and tests.
7. Implement without starting unrelated work.

## During implementation

Use short cycles:

```text
Inspect
→ Change
→ Compile/Test
→ Correct
→ Re-run
```

Prefer working software over large speculative scaffolding.

## Before completion

1. Review the diff for unrelated changes.
2. Run all relevant checks.
3. Check that configuration examples are updated.
4. Check for accidentally committed secrets.
5. Check that new API behavior has tests or a justified test strategy.
6. Check that database changes have a new migration.
7. Update handoff documentation.

## Completion report format

```markdown
## Task
QL-XXX — Task title

## Result
Completed | Partially completed | Blocked

## Summary
...

## Files created
- ...

## Files modified
- ...

## Migrations
- ...

## Commands executed
- `...`

## Verification results
- PASS/FAIL — ...

## Unresolved issues
- ...

## Assumptions
- ...

## Risks
- ...

## Git diff stat
...

## Suggested commit
`type(scope): message`
```

## Failure policy

A partially working implementation is not complete.

When blocked:
- keep safe completed work if useful;
- do not fake missing behavior;
- document the blocker precisely;
- state the minimum information or action needed to continue.
