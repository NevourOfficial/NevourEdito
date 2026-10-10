# Nevour Editor

**Nevour Editor** is a planned free, open-source, modular mobile video editor by **Nevour**, designed with smooth editing on everyday Android phones—including devices with 6 GB of RAM—as a core goal.

> **Project status: early setup.** This repository currently contains project documentation and planning. A working editor, installable APK, and performance claims are not available yet.

## Project goals

- Essential editing tools without requiring an account or downloading a large asset pack at launch.
- Core editing and exporting of local projects that remains usable offline.
- Optional templates, presets, effects, stickers, and other library assets fetched only when requested.
- Cached downloads verified with checksums.
- Modular features that users can disable and developers can omit from customized builds.
- Responsive UI, memory-aware preview rendering, and reliable exports on mid-range devices.
- Free use and source code available for the community to inspect and modify.

## Planned features

This list is a roadmap, not a claim that these tools already work.

- Multi-track timeline
- Trim, split, crop, rotate, reorder, and speed controls
- Audio/music tracks, volume controls, and fades
- Text, titles, and subtitles
- Transitions, filters, color adjustments, and effects
- Keyframes and reusable presets
- Optional downloadable templates, stickers, fonts, and effects
- Project save/restore and export settings
- Optional advanced or AI-assisted modules, evaluated after the editing foundation is stable

## Design principles

1. **Offline-first core:** opening and editing a local project must not depend on the asset service.
2. **Download on demand:** no large bundled asset library and no asset downloads just because the app launched.
3. **Modular by design:** optional features should have clear boundaries and dependencies.
4. **Performance before spectacle:** measure memory use, preview smoothness, thermal behavior, and export reliability on real devices.
5. **Transparent licensing:** code and downloadable media have separate licenses and attribution requirements.
6. **No false promises:** publish measured device support and limitations as they are tested.

## Planned technical direction

The initial target is Android. The architecture will be validated with small prototypes before it is locked in. Current candidates include Kotlin and Jetpack Compose for the app UI, Android media APIs for decoding/encoding, and a native rendering/media layer where benchmarks justify it. These are proposals, not finalized implementation choices.

See [ARCHITECTURE.md](ARCHITECTURE.md) and [ROADMAP.md](ROADMAP.md).

## Build status

There is no runnable app build in this repository yet. Setup instructions will be added when the first buildable Android project is committed.

## Contributing

Early feedback, issue reports, and architecture discussion are welcome. Please read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.

## License

The source code is intended to use the Apache License 2.0. Downloadable presets, fonts, sound effects, and other media may use different licenses and will need their own attribution and license records.
