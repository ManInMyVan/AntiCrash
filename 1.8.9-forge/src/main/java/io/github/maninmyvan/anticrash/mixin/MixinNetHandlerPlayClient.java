package io.github.maninmyvan.anticrash.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Shadow
    private WorldClient clientWorldController;

    @Redirect(method = "handleCollectItem", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntityByID(I)Lnet/minecraft/entity/Entity;"))
    private Entity a(WorldClient world, int id) {
        Entity entity = world.getEntityByID(id);
        return entity instanceof EntityLivingBase ? entity : null;
    }

    @Inject(
            method = "handleAnimation",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/network/play/server/S0BPacketAnimation;getAnimationType()I"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void a(S0BPacketAnimation packet, CallbackInfo ci, Entity entity) {
        if (packet.getAnimationType() == 0 && !(entity instanceof EntityLivingBase)
                || packet.getAnimationType() == 2 && !(entity instanceof EntityPlayer)) {
            ci.cancel();
        }
    }

    @Inject(method = "handleUseBed", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/server/S0APacketUseBed;getPlayer(Lnet/minecraft/world/World;)Lnet/minecraft/entity/player/EntityPlayer;"), cancellable = true)
    private void a(S0APacketUseBed packet, CallbackInfo ci) {
        try {
            if (packet.getPlayer(this.clientWorldController) == null) {
                ci.cancel();
            }
        } catch (ClassCastException e) {
            ci.cancel();
        }
    }

    @Inject(
            method = "handleEntityStatus",
            at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/network/play/server/S19PacketEntityStatus;getOpCode()B"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void a(S19PacketEntityStatus packet, CallbackInfo ci, Entity entity) {
        if (packet.getOpCode() == 21 && !(entity instanceof EntityGuardian)) {
            ci.cancel();
        }
    }

    @Redirect(method = "handleEntityEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setCurrentItemOrArmor(ILnet/minecraft/item/ItemStack;)V"))
    private void a(Entity entity, int slot, ItemStack stack) {
        try {
            entity.setCurrentItemOrArmor(slot, stack);
        } catch (Throwable ignored) {}
    }
}
