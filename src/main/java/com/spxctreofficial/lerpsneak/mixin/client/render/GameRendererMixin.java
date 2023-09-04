package com.spxctreofficial.lerpsneak.mixin.client.render;

import com.spxctreofficial.lerpsneak.interfaces.LerpSneakCameraEntity;
import com.spxctreofficial.lerpsneak.interfaces.LerpSneakGameRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements LerpSneakGameRenderer {
    @Unique
    private float tickDelta;

    @Shadow private MinecraftClient client;
    @Inject(method = "transformCamera", at = @At(value = "HEAD"))
    private void getTickDelta(float tickDelta, CallbackInfo ci) {
        this.tickDelta = tickDelta;
    }

    @Redirect(method = "transformCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getEyeHeight()F"))
    private float lerpSneak(Entity entity) {
        return (float) MathHelper.clampedLerp(((LerpSneakCameraEntity) entity).lerpsneak$getLastCameraY(), ((LerpSneakCameraEntity) entity).lerpsneak$getCameraY(), tickDelta);
    }

//    @ModifyVariable(method = "transformCamera", at = @At("STORE"), ordinal = 0)
//    private float test() {
//        return 2.5f;
//    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientWorld;getBrightness(Lnet/minecraft/util/math/BlockPos;)F"))
    private void updateCameraHeight(CallbackInfo ci) {
        if (this.client.getCameraEntity() != null) {
            ((LerpSneakCameraEntity) this.client.getCameraEntity()).lerpsneak$updateCameraHeight(tickDelta);
        }
    }

    @Override
    public float lerpsneak$getTickDelta() {
        return tickDelta;
    }
}
