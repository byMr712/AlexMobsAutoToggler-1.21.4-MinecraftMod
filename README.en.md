> **Language:** [Русский](README.md) · English

# Modded2Vanilla (Minecraft 1.21.4 Fabric)

**Modded2Vanilla** is a universal client-side compatibility mod for **Minecraft 1.21.4 (Fabric)** that allows you to play singleplayer with your favorite content mods (adding blocks, mobs, food, weapons, decorations) and **seamlessly connect to any external multiplayer servers (Vanilla, Paper, Purpur, Spigot, Realms, or via ViaFabricPlus)** without having to disable your mods or manage separate launcher profiles.

---

## 🎯 Primary Purpose

When content mods are installed on the client, they register custom blocks, items, and entities into Minecraft's global registries. When attempting to join a regular server (Vanilla, Paper, Spigot, etc.) or a multi-version server via **ViaFabricPlus**, network discrepancies occur:
- Global block and item IDs get shifted (e.g. vanilla sugar cane or oak logs rendering as custom mod items/blocks).
- Entity metadata packets break (`Invalid entity data item type`), causing client disconnects/crashes when encountering vanilla entities.
- Network recipe synchronization gets corrupted.

**Modded2Vanilla** intelligently detects the connection context:
- When joining a vanilla/remote server, it isolates conflicting mod entries and presents standard vanilla registry mappings.
- **If a mod is installed on BOTH the client and server**, its functionality is **completely untouched**, allowing custom packets and data to synchronize normally!

---

## 🛠️ Features & Behavior

1. **Block & Chunk Palette ID Protection (`Block.STATE_IDS`)**:
   - Intercepts lookups to the global BlockState ID palette and returns clean 1.21.4 vanilla IDs. This prevents blocks in the world from visually turning into random mod blocks when joining servers.
2. **Item ID Normalization (`Item.byRawId`)**:
   - Ensures that item network packets consistently resolve to standard vanilla IDs during multiplayer sessions.
3. **Smart Entity `DataTracker` Metadata Protection**:
   - Dynamically checks data handler type matches (`entryIdMatches`).
   - If the server is vanilla and the client has mob mods shifting `LivingEntity` indices, indices are safely realigned.
   - If the server also has the mod and sends valid custom data, Modded2Vanilla passes it through without interference.
4. **Seamless ViaFabricPlus Support**:
   - When using **ViaFabricPlus**, the mod ensures version translators (spanning **1.8** to **1.21.x** and **26.x**) operate on pristine vanilla IDs rather than mod-shifted palettes.

---

## 🎮 Automatic Behavior Matrix

The mod requires zero configuration and works automatically:

| Mode | Behavior |
|---|---|
| **Singleplayer** | Isolation **OFF**: all your mod blocks, mobs, recipes, and items function at 100% capacity |
| **Vanilla Server (Vanilla / Paper / Purpur / Spigot / Realms)** | Isolation **ACTIVE**: you connect cleanly without registry conflicts or crashes |
| **Server with matching mods (Fabric)** | Isolation **transparent**: mods present on the server work and synchronize normally |
| **Connecting via ViaFabricPlus (all versions)** | Isolation **ACTIVE**: version translators operate on pristine vanilla tables |

---

## 📦 Build

```bash
gradlew build
```

Output: `build/libs/Modded2Vanilla-1.21.4-byMr712.jar`.

## 🚀 Installation

1. Install **Fabric Loader** (0.16.0+) and **Fabric API** for 1.21.4.
2. Place `Modded2Vanilla-1.21.4-byMr712.jar` in your `mods/` directory.
3. *(Optional)* Install **ViaFabricPlus** if you plan to connect to servers of different versions.
4. Launch the game.

---

## 📄 License

Licensed under **Apache License 2.0**.