/*
 * Copyright (c) 2019-2024 Team Galacticraft
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

package dev.galacticraft.mod.data;

import dev.galacticraft.mod.Constant;
import dev.galacticraft.mod.content.item.GCItems;
import dev.galacticraft.mod.data.recipes.CircuitFabricatorRecipeBuilder;
import dev.galacticraft.mod.data.recipes.ShapedCompressorRecipeBuilder;
import dev.galacticraft.mod.data.recipes.ShapelessCompressorRecipeBuilder;
import dev.galacticraft.mod.tag.GCTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GCRecipeProvider extends FabricRecipeProvider {

    public GCRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, GCItems.ROCKET_LAUNCH_PAD, 9)
                .define('C', GCItems.COMPRESSED_IRON)
                .define('I', Items.IRON_BLOCK)
                .pattern("CCC")
                .pattern("III")
                .unlockedBy(getHasName(Items.IRON_BLOCK), has(Items.IRON_BLOCK))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.OXYGEN_FAN)
                .define('S', GCItems.COMPRESSED_STEEL)
                .define('W', GCItems.BASIC_WAFER)
                .define('R', ConventionalItemTags.REDSTONE_DUSTS)
                .pattern("S S")
                .pattern(" W ")
                .pattern("SRS")
                .unlockedBy(getHasName(GCItems.BASIC_WAFER), has(GCItems.BASIC_WAFER))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.OXYGEN_VENT)
                .define('T', GCItems.COMPRESSED_TIN)
                .define('S', GCItems.COMPRESSED_STEEL)
                .pattern("TT")
                .pattern("TS")
                .unlockedBy(getHasName(GCItems.COMPRESSED_TIN), has(GCItems.COMPRESSED_TIN))
                .save(exporter);

        // Compressing
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_ALUMINUM)
                .requires(GCTags.ALUMINUM_INGOTS)
                .requires(GCTags.ALUMINUM_INGOTS)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_BRONZE)
                .requires(GCItems.COMPRESSED_COPPER)
                .requires(GCItems.COMPRESSED_TIN)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_COPPER)
                .requires(ConventionalItemTags.COPPER_INGOTS)
                .requires(ConventionalItemTags.COPPER_INGOTS)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_DESH)
                .requires(GCItems.DESH_INGOT)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_IRON)
                .requires(ConventionalItemTags.IRON_INGOTS)
                .requires(ConventionalItemTags.IRON_INGOTS)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_METEORIC_IRON)
                .requires(GCItems.METEORIC_IRON_INGOT)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_STEEL)
                .requires(ConventionalItemTags.COAL)
                .requires(GCItems.COMPRESSED_IRON)
                .requires(ConventionalItemTags.COAL)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_STEEL)
                .requires(GCTags.STEEL_INGOTS)
                .requires(GCTags.STEEL_INGOTS)
                .save(exporter, Constant.id("compressed_steel_from_ingots"));
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_TIN)
                .requires(GCTags.TIN_INGOTS)
                .requires(GCTags.TIN_INGOTS)
                .save(exporter);
        ShapelessCompressorRecipeBuilder.shapeless(GCItems.COMPRESSED_TITANIUM)
                .requires(GCItems.TITANIUM_INGOT)
                .requires(GCItems.TITANIUM_INGOT)
                .save(exporter);

        ShapedCompressorRecipeBuilder.create(GCItems.TIER_1_HEAVY_DUTY_PLATE, 2)
                .define('B', GCItems.COMPRESSED_BRONZE)
                .define('A', GCItems.COMPRESSED_ALUMINUM)
                .define('S', GCItems.COMPRESSED_STEEL)
                .pattern("BAS")
                .pattern("BAS")
                .save(exporter);

        // Machines
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.CIRCUIT_FABRICATOR)
                .define('A', GCItems.ALUMINUM_INGOT)
                .define('L', Items.LEVER)
                .define('B', Items.STONE_BUTTON)
                .define('F', Items.FURNACE)
                .define('W', GCItems.ALUMINUM_WIRE)
                .define('R', Items.REDSTONE_TORCH)
                .pattern("ALA")
                .pattern("BFB")
                .pattern("WRW")
                .unlockedBy(getHasName(GCItems.ALUMINUM_INGOT), has(GCItems.ALUMINUM_INGOT))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.COMPRESSOR)
                .define('I', GCItems.ALUMINUM_INGOT)
                .define('A', Items.ANVIL)
                .define('C', ConventionalItemTags.COPPER_INGOTS)
                .define('W', GCItems.BASIC_WAFER)
                .pattern("IAI")
                .pattern("ICI")
                .pattern("IWI")
                .unlockedBy(getHasName(GCItems.ALUMINUM_INGOT), has(GCItems.ALUMINUM_INGOT))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.ELECTRIC_COMPRESSOR)
                .define('S', GCItems.COMPRESSED_STEEL)
                .define('A', Items.ANVIL)
                .define('B', GCItems.COMPRESSED_BRONZE)
                .define('W', GCItems.ADVANCED_WAFER)
                .define('I', GCItems.ALUMINUM_WIRE)
                .pattern("SAS")
                .pattern("SBS")
                .pattern("IWI")
                .unlockedBy(getHasName(GCItems.ALUMINUM_INGOT), has(GCItems.ALUMINUM_INGOT))
                .save(exporter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.ELECTRIC_COMPRESSOR)
                .define('S', GCItems.COMPRESSED_STEEL)
                .define('T', GCItems.COMPRESSED_TIN)
                .define('C', GCItems.COMPRESSOR)
                .define('W', GCItems.ADVANCED_WAFER)
                .define('I', GCItems.ALUMINUM_WIRE)
                .pattern("STS")
                .pattern("SCS")
                .pattern("IWI")
                .unlockedBy(getHasName(GCItems.ALUMINUM_INGOT), has(GCItems.ALUMINUM_INGOT))
                .save(exporter, Constant.id("electric_compressor_alt"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.OXYGEN_BUBBLE_DISTRIBUTOR)
                .define('S', GCItems.COMPRESSED_STEEL)
                .define('F', GCItems.OXYGEN_FAN)
                .define('V', GCItems.OXYGEN_VENT)
                .define('A', GCItems.COMPRESSED_ALUMINUM)
                .pattern("SFS")
                .pattern("VAV")
                .pattern("SFS")
                .unlockedBy(getHasName(GCItems.COMPRESSED_STEEL), has(GCItems.COMPRESSED_STEEL))
                .save(exporter);

        // Rockets
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GCItems.ROCKET_WORKBENCH)
                .define('S', GCItems.COMPRESSED_STEEL)
                .define('C', Items.CRAFTING_TABLE)
                .define('L', Items.LEVER)
                .define('W', GCItems.ADVANCED_WAFER)
                .define('R', Items.REDSTONE_TORCH)
                .pattern("SCS")
                .pattern("LWL")
                .pattern("SRS")
                .unlockedBy(getHasName(GCItems.ADVANCED_WAFER), has(GCItems.ADVANCED_WAFER))
                .save(exporter);

        // Fabricator
        CircuitFabricatorRecipeBuilder.create(GCItems.BASIC_WAFER, 3)
                .requires(Items.REDSTONE_TORCH)
                .save(exporter);
        CircuitFabricatorRecipeBuilder.create(GCItems.ADVANCED_WAFER)
                .requires(Items.REPEATER)
                .save(exporter);

        CircuitFabricatorRecipeBuilder.create(GCItems.BLUE_SOLAR_WAFER, 9)
                .requires(Items.LAPIS_LAZULI)
                .save(exporter);
        CircuitFabricatorRecipeBuilder.create(GCItems.SOLAR_ARRAY_WAFER, 3)
                .requires(GCItems.SOLAR_DUST)
                .save(exporter);

        // Nuggets <-> Ingots
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.METEORIC_IRON_NUGGET, RecipeCategory.MISC, GCItems.METEORIC_IRON_INGOT, "meteoric_iron_ingot_from_nuggets", "meteoric_iron_ingot");
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.DESH_NUGGET, RecipeCategory.MISC, GCItems.DESH_INGOT, "desh_ingot_from_nuggets", "desh_ingot");
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.LEAD_NUGGET, RecipeCategory.MISC, GCItems.LEAD_INGOT, "lead_ingot_from_nuggets", "lead_ingot");
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.ALUMINUM_NUGGET, RecipeCategory.MISC, GCItems.ALUMINUM_INGOT, "aluminum_ingot_from_nuggets", "aluminum_ingot");
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.TIN_NUGGET, RecipeCategory.MISC, GCItems.TIN_INGOT, "tin_ingot_from_nuggets", "tin_ingot");
        GCRecipeProvider.nineBlockStorageRecipesWithCustomPacking(exporter, RecipeCategory.MISC, GCItems.TITANIUM_NUGGET, RecipeCategory.MISC, GCItems.TITANIUM_INGOT, "titanium_ingot_from_nuggets", "titanium_ingot");
        // Ingots <-> Blocks
        GCRecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(exporter, RecipeCategory.MISC, GCItems.METEORIC_IRON_INGOT, RecipeCategory.BUILDING_BLOCKS, GCItems.METEORIC_IRON_BLOCK, "meteoric_iron_ingot_from_block", "meteoric_iron_ingot");
        GCRecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(exporter, RecipeCategory.MISC, GCItems.DESH_INGOT, RecipeCategory.BUILDING_BLOCKS, GCItems.DESH_BLOCK, "desh_ingot_from_block", "desh_ingot");
        GCRecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(exporter, RecipeCategory.MISC, GCItems.LEAD_INGOT, RecipeCategory.BUILDING_BLOCKS, GCItems.LEAD_BLOCK, "lead_ingot_from_block", "lead_ingot");
        // skips aluminum and tin blocks
        GCRecipeProvider.nineBlockStorageRecipesRecipesWithCustomUnpacking(exporter, RecipeCategory.MISC, GCItems.TITANIUM_INGOT, RecipeCategory.BUILDING_BLOCKS, GCItems.TITANIUM_BLOCK, "titanium_ingot_from_block", "titanium_ingot");
    }

    // Code copied from RecipeProvider class with changes to save with GC mod id
    public static void nineBlockStorageRecipesWithCustomPacking(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory, ItemLike itemLike, RecipeCategory recipeCategory2, ItemLike itemLike2, String string, String string2) {
        GCRecipeProvider.nineBlockStorageRecipes(consumer, recipeCategory, itemLike, recipeCategory2, itemLike2, string, string2, RecipeProvider.getSimpleRecipeName(itemLike), null);
    }
    public static void nineBlockStorageRecipesRecipesWithCustomUnpacking(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory, ItemLike itemLike, RecipeCategory recipeCategory2, ItemLike itemLike2, String string, String string2) {
        GCRecipeProvider.nineBlockStorageRecipes(consumer, recipeCategory, itemLike, recipeCategory2, itemLike2, RecipeProvider.getSimpleRecipeName(itemLike2), null, string, string2);
    }
    public static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory, ItemLike itemLike, RecipeCategory recipeCategory2, ItemLike itemLike2, String string, @Nullable String string2, String string3, @Nullable String string4) {
        ShapelessRecipeBuilder.shapeless(recipeCategory, itemLike, 9).requires(itemLike2).group(string4).unlockedBy(RecipeProvider.getHasName(itemLike2), RecipeProvider.has(itemLike2)).save(consumer, Constant.id(string3));
        ShapedRecipeBuilder.shaped(recipeCategory2, itemLike2).define('#', itemLike).pattern("###").pattern("###").pattern("###").group(string2).unlockedBy(RecipeProvider.getHasName(itemLike), RecipeProvider.has(itemLike)).save(consumer, Constant.id(string));
    }

}