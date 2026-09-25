package com.mr712.vanillarecipeisolator.state;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public final class IsolatorState {
    private static volatile boolean forceEnabled = false;
    private static volatile boolean forceDisabled = false;

    private IsolatorState() {
    }

    public static boolean isIsolating() {
        if (forceDisabled) {
            return false;
        }
        if (forceEnabled) {
            return true;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) {
            return false;
        }
        ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
        if (networkHandler == null) {
            return false;
        }
        // In singleplayer, all mods and modded blocks remain completely functional
        if (client.isInSingleplayer()) {
            return false;
        }
        // In multiplayer, isolate vanilla registries from mod pollution
        return true;
    }

    public static boolean isCompensatingDataTracker() {
        return isIsolating();
    }

    public static void setForceEnabled(boolean enabled) {
        forceEnabled = enabled;
    }

    public static void setForceDisabled(boolean disabled) {
        forceDisabled = disabled;
    }
}
