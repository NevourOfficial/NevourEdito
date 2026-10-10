# Architecture Notes

This is an initial proposal. Major technology choices should be validated with small prototypes and recorded as decisions before implementation grows.

## Product boundaries

### Core app
- Project browser and editor UI
- Media import and local file access
- Project save/load and migrations
- Playback, timeline state, and export orchestration
- Settings, accessibility, diagnostics, and crash-safe recovery

### Media pipeline
- Decode and encode through Android-supported media APIs where possible
- Separate timeline/project state from preview rendering
- Use a rendering abstraction so effects can be introduced incrementally
- Benchmark native code and GPU APIs before committing to a complex engine

### Optional modules
Potential modules include basic editing, text, audio, transitions, color, stickers/templates, and advanced tools. Modules must declare dependencies and capability requirements. Projects containing an unavailable module should preserve its data and clearly explain what cannot be rendered or edited.

### Asset service
- Remote storage is for optional downloadable assets, not project files by default.
- Store a versioned catalog separately from asset blobs.
- Each catalog entry should include a stable ID, version, file size, cryptographic checksum, media type, compatibility range, license, attribution, and source/provenance.
- Download only when requested; verify before marking an asset ready.
- Use temporary files and atomic promotion to avoid partial/corrupt cache entries.
- Support retries, cancellation, cache limits, and explicit cache clearing.
- If the server is unavailable, the local editor must continue to open, edit, and export local projects.

## Proposed repository layout

As the project grows, organize code around clear responsibilities rather than creating empty modules prematurely. A possible layout:

- `app/` — Android application and navigation
- `core/project/` — project model, serialization, migrations
- `core/media/` — media abstraction and playback/export orchestration
- `core/timeline/` — timeline model and editing operations
- `core/rendering/` — preview compositor and effects interfaces
- `feature/*` — independently testable UI/features
- `asset-system/` — catalog, downloader, checksum validation, cache
- `docs/adr/` — architecture decision records
- `tools/` — developer scripts and asset validation

## Project format principles

- Use a versioned, documented schema.
- Store references to user media rather than silently copying large files.
- Persist edit decisions non-destructively where feasible.
- Use stable clip/track IDs rather than array positions as identities.
- Validate input and support forward-compatible preservation of unknown fields.
- Write changes atomically and keep backups/recovery behavior in mind.

## Performance and reliability

- Never load full-resolution media frames into memory unnecessarily.
- Bound caches and release decoder/renderer resources promptly.
- Adapt preview resolution and frame rate when device load is high.
- Keep expensive processing off the UI thread.
- Make long operations cancellable and provide clear progress/error states.
- Profile startup, timeline interaction, playback, and export on physical devices.

## Security, privacy, and licensing

- Do not upload user media without a clear opt-in and explicit product behavior.
- Do not put credentials or signing keys in the repository.
- Treat all downloaded assets and catalog responses as untrusted until validated.
- Track license and attribution per downloadable asset; the code license does not automatically license media assets.
