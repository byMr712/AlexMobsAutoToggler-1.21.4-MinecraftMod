> **Language:** [Русский](README.md) · English

# Vanilla Recipe Isolator (Minecraft 1.21.4 Fabric)

Universal client-side mod for **Minecraft 1.21.4 (Fabric)** providing clean compatibility and mod registry isolation when connecting to vanilla and cross-version servers (including ViaFabricPlus).

---

## Features & Fixes

When content mods (such as `tide`, `regs-more-foods`, `StrawBed`, `TotemCraft`, `Alex's Mobs`, etc.) are installed on the client, they register custom blocks, items, and entity fields into the game's shared registries. When joining vanilla servers, this causes severe issues:

1. **Block & Chunk Palette ID Shifts**:
   - Custom mod blocks shift the global `Block.STATE_IDS` table. On vanilla servers (or via ViaFabricPlus), blocks get rendered as wrong blocks (e.g. sugar cane renders as budding amethyst).
   - **Vanilla Recipe Isolator** intercepts chunk palette lookups and `Block.STATE_IDS`, supplying the pure vanilla 1.21.4 ID map.

2. **Item ID Normalization (`Item.byRawId`)**:
   - Ensures network item packets strictly use vanilla 1.21.4 raw IDs on multiplayer servers.

3. **Entity `DataTracker` Desync Fix**:
   - Entity mods (such as `Alex's Mobs`) inject extra tracked fields into `LivingEntity`, causing `IllegalStateException: Invalid entity data item type` crashes on vanilla servers.
   - **Vanilla Recipe Isolator** dynamically aligns field indices and suppresses incompatible data updates.

---

## Behavior Matrix

| Mode | Behavior |
|---|---|
| **Vanilla Server / ViaFabricPlus (26.x, 1.21.x, etc.)** | Isolation **ACTIVE**: pure vanilla registries are used, eliminating all visual and network desyncs |
| **Modded Server with matching mods** | Synchronized normally via Fabric API |
| **Singleplayer** | Isolation **OFF**: all custom blocks, items, and entities from your mods work 100% |

Activation is completely automatic upon joining any server without requiring manual configuration.

---

## Build

```bash
gradlew build
```

Output: `build/libs/VanillaRecipeIsolator-1.21.4-byMr712.jar`.

## License

Licensed under **Apache License 2.0**.