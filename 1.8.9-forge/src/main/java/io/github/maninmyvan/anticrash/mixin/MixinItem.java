package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Item.class)
public class MixinItem {
    @Shadow @Final private static Map<Block, Item> BLOCK_TO_ITEM;

    // https://bugs.mojang.com/browse/MC/issues/MC-36933
    @Inject(method = "registerItems", at = @At("TAIL"))
    private static void fixMC36933(CallbackInfo ci) {
        BLOCK_TO_ITEM.put(Blocks.lit_redstone_ore, BLOCK_TO_ITEM.get(Blocks.redstone_ore));
    }
}
