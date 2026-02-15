package io.github.maninmyvan.anticrash.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState.StateImplementation;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StateImplementation.class)
public class MixinStateImplementation {
    @Shadow @Final private ImmutableMap<IProperty<?>, Comparable<?>> properties;
    @Shadow public native <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value);

    // https://bugs.mojang.com/browse/MC/issues/MC-82677
    @Inject(method = "withProperty", at = @At(value = "HEAD"), cancellable = true)
    private <T extends Comparable<T>, V extends T> void fixMC82677(IProperty<T> property, V value, CallbackInfoReturnable<IBlockState> cir) {
        if (!this.properties.containsKey(property)) {
            cir.setReturnValue((StateImplementation) (Object) this);
            return;
        }

        if (!property.getAllowedValues().contains(value) && !property.getAllowedValues().isEmpty()) {
            cir.setReturnValue(withProperty(property, property.getAllowedValues().iterator().next()));
        }
    }
}
