package com.mr712.vanillaisolator;

import com.mr712.vanillaisolator.registry.VanillaRegistrySnapshot;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VanillaIsolator implements ClientModInitializer {
    public static final String MOD_ID = "vanillaisolator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        VanillaRegistrySnapshot.init();
        LOGGER.info("Vanilla Isolator loaded. Modded registries and DataTrackers will be isolated on vanilla/multiplayer servers.");
    }
}
