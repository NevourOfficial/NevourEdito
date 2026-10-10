# Contributing to Nevour Editor

Thanks for your interest in Nevour Editor. The project is at an early planning stage, so clear discussion and small, reviewable changes are especially helpful.

## Before starting

- Search existing issues and discussions to avoid duplicate work.
- For large changes, open an issue first to discuss the problem and proposed approach.
- Keep changes focused and explain why they are needed.
- Do not commit copyrighted media, fonts, music, or other assets unless redistribution rights are documented.
- Never include API keys, access tokens, signing keys, or private credentials in commits.

## Proposed code expectations

When implementation begins:

- Prefer small modules with documented interfaces.
- Keep local editing and export independent of the remote asset catalog.
- Avoid downloading assets during app startup.
- Consider low-memory devices and cancellation/recovery for long-running work.
- Add tests for project-file compatibility, asset integrity, timeline operations, and export behavior where practical.
- Document any new dependencies and their licenses.

## Pull requests

Please include:

1. What problem the change solves.
2. A short summary of the implementation.
3. How you tested it, including device/model and Android version when relevant.
4. Screenshots or a short recording for UI changes, when useful.
5. Any performance, compatibility, licensing, or migration notes.

By submitting a contribution, you agree that it is provided under the repository's applicable license unless a separate written agreement says otherwise.
