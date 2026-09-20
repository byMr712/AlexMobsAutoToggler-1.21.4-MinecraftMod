package com.notunanancyowen.alexmobsautotoggler;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public final class TogglerState {
    private TogglerState() {
    }

    public static boolean isCompensating() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return false;
        }
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        return networkHandler != null && !client.isInSingleplayer();
    }
}