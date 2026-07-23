# QuantLens Tasks

## Current

- [ ] **QL-001 — Assess repository and bootstrap Claude governance files**

## QL-001 objective

Inspect the actual repository, integrate this rules package, and make only the minimum safe repository adjustments required to establish a clean starting point.

### Requirements

1. Inspect:
   - current branches and active branch;
   - `git status`;
   - complete top-level tree;
   - existing README and configuration;
   - existing source modules;
   - existing build files;
   - existing Docker files;
   - existing CI workflows;
   - existing documentation.

2. Copy or merge the supplied governance files into the repository:
   - `/CLAUDE.md`
   - `/docs/ai/*.md`

3. Do not overwrite valuable existing documentation blindly. Merge conflicts carefully and report them.

4. Update:
   - `PROJECT_STATE.md` with actual repository status;
   - `CODE_MAP.md` with the actual tree and module descriptions;
   - `CHANGELOG.md`;
   - this task list.

5. If the repository is empty or nearly empty:
   - create only the agreed top-level directories;
   - add `.gitkeep` only where needed;
   - create or refine a root `README.md` with product summary, planned structure, prerequisites, and a clear statement that implementation has not yet started;
   - do not generate Spring Boot, React, or FastAPI application code in QL-001.

6. If application code already exists:
   - do not restructure or rewrite it in QL-001;
   - document the differences from the agreed architecture;
   - propose the smallest next task.

7. Verify Markdown links and repository cleanliness.

### Acceptance criteria

- [ ] `CLAUDE.md` exists at repository root.
- [ ] All required `docs/ai` files exist.
- [ ] Repository state is accurately documented.
- [ ] No application feature code is created unless it already existed.
- [ ] No architecture-changing dependency is introduced.
- [ ] `git diff --stat` is reported.
- [ ] A suggested commit message is returned.
- [ ] Claude stops after QL-001.

## Next

To be defined after the QL-001 review. Likely candidates:

- [ ] QL-002 — Initialize reproducible local development foundation.
- [ ] QL-003 — Initialize PostgreSQL and migration baseline.
- [ ] QL-004 — Initialize Spring Boot API health slice.
- [ ] QL-005 — Initialize FastAPI health slice.
- [ ] QL-006 — Initialize React application shell.

The architecture reviewer will approve the exact next task after reviewing QL-001.

## Completed

None.
