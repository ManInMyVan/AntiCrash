package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockRedstoneOre.class)
public class MixinBlockRedstoneOre extends Block {
    public MixinBlockRedstoneOre(Material material) {
        super(material);
    }

    // https://bugs.mojang.com/browse/MC/issues/MC-36933
    @Override
    public Item getItem(World worldIn, BlockPos pos) {
        return Item.getItemFromBlock(Blocks.redstone_ore);
    }
}
