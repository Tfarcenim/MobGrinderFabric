package com.tfar.mobgrinder;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface EntityInterface {
	List<ItemStack> captureDrops(DamageSource source, int lootingMultiplier, boolean allowDrops);
}
