# Roadmap

This roadmap describes intended stages. Dates and performance targets will be added only when there is a realistic implementation and test plan.

## Stage 0 — Repository and architecture

- [x] Create the repository
- [x] Add initial project documentation
- [ ] Confirm repository visibility and license choice
- [ ] Define the first supported Android API range
- [ ] Write architecture decision records for media playback, rendering, project format, and asset delivery

## Stage 1 — Buildable foundation

- [ ] Add a minimal Android app that builds in CI
- [ ] Establish UI navigation and an accessible, touch-friendly design system
- [ ] Create a versioned project format and save/load tests
- [ ] Add media import and permission handling
- [ ] Add basic playback and a minimal timeline
- [ ] Add automated checks for formatting, tests, and builds

## Stage 2 — Core editing

- [ ] Trim, split, reorder, crop, and rotate clips
- [ ] Add audio tracks and basic volume controls
- [ ] Add text overlays
- [ ] Save, reopen, and recover projects safely
- [ ] Export a first supported output format and report errors clearly

## Stage 3 — Performance and reliability

- [ ] Test on representative low-, mid-, and high-range Android devices
- [ ] Measure memory use, preview frame pacing, startup time, and export reliability
- [ ] Add adaptive preview resolution and proxy media where appropriate
- [ ] Handle backgrounding, low storage, cancellation, and failed exports
- [ ] Publish measured limitations instead of promising unsupported performance

## Stage 4 — Optional modules and online library

- [ ] Define a versioned asset catalog schema
- [ ] Add on-demand asset downloads with checksums and license metadata
- [ ] Implement local caching, cache cleanup, retries, and offline behavior
- [ ] Add optional transitions, filters, presets, stickers, and fonts
- [ ] Allow optional modules to be disabled without breaking existing projects

## Stage 5 — Advanced tools

- [ ] Keyframes and more advanced color tools
- [ ] More transitions, effects, captions, and templates
- [ ] Evaluate AI-assisted features based on device capability, privacy, licensing, and maintenance cost

## Initial performance goals

The 6 GB RAM device target is a design constraint, not a guarantee. Candidate goals include responsive UI interactions, smooth timeline scrolling, and 1080p/30 fps editing where device codecs and thermals allow. These must be measured on real hardware before being advertised as supported.
