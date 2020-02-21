package com.tfar.mobgrinder.mixin;

import com.tfar.mobgrinder.LivingEntityInterface;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
class LivingEntityMixin extends EntityMixin implements LivingEntityInterface {

	@Shadow protected PlayerEntity attackingPlayer;
	@Override
	public void setAttackingPlayer(PlayerEntity player) {
		this.attackingPlayer = player;
	}
}
