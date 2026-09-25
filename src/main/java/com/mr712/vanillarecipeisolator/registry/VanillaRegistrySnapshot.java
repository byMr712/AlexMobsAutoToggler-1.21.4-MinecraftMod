package com.mr712.vanillarecipeisolator.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class VanillaRegistrySnapshot {
    private static final Logger LOGGER = LoggerFactory.getLogger("VanillaRecipeIsolator");

    private static BlockState[] vanillaIdToBlockState;
    private static Map<BlockState, Integer> vanillaBlockStateToId;

    private static Item[] vanillaIdToItem;
    private static Map<Item, Integer> vanillaItemToId;

    private static boolean initialized = false;

    private VanillaRegistrySnapshot() {
    }

    public static synchronized void init() {
        if (initialized) {
            return;
        }

        try {
            // 1. Snapshot BlockStates for all "minecraft" namespace blocks
            List<BlockState> stateList = new ArrayList<>(26000);
            Map<BlockState, Integer> stateMap = new IdentityHashMap<>(26000);

            for (Block block : Registries.BLOCK) {
                Identifier id = Registries.BLOCK.getId(block);
                if (id != null && "minecraft".equals(id.getNamespace())) {
                    for (BlockState state : block.getStateManager().getStates()) {
                        int index = stateList.size();
                        stateList.add(state);
                        stateMap.put(state, index);
                    }
                }
            }

            vanillaIdToBlockState = stateList.toArray(new BlockState[0]);
            vanillaBlockStateToId = stateMap;

            // 2. Snapshot Items for all "minecraft" namespace items
            List<Item> itemList = new ArrayList<>(1500);
            Map<Item, Integer> itemMap = new IdentityHashMap<>(1500);

            for (Item item : Registries.ITEM) {
                Identifier id = Registries.ITEM.getId(item);
                if (id != null && "minecraft".equals(id.getNamespace())) {
                    int index = itemList.size();
                    itemList.add(item);
                    itemMap.put(item, index);
                }
            }

            vanillaIdToItem = itemList.toArray(new Item[0]);
            vanillaItemToId = itemMap;

            initialized = true;
            LOGGER.info("[VanillaRecipeIsolator] Captured vanilla snapshot: {} BlockStates, {} Items.",
                    vanillaIdToBlockState.length, vanillaIdToItem.length);
        } catch (Throwable t) {
            LOGGER.error("[VanillaRecipeIsolator] Failed to build vanilla registry snapshot", t);
        }
    }

    public static BlockState getVanillaBlockState(int rawId) {
        if (!initialized || rawId < 0 || vanillaIdToBlockState == null || rawId >= vanillaIdToBlockState.length) {
            return null;
        }
        return vanillaIdToBlockState[rawId];
    }

    public static int getVanillaBlockStateRawId(BlockState state) {
        if (!initialized || state == null || vanillaBlockStateToId == null) {
            return -1;
        }
        Integer id = vanillaBlockStateToId.get(state);
        return id != null ? id : -1;
    }

    public static Item getVanillaItem(int rawId) {
        if (!initialized || rawId < 0 || vanillaIdToItem == null || rawId >= vanillaIdToItem.length) {
            return null;
        }
        return vanillaIdToItem[rawId];
    }

    public static int getVanillaItemRawId(Item item) {
        if (!initialized || item == null || vanillaItemToId == null) {
            return -1;
        }
        Integer id = vanillaItemToId.get(item);
        return id != null ? id : -1;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
