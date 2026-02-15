package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.BlockFire;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFire.class)
public class MixinBlockFire {
    // https://bugs.mojang.com/browse/MC/issues/MC-44140
    @Inject(method = "init", at = @At("HEAD"))
    private static void fixMC44140(CallbackInfo ci) {
        Blocks.fire.setFireInfo(Blocks.dark_oak_stairs, 5, 20);
        Blocks.fire.setFireInfo(Blocks.acacia_stairs, 5, 20);
    }
}
