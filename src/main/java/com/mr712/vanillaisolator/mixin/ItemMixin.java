package com.mr712.vanillaisolator.mixin;

import com.mr712.vanillaisolator.registry.VanillaRegistrySnapshot;
import com.mr712.vanillaisolator.state.IsolatorState;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Inject(method = "byRawId", at = @At("HEAD"), cancellable = true)
    private static void vanillaIsolator$byRawId(int id, CallbackInfoReturnable<Item> cir) {
        if (IsolatorState.isIsolating()) {
            Item item = VanillaRegistrySnapshot.getVanillaItem(id);
            if (item != null) {
                cir.setReturnValue(item);
            }
        }
    }

    @Inject(method = "getRawId", at = @At("HEAD"), cancellable = true)
    private static void vanillaIsolator$getRawId(Item item, CallbackInfoReturnable<Integer> cir) {
        if (IsolatorState.isIsolating()) {
            int id = VanillaRegistrySnapshot.getVanillaItemRawId(item);
            if (id != -1) {
                cir.setReturnValue(id);
            }
        }
    }
}
