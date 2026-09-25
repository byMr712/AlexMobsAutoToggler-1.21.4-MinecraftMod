> **Language:** [Русский](README.md) · English

# Vanilla Recipe Isolator (Minecraft 1.21.4 Fabric)

Universal client-side mod for **Minecraft 1.21.4 (Fabric)** — the perfect companion for **ViaFabricPlus** and vanilla multiplayer. Provides full mod registry isolation when joining servers to eliminate all visual and network desyncs.

---

## 🌟 Perfect Synergy with ViaFabricPlus

If you love playing singleplayer with content mods (new blocks, foods, tools, mobs, and decorations) while also connecting to servers of any version via **ViaFabricPlus** — this mod is made for you!

Client-side content mods normally pollute local registries and break protocol translators. **Vanilla Recipe Isolator** fixes this completely: you can keep all your favorite content mods in your `mods` folder and seamlessly connect via ViaFabricPlus to servers of any version (from **26.x** and **1.21.x** to **1.16**, **1.12**, and **1.8**).

---

## 🛠️ Features & Fixes

When content mods (such as `tide`, `regs-more-foods`, `StrawBed`, `TotemCraft`, `Alex's Mobs`, etc.) are installed on the client, they register custom blocks, items, and entity fields into the game's shared registries. When joining vanilla servers or using ViaFabricPlus, this causes severe issues:

1. **Block & Chunk Palette ID Shifts (`Block.STATE_IDS`)**:
   - Custom mod blocks shift the global `Block.STATE_IDS` table. On vanilla servers (or via ViaFabricPlus), blocks get rendered as wrong blocks (e.g. sugar cane renders as budding amethyst, chests look like mod blocks, etc.).
   - **Vanilla Recipe Isolator** intercepts chunk palette lookups and `Block.STATE_IDS`, supplying the pure vanilla 1.21.4 ID map.

2. **Item ID Normalization (`Item.byRawId`)**:
   - Ensures network item packets strictly use vanilla 1.21.4 raw IDs on multiplayer servers.

3. **Entity `DataTracker` Desync Fix**:
   - Entity mods (such as `Alex's Mobs`) inject extra tracked fields into `LivingEntity`, causing `IllegalStateException: Invalid entity data item type` crashes on multiplayer servers.
   - **Vanilla Recipe Isolator** dynamically aligns field indices and suppresses incompatible data updates.

---

## 🎮 Behavior Matrix

| Mode | Behavior |
|---|---|
| **ViaFabricPlus (26.x, 1.21.x, 1.16, 1.12, 1.8, etc.)** | Isolation **ACTIVE**: pure vanilla registries are used, ViaFabricPlus works flawlessly |
| **Vanilla Multiplayer Server** | Isolation **ACTIVE**: all visual and network desyncs eliminated |
| **Modded Server with matching mods** | Synchronized normally via Fabric API |
| **Singleplayer** | Isolation **OFF**: all custom blocks, items, recipes, and entities from your mods work 100% |

Activation is completely automatic upon joining any server without requiring manual configuration.

---

## 📦 Build

```bash
gradlew build
```

Output: `build/libs/VanillaRecipeIsolator-1.21.4-byMr712.jar`.

## 🚀 Installation

1. Install **Fabric Loader** (0.16.0+) and **Fabric API** for 1.21.4.
2. *(Recommended)* Install **ViaFabricPlus** to connect to any server version.
3. Place `VanillaRecipeIsolator-1.21.4-byMr712.jar` in your `mods/` directory.
4. Launch the game.

---

## 📄 License

Licensed under **Apache License 2.0**.