package com.tfar.mobgrinder;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

public class ShardItem extends Item {
	public ShardItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		user.getStackInHand(hand).getOrCreateTag().putString("id", EntityType.getId(entity.getType()).toString());
		return true;
	}

	@Override
	public boolean hasEnchantmentGlint(ItemStack stack) {
		return super.hasEnchantmentGlint(stack)||stack.hasTag();
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		Utils.getEntityTypeFromStack(stack).ifPresent(
						entityType -> tooltip.add(new TranslatableText(entityType.getTranslationKey())));
	}
}
