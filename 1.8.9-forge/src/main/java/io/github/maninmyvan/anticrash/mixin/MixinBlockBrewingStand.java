package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.BlockBrewingStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBrewingStand.class)
public abstract class MixinBlockBrewingStand {
    @Shadow
    public abstract void setBlockBoundsForItemRender();

    // https://bugs.mojang.com/browse/MC-85109
    @Inject(method = "<init>", at = @At("TAIL"))
    private void fixMC85109(CallbackInfo ci) {
        this.setBlockBoundsForItemRender();
    }
}
