package com.spxctreofficial.lerpsneak.mixin.entity;

import com.spxctreofficial.lerpsneak.interfaces.LerpSneakCameraEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin implements LerpSneakCameraEntity {
    @Unique
    private float lastCameraY;
    @Unique
    private float cameraY;

    @Override
    public float lerpsneak$getLastCameraY() {
        return lastCameraY;
    }
    @Override
    public float lerpsneak$getCameraY() {
        return cameraY;
    }
    @Override
    public void lerpsneak$updateCameraHeight(double tickDelta) {
        this.lastCameraY = this.lerpsneak$getCameraY();
        cameraY = (float) MathHelper.clampedLerp(this.lerpsneak$getLastCameraY(), ((PlayerEntity) (Object) this).getEyeHeight(), tickDelta / 2);
    }
}
