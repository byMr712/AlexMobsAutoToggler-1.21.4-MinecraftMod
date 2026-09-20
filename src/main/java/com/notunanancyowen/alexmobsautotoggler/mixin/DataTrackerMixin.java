package com.notunanancyowen.alexmobsautotoggler.mixin;

import java.util.List;
import java.util.Objects;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.notunanancyowen.alexmobsautotoggler.AlexMobsAutoToggler;
import com.notunanancyowen.alexmobsautotoggler.TogglerState;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;

@Mixin(DataTracker.class)
public abstract class DataTrackerMixin {
    @Shadow
    private DataTracked trackedEntity;

    @Shadow
    private DataTracker.Entry<?>[] entries;

    @Redirect(
        method = "writeUpdatedEntries",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/entity/data/DataTracker$SerializedEntry;id:I",
            opcode = Opcodes.GETFIELD
        )
    )
    private int alexmobsautotoggler$redirectId(DataTracker.SerializedEntry<?> entry, List<?> unusedEntries) {
        int originalId = entry.id();
        if (!TogglerState.isCompensating() || !(this.trackedEntity instanceof LivingEntity)) {
            return originalId;
        }
        if (entryIdMatches(originalId, entry)) {
            return originalId;
        }
        if (entryIdMatches(originalId + 1, entry)) {
            AlexMobsAutoToggler.LOGGER.info(
                "[AlexMobsAutoToggler] Remapping entity data field {} to {} for {}",
                originalId, originalId + 1, this.trackedEntity
            );
            return originalId + 1;
        }
        return originalId;
    }

    @Inject(method = "copyToFrom", at = @At("HEAD"), cancellable = true)
    private void alexmobsautotoggler$guardCopy(
        DataTracker.Entry<?> to, DataTracker.SerializedEntry<?> from, CallbackInfo ci
    ) {
        if (!TogglerState.isCompensating() || !(this.trackedEntity instanceof LivingEntity)) {
            return;
        }
        if (!sameHandler(to.getData(), from.handler())) {
            AlexMobsAutoToggler.LOGGER.warn(
                "[AlexMobsAutoToggler] Suppressed incompatible entity data update for {}: {}",
                this.trackedEntity, from
            );
            ci.cancel();
        }
    }

    private boolean entryIdMatches(int id, DataTracker.SerializedEntry<?> entry) {
        if (id < 0 || id >= this.entries.length) {
            return false;
        }
        return sameHandler(this.entries[id].getData(), entry.handler());
    }

    private static boolean sameHandler(TrackedData data, TrackedDataHandler<?> handler) {
        return Objects.equals(data.dataType(), handler);
    }
}