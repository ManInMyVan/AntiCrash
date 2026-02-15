package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow public static native Block getBlockFromItem(Item itemIn);

    // https://bugs.mojang.com/browse/MC/issues/MC-62975
    @Redirect(method = "getPickBlock(Lnet/minecraft/util/MovingObjectPosition;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getBlockFromItem(Lnet/minecraft/item/Item;)Lnet/minecraft/block/Block;"))
    public Block fixMC62975(Item item) {
        return item instanceof ItemBanner ? (Block) (Object) this : getBlockFromItem(item);
    }
}
