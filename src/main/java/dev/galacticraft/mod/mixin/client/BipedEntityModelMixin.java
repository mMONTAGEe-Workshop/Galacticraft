/*
 * Copyright (c) 2019-2021 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.mod.mixin.client;

import dev.galacticraft.mod.entity.RocketEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
@Environment(EnvType.CLIENT)
public abstract class BipedEntityModelMixin<T extends LivingEntity> {
    @Final @Shadow public ModelPart head;

    @Final @Shadow public ModelPart leftArm;

    @Final @Shadow public ModelPart rightArm;

    @Final @Shadow public ModelPart leftLeg;

    @Final
    @Shadow
    public ModelPart rightLeg;

    @Shadow @Final public ModelPart hat;

    @Shadow @Final public ModelPart body;

    @Inject(at = @At("HEAD"), method = "setAngles")
    private void standInRocketGC(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (((BipedEntityModel<T>) (Object) this).riding) {
            if (livingEntity.getVehicle() instanceof RocketEntity) {
                ((BipedEntityModel<T>) (Object) this).riding = false;
            }
        }
    }

//    @Inject(method = "setAngles", at = @At(value = "TAIL"))
//    private void rotateToMatchRocket(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
//        if (entity.hasVehicle()) {
//            if (entity.getVehicle() instanceof RocketEntity) {
//                GlStateManager.rotatef((entity.getVehicle().getYaw() - 180.0F) * -1.0F, 0.0F, 1.0F, 0.0F); //todo: what is this mess??
//                GlStateManager.rotatef(entity.getVehicle().getPitch() * -1.0F, 1.0F, 0.0F, 0.0F); //what was i thinking?
//            }
//        }
//    }

    @Inject(method = "setAngles", at = @At(value = "RETURN"))
    private void rotateToMatchRocketHeadRender(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity.hasVehicle()) {
            if (entity.getVehicle() instanceof RocketEntity) {
                this.head.pitch = 0.0F;
                this.leftArm.pitch = 0.0F;
                this.leftArm.yaw = entity.getVehicle().getPitch() * -1.0F;
                this.rightArm.pitch = 0.0F;
                this.rightArm.yaw = entity.getVehicle().getPitch() * -1.0F;
                this.leftLeg.pitch = 0.0F;
                this.leftLeg.yaw = entity.getVehicle().getPitch() * -1.0F;
                this.rightLeg.pitch = 0.0F;
                this.rightLeg.yaw = entity.getVehicle().getPitch() * -1.0F;
                this.hat.pitch = 0.0F;
                this.hat.yaw = entity.getVehicle().getPitch() * -1.0F;
                this.body.pitch = 0.0F;
                this.body.yaw = entity.getVehicle().getPitch() * -1.0F;
            }
        }
    }
}
