package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class MixinBlock {
    @Shadow public static native Block getBlockFromItem(Item itemIn);

    // https://bugs.mojang.com/browse/MC/issues/MC-62975
    @Redirect(method = "getPickBlock(Lnet/minecraft/util/MovingObjectPosition;Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)Lnet/minecraft/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getBlockFromItem(Lnet/minecraft/item/Item;)Lnet/minecraft/block/Block;"))
    public Block fixMC62975(Item item) {
        return item instanceof ItemBanner ? (Block) (Object) this : getBlockFromItem(item);
    }

    // forge allows placing cactus on terracotta and dirt, which is not vanilla
    @Inject(method = "canSustainPlant", at = @At("RETURN"), cancellable = true, remap = false)
    private void fixCactusPlanting(IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable, CallbackInfoReturnable<Boolean> cir) {
        Block plant = plantable.getPlant(world, pos.offset(direction)).getBlock();
        if (plant == Blocks.cactus) {
            Block block = (Block) (Object) this;
            cir.setReturnValue(block == Blocks.cactus || block == Blocks.sand);
        }
    }

    // forge allows placing mushrooms on the tops of slabs, which is not vanilla
    // we can just return false here because it does the correct check before this
    @Redirect(method = "canSustainPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;isSideSolid(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"), remap = false)
    private boolean fixMushroomPlanting(Block instance, IBlockAccess shape, BlockPos facing, EnumFacing enumFacing) {
        return false;
    }
}
