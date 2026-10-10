# Nevour Editor

**Nevour Editor** is a free, open-source mobile video editor project by **Nevour**, with a focus on modularity and efficient editing on everyday Android phones.

> **Current status: interactive UI prototype.** The Android Compose screens and UI interactions are in place for review. Video import, real playback, editing operations, persistent projects, asset downloads, and encoding/export are not connected yet. The preview clips and projects are sample UI content, not real media.

## Review the UI

The current prototype includes:
- Home and recent-projects screen
- Editor layout with sample preview, timeline tracks, clip selection, tool panels, and export settings dialog
- Projects screen
- Asset library categories and selectable sample assets
- Settings screen with interactive demo toggles
- Bottom navigation between the main screens

The controls demonstrate the intended interaction flow. They do not yet edit or export actual video. This phase is specifically for reviewing the layout and workflow before implementing the media engine.

## Open the Android project

1. Open this repository in Android Studio.
2. Allow Gradle to sync and install the required Android SDK (compile SDK 35, JDK 17).
3. Run the `app` configuration on an Android emulator or device.

The project uses Kotlin, Jetpack Compose, and Material 3. No media assets are downloaded at startup.

## Project goals

- Essential editing without an account or a large asset download on launch.
- Keep local project editing independent of remote asset services.
- Fetch optional presets, effects, stickers, fonts, and templates only when requested.
- Cache downloaded assets and verify them with checksums.
- Let users disable optional features and developers create customized builds.
- Measure performance on real devices before publishing compatibility claims.
- Keep the source code free and available for community modification.

## Planned features

These are future implementation goals, not features available in the current UI-only prototype:
- Multi-track editing with trim, split, crop, rotate, reorder, and speed controls
- Audio tracks, volume controls, and fades
- Text, titles, and subtitles
- Transitions, filters, color adjustments, and effects
- Keyframes, presets, and optional downloadable assets
- Versioned projects, local save/restore, and video export
- Advanced or AI-assisted modules after the core editor is stable

## Architecture and roadmap

See [ARCHITECTURE.md](ARCHITECTURE.md), [ROADMAP.md](ROADMAP.md), and [CONTRIBUTING.md](CONTRIBUTING.md).

## License

The source code is intended to use Apache License 2.0. Downloadable media may have separate licenses and attribution requirements.
