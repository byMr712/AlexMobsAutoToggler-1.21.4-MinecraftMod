package com.mr712.vanillarecipeisolator.mixin;

import com.mr712.vanillarecipeisolator.registry.VanillaRegistrySnapshot;
import com.mr712.vanillarecipeisolator.state.IsolatorState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.IdList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IdList.class)
public abstract class IdListMixin<T> {

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void vanillaRecipeIsolator$redirectGet(int index, CallbackInfoReturnable<T> cir) {
        if (IsolatorState.isIsolating()) {
            if ((Object) this == Block.STATE_IDS) {
                BlockState state = VanillaRegistrySnapshot.getVanillaBlockState(index);
                if (state != null) {
                    cir.setReturnValue((T) state);
                }
            }
        }
    }

    @Inject(method = "getRawId", at = @At("HEAD"), cancellable = true)
    private void vanillaRecipeIsolator$redirectGetRawId(T value, CallbackInfoReturnable<Integer> cir) {
        if (IsolatorState.isIsolating()) {
            if ((Object) this == Block.STATE_IDS && value instanceof BlockState bs) {
                int id = VanillaRegistrySnapshot.getVanillaBlockStateRawId(bs);
                if (id != -1) {
                    cir.setReturnValue(id);
                }
            }
        }
    }
}
