package com.mr712.modded2vanilla.compat.tide;

import com.mr712.modded2vanilla.Modded2Vanilla;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.ArrayList;
import java.util.List;

public final class TideRegistryDeferHandler {

    private static final List<Pair<RegistryKey<Item>, Item>> PENDING_REGISTRATIONS = new ArrayList<>();
    private static boolean flushed = false;

    @SuppressWarnings("unchecked")
    public static synchronized Object defer(Object key, Object item) {
        if (flushed) {
            return Registry.register(Registries.ITEM, (RegistryKey<Item>) key, (Item) item);
        }
        PENDING_REGISTRATIONS.add(new Pair<>((RegistryKey<Item>) key, (Item) item));
        return item;
    }

    public static synchronized void flush() {
        if (flushed || PENDING_REGISTRATIONS.isEmpty()) {
            return;
        }
        flushed = true;
        Modded2Vanilla.LOGGER.info("Flushing {} deferred Tide items to Registries.ITEM after vanilla initialization...", PENDING_REGISTRATIONS.size());
        for (final Pair<RegistryKey<Item>, Item> entry : PENDING_REGISTRATIONS) {
            Registry.register(Registries.ITEM, entry.key(), entry.value());
        }
        PENDING_REGISTRATIONS.clear();
    }

    private record Pair<K, V>(K key, V value) {
    }
}
