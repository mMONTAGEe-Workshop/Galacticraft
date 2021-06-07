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

package dev.galacticraft.mod.client.gui.screen.ingame;

import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import dev.galacticraft.mod.Constant;
import dev.galacticraft.mod.api.client.screen.MachineHandledScreen;
import dev.galacticraft.mod.api.machine.MachineStatus;
import dev.galacticraft.mod.block.entity.FuelLoaderBlockEntity;
import dev.galacticraft.mod.block.special.rocketlaunchpad.RocketLaunchPadBlockEntity;
import dev.galacticraft.mod.client.gui.widget.machine.CapacitorWidget;
import dev.galacticraft.mod.entity.RocketEntity;
import dev.galacticraft.mod.screen.FuelLoaderScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
@Environment(EnvType.CLIENT)
public class FuelLoaderScreen extends MachineHandledScreen<FuelLoaderScreenHandler> {
    public static final int X_X = 176;
    public static final int X_Y = 40;
    public static final int X_WIDTH = 11;
    public static final int X_HEIGHT = 11;

    public static final int ROCKET_FACE_X = 176;
    public static final int ROCKET_FACE_Y = 53;
    public static final int ROCKET_FACE_WIDTH = 14;
    public static final int ROCKET_FACE_HEIGHT = 35;

    public static final int TANK_OVERLAY_X = 176;
    public static final int TANK_OVERLAY_Y = 0;
    public static final int TANK_OVERLAY_WIDTH = 38;
    public static final int TANK_OVERLAY_HEIGHT = 38;

    private final FuelLoaderBlockEntity fuelLoader;

    public FuelLoaderScreen(FuelLoaderScreenHandler handler, PlayerInventory inv, Text title) {
        super(handler, inv, inv.player.world, handler.machine.getPos(), title, Constant.ScreenTexture.FUEL_LOADER_SCREEN);
        this.fuelLoader = handler.machine;
        this.addWidget(new CapacitorWidget(handler.machine.capacitor(), 10, 9, 48, this::getEnergyTooltipLines, handler.machine::getStatus));
    }

    @Override
    protected void renderBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        super.renderBackground(matrices, delta, mouseX, mouseY);

        if (this.fuelLoader.fluidInv().getInvFluid(0).getRawFluid() != null
                && this.fuelLoader.fluidInv().getInvFluid(0).getRawFluid() != Fluids.EMPTY) {
            matrices.push();
            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(this.fuelLoader.fluidInv().getInvFluid(0).getRawFluid()).getFluidSprites(null, null, this.fuelLoader.fluidInv().getInvFluid(0).getRawFluid().getDefaultState())[0];
            this.client.getTextureManager().bindTexture(sprite.getAtlas().getId());
            drawSprite(matrices, x + 106, y + 46, getZOffset(), -TANK_OVERLAY_WIDTH, (int)-(((double)TANK_OVERLAY_HEIGHT) * (fuelLoader.fluidInv().getInvFluid(0).getAmount_F().asInexactDouble() / fuelLoader.fluidInv().getMaxAmount_F(0).asInexactDouble())), sprite);
            matrices.pop();
            this.client.getTextureManager().bindTexture(Constant.ScreenTexture.FUEL_LOADER_SCREEN);
            drawTexture(matrices, x + 68, y + 8, TANK_OVERLAY_X, TANK_OVERLAY_Y, TANK_OVERLAY_WIDTH, TANK_OVERLAY_HEIGHT);
        }

        if (fuelLoader.getStatus().getType() == MachineStatus.StatusType.MISSING_RESOURCE) {
            this.client.getTextureManager().bindTexture(Constant.ScreenTexture.FUEL_LOADER_SCREEN);
            drawTexture(matrices, x + 116, y + 45, X_X, X_Y, X_WIDTH, X_HEIGHT);
        }

        if (check(mouseX, mouseY, x + 145, y + 29, ROCKET_FACE_WIDTH, ROCKET_FACE_HEIGHT)) {
            if (fuelLoader.getConnectionPos() != null) {
                BlockEntity be = world.getBlockEntity(fuelLoader.getConnectionPos());
                if (be instanceof RocketLaunchPadBlockEntity) {
                    if (((RocketLaunchPadBlockEntity) be).hasRocket()) {
                        Entity entity = world.getEntityById(((RocketLaunchPadBlockEntity) be).getRocketEntityId());
                        if (entity instanceof RocketEntity) {
                            if (!((RocketEntity) entity).getTank().getInvFluid(0).isEmpty()) {
                                double amount = ((RocketEntity) entity).getTank().getInvFluid(0).getAmount_F().asInexactDouble();
                                double max = ((RocketEntity) entity).getTank().getMaxAmount_F(0).asInexactDouble();
                                matrices.push();
                                Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(((RocketEntity) entity).getTank().getInvFluid(0).getRawFluid()).getFluidSprites(null, null, ((RocketEntity) entity).getTank().getInvFluid(0).getRawFluid().getDefaultState())[0];
                                this.client.getTextureManager().bindTexture(sprite.getAtlas().getId());
                                DrawableHelper.drawSprite(matrices, x + 159, y + 64, getZOffset(), -ROCKET_FACE_WIDTH, (int) -(((double) ROCKET_FACE_HEIGHT) * (amount / max)), sprite);
                                matrices.pop();
                            }
                        }
                    }
                }
            }
        } else {
            this.client.getTextureManager().bindTexture(Constant.ScreenTexture.FUEL_LOADER_SCREEN);
            drawTexture(matrices, x + 145, y + 29, ROCKET_FACE_X, ROCKET_FACE_Y, ROCKET_FACE_WIDTH, ROCKET_FACE_HEIGHT);
        }
        drawCenteredText(matrices, this.client.textRenderer, I18n.translate("block.galacticraft.fuel_loader"), (this.width / 2), this.y - 18, Formatting.DARK_GRAY.getColorValue());
    }

    @Override
    public void drawMouseoverTooltip(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawMouseoverTooltip(matrices, mouseX, mouseY);
        List<OrderedText> list = new ArrayList<>();
        if (check(mouseX, mouseY, x + 69, y + 9, TANK_OVERLAY_WIDTH, TANK_OVERLAY_HEIGHT)) {
            if (fuelLoader.fluidInv().getInvFluid(0).isEmpty()) {
                list.add(new TranslatableText("tooltip.galacticraft.no_fluid").setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
            } else {
                FluidAmount fraction = fuelLoader.fluidInv().getInvFluid(0).getAmount_F().mul(FluidAmount.ONE); //every action forces simplification of the fraction
                if (fraction.denominator == 1) {
                    list.add(new TranslatableText("tooltip.galacticraft.buckets", fraction.numerator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                } else {
                    if (!Screen.hasShiftDown()) {
                        if (fraction.asInexactDouble() > 1.0D) {
                            long whole = fraction.whole;
                            int numerator = (int) (fraction.numerator % fraction.denominator);
                            list.add(new TranslatableText("tooltip.galacticraft.buckets_mixed_number", whole, numerator, fraction.denominator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                        } else {
                            list.add(new TranslatableText("tooltip.galacticraft.buckets_fraction", fraction.numerator, fraction.denominator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                        }
                    } else {
                        list.add(new TranslatableText("tooltip.galacticraft.buckets", fraction.asInexactDouble()).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                    }
                }
                if (Screen.hasControlDown()) {
                    list.add(new TranslatableText("tooltip.galacticraft.fluid").setStyle(Style.EMPTY.withColor(Formatting.GRAY)).append(new LiteralText(Registry.FLUID.getId(fuelLoader.fluidInv().getInvFluid(0).getRawFluid()).toString()).setStyle(Style.EMPTY.withColor(Formatting.AQUA))).asOrderedText());
                }
            }
            this.renderOrderedTooltip(matrices, list, mouseX, mouseY);
        }
        list.clear();

        if (check(mouseX, mouseY, x + 145, y + 29, ROCKET_FACE_WIDTH, ROCKET_FACE_HEIGHT)) {
            if (fuelLoader.getConnectionPos() != null) {
                BlockEntity be = world.getBlockEntity(fuelLoader.getConnectionPos());
                if (be instanceof RocketLaunchPadBlockEntity) {
                    if (((RocketLaunchPadBlockEntity) be).hasRocket()) {
                        Entity entity = world.getEntityById(((RocketLaunchPadBlockEntity) be).getRocketEntityId());
                        if (entity instanceof RocketEntity) {
                            if (((RocketEntity) entity).getTank().getInvFluid(0).isEmpty()) {
                                list.add(new TranslatableText("tooltip.galacticraft.no_fluid").setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                            } else {
                                FluidAmount fraction = ((RocketEntity) entity).getTank().getInvFluid(0).getAmount_F();
                                if (fraction.denominator == 1) {
                                    list.add(new TranslatableText("tooltip.galacticraft.buckets", fraction.numerator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                                } else {
                                    if (!Screen.hasShiftDown()) {
                                        if (fraction.asInexactDouble() > 1.0D) {
                                            long whole = fraction.whole;
                                            int numerator = (int) (fraction.numerator % fraction.denominator);
                                            list.add(new TranslatableText("tooltip.galacticraft.buckets_mixed_number", whole, numerator, fraction.denominator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                                        } else {
                                            list.add(new TranslatableText("tooltip.galacticraft.buckets_fraction", fraction.numerator, fraction.denominator).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                                        }
                                    } else {
                                        list.add(new TranslatableText("tooltip.galacticraft.buckets", fraction.asInexactDouble()).setStyle(Style.EMPTY.withColor(Formatting.GOLD)).asOrderedText());
                                    }
                                }
                                if (Screen.hasControlDown()) {
                                    list.add(new TranslatableText("tooltip.galacticraft.fluid").setStyle(Style.EMPTY.withColor(Formatting.GRAY)).append(new LiteralText(Registry.FLUID.getId(((RocketEntity) entity).getTank().getInvFluid(0).getRawFluid()).toString()).setStyle(Style.EMPTY.withColor(Formatting.AQUA))).asOrderedText());
                                }
                            }
                        }
                    }
                }
            }
            if (!list.isEmpty()) {
                this.renderOrderedTooltip(matrices, list, mouseX, mouseY);
            }
        }
    }
}
