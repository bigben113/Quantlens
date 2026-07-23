# Prompt to execute in Claude Code

Copy the text below into Claude Code from the root of the local QuantLens repository after copying this rules package into the repository.

---

Read `CLAUDE.md` and every file under `docs/ai/` before doing anything else.

Execute only the current task `QL-001` from `docs/ai/TASKS.md`.

Important constraints:

- First inspect the real repository and Git state.
- Do not generate application feature code in this task.
- Do not initialize Spring Boot, React, or FastAPI unless the repository already contains such code and the task only requires documenting it.
- Integrate or merge the governance files carefully.
- Update the AI state documents with facts observed from the repository.
- Do not invent successful commands or repository details.
- Run appropriate validation for Markdown and repository cleanliness.
- Return the exact completion report required by `docs/ai/WORKFLOW.md`.
- Include `git diff --stat`.
- Suggest one commit message.
- Stop after QL-001 and wait for review.
