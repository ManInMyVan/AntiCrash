package io.github.maninmyvan.anticrash.mixin;

import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameProfile.class)
public class MixinGameProfile {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/apache/commons/lang3/StringUtils;isBlank(Ljava/lang/CharSequence;)Z", ordinal = 0))
    private boolean fixCrash(CharSequence strLen) {
        return false;
    }
}
