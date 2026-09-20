> **Language:** English · [Русский](README.md)

# AlexMobs AutoToggler (Minecraft 1.21.4 Fabric)

A client-side mod for **Minecraft 1.21.4 (Fabric)** that fixes a protocol error caused by **Alex's Mobs Continued** when connecting to a multiplayer server that does not have the mod installed (while you do).

---

## About the Mod

**Alex's Mobs Continued** (Fabric build) registers one extra synced data field on every living entity (`LivingEntity`) on the client. As a result, the tracked-data field ids become "shifted" by one relative to a vanilla (or differently-modded) server.

### The Symptom: crash when joining a server

On a vanilla multiplayer server the id mismatch breaks entity data sync:

```
IllegalStateException: Invalid entity data item type for field 17 ...
old=false(Boolean), new=0(Integer)
```

(the fish `fromBucket`/`variant` fields: the server sends an `Integer`, the client expects a `Boolean`).

### The Fix

The mod intercepts client-side handling of incoming entity data updates (a mixin into `DataTracker.writeUpdatedEntries`):

- Fields whose type does not match at the current slot but matches at `id + 1` are shifted by `+1` (**self-correcting compensation** — no hard-coded dependency on Alex's Mobs Continued field id);
- Fields that already match are left untouched;
- Fully unrecognized entries are skipped and logged instead of crashing.

### Where It Applies

| Situation | Behavior |
|---|---|
| Multiplayer server without Alex's Mobs | Server field layout differs from the client's — the mod compensates for the offset |
| Multiplayer server with Alex's Mobs | The mod is disabled; it does not affect **Alex's Mobs Continued** behavior |
| Single-player game | The mod is disabled; it does not affect **Alex's Mobs Continued** behavior |

Compensation activates automatically when connecting to any multiplayer server and deactivates automatically in singleplayer (no configuration needed).

---

## Building

```
gradlew build
```

Output: `build/libs/AlexMobsAutoToggler-1.21.4-byMr712.jar`.

## Installation

1. Install **Fabric Loader** (0.16.0+) and **Fabric API** for 1.21.4.
2. Copy `AlexMobsAutoToggler-1.21.4-byMr712.jar` into the `mods/` folder.
3. Launch the game (requires Java 21).

## Technical Details

- Target methods (Yarn 1.21.4+build.7): `DataTracker.writeUpdatedEntries` and `DataTracker.copyToFrom`.
- Client-only modification (`environment: client`); the mixin is not applied on a server.
- Compatibility: Fabric Loader 0.16.0+, Minecraft 1.21.4, Java 21, requires Fabric API.

## License

Licensed under the **Apache License 2.0**.