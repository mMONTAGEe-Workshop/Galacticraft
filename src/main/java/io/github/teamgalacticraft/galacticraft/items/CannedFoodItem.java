package io.github.teamgalacticraft.galacticraft.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

/**
 * @author <a href="https://github.com/teamgalacticraft">TeamGalacticraft</a>
 */
public class CannedFoodItem extends Item {

    CannedFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack onItemFinishedUsing(ItemStack stack, World world, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.eatFood(world, stack);
            player.dropStack(new ItemStack(GalacticraftItems.CANISTER_TIN));
        }
        stack.subtractAmount(1);
        return stack;
    }

}
