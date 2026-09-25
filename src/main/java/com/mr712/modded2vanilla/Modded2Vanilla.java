package com.mr712.modded2vanilla;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Modded2Vanilla implements ClientModInitializer {
    public static final String MOD_ID = "modded2vanilla";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Modded2Vanilla loaded. Modded DataTrackers will be isolated on vanilla/multiplayer servers.");
    }
}
