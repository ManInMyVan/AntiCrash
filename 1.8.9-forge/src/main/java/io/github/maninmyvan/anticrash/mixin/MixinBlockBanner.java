package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBanner;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBanner.class)
public class MixinBlockBanner extends Block {
    public MixinBlockBanner(Material material) {
        super(material);
    }

    // https://bugs.mojang.com/browse/MC/issues/MC-62975
    @Override
    public int getDamageValue(World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity instanceof TileEntityBanner ? ((TileEntityBanner) tileEntity).getBaseColor() : super.getDamageValue(world, pos);
    }
}
