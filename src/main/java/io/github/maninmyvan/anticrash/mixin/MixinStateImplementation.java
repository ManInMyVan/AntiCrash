package io.github.maninmyvan.anticrash.mixin;

import com.google.common.collect.ImmutableMap;
import io.github.maninmyvan.anticrash.AntiCrashMod;
import net.minecraft.block.Block;
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
    @Shadow @Final private Block block;
    @Shadow public native <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value);

    @SuppressWarnings("unchecked")
    @Inject(method = "withProperty", at = @At(value = "HEAD"), cancellable = true)
    private <T extends Comparable<T>, V extends T> void invalidPropertyPatch(IProperty<T> property, V value, CallbackInfoReturnable<IBlockState> cir) {
        if (!this.properties.containsKey(property)) {
            AntiCrashMod.stacktrace(new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState()));
            cir.setReturnValue((StateImplementation) (Object) this);
            return;
        }

        if (!property.getAllowedValues().contains(value) && !property.getAllowedValues().isEmpty()) {
            AntiCrashMod.stacktrace(new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value"));
            cir.setReturnValue(withProperty(property, (V) property.getAllowedValues().iterator().next()));
        }
    }
}
