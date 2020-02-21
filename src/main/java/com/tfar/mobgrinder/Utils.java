package com.tfar.mobgrinder;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

public class Utils {

	public static final Tag<Item> entity_holders = new ItemTags.CachingTag(new Identifier(MobGrinder.MODID,"entity_holders"));
	public static final String KEY = "entity_holder";
	//helper methods

	public static boolean containsEntity(ItemStack stack) {
		return stack.hasTag() && stack.getTag().contains(KEY);
	}

	public static Optional<EntityType<?>> getEntityTypeFromNBT(CompoundTag nbt) {
		return Registry.ENTITY_TYPE.getOrEmpty(new Identifier(nbt.getString("id")));
	}

	public static Optional<EntityType<?>> getEntityTypeFromStack(ItemStack stack) {
			return Registry.ENTITY_TYPE.getOrEmpty(
							new Identifier(stack.getOrCreateTag().getString("id")));
	}
}
