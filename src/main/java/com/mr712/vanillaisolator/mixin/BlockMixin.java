package com.mr712.vanillaisolator.mixin;

import com.mr712.vanillaisolator.registry.VanillaRegistrySnapshot;
import com.mr712.vanillaisolator.state.IsolatorState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Inject(method = "getStateFromRawId", at = @At("HEAD"), cancellable = true)
    private static void vanillaIsolator$getStateFromRawId(int id, CallbackInfoReturnable<BlockState> cir) {
        if (IsolatorState.isIsolating()) {
            BlockState state = VanillaRegistrySnapshot.getVanillaBlockState(id);
            if (state != null) {
                cir.setReturnValue(state);
            }
        }
    }

    @Inject(method = "getRawIdFromState", at = @At("HEAD"), cancellable = true)
    private static void vanillaIsolator$getRawIdFromState(BlockState state, CallbackInfoReturnable<Integer> cir) {
        if (IsolatorState.isIsolating()) {
            int id = VanillaRegistrySnapshot.getVanillaBlockStateRawId(state);
            if (id != -1) {
                cir.setReturnValue(id);
            }
        }
    }
}
