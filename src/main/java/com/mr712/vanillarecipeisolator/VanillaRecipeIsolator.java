package com.mr712.vanillarecipeisolator;

import com.mr712.vanillarecipeisolator.registry.VanillaRegistrySnapshot;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VanillaRecipeIsolator implements ClientModInitializer {
    public static final String MOD_ID = "vanillarecipeisolator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        VanillaRegistrySnapshot.init();
        LOGGER.info("Vanilla Recipe Isolator loaded. Modded registries and DataTrackers will be isolated on vanilla/multiplayer servers.");
    }
}
