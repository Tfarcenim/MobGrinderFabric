package com.tfar.mobgrinder.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Invoker("dropLoot")
	void _dropLoot(DamageSource source, boolean allowDrops);
	@Invoker("dropEquipment")
	void _dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops);
	@Invoker("getCurrentExperience")
	int _getCurrentExperience(PlayerEntity player);
}
