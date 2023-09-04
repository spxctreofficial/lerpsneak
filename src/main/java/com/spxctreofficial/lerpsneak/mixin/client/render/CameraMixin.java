package com.spxctreofficial.lerpsneak.mixin.client.render;

import com.spxctreofficial.lerpsneak.interfaces.LerpSneakCameraEntity;
import com.spxctreofficial.lerpsneak.interfaces.LerpSneakGameRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Unique
    private static PlayerEntity playerEntity;
    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;<init>(DDD)V", ordinal = 0), index = 1)
    private static double lerpsneak$modifySetPosY(double y) {
        return MathHelper.clampedLerp(playerEntity.prevY, playerEntity.y + MathHelper.clampedLerp(((LerpSneakCameraEntity) playerEntity).lerpsneak$getLastCameraY(), ((LerpSneakCameraEntity) playerEntity).lerpsneak$getCameraY(), ((LerpSneakGameRenderer) MinecraftClient.getInstance().gameRenderer).lerpsneak$getTickDelta()), ((LerpSneakGameRenderer) MinecraftClient.getInstance().gameRenderer).lerpsneak$getTickDelta());
    }

    @Inject(method = "update", at = @At("HEAD"))
    private static void getPlayer(PlayerEntity player, boolean thirdPerson, CallbackInfo ci) {
        playerEntity = player;
    }
}
