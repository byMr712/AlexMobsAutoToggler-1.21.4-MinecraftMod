package com.mr712.vanillarecipeisolator;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VanillaRecipeIsolator implements ClientModInitializer {
    public static final String MOD_ID = "vanillarecipeisolator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Vanilla Recipe Isolator loaded. Modded DataTrackers will be isolated on vanilla/multiplayer servers.");
    }
}
