package com.notunanancyowen.alexmobsautotoggler;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlexMobsAutoToggler implements ClientModInitializer {
    public static final String MOD_ID = "alexmobsautotoggler";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("AlexMobs AutoToggler loaded. Data tracker field remapping is active on multiplayer servers.");
    }
}