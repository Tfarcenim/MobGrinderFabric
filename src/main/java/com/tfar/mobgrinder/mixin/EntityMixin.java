package com.tfar.mobgrinder.mixin;

import com.tfar.mobgrinder.EntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Entity.class)
abstract class EntityMixin implements EntityInterface {

	@Unique
	public boolean captureSpecialItems = false;
	@Unique
	public List<ItemStack> capturedDrops = null;

	public List<ItemStack> captureDrops(DamageSource source, int lootingMultiplier, boolean allowDrops){
		captureSpecialItems = true;
		capturedDrops = new ArrayList<>();
		//noinspection ConstantConditions
		if ((Object)this instanceof LivingEntity){
			((LivingEntityAccessor)this)._dropLoot(source,allowDrops);
			((LivingEntityAccessor)this)._dropEquipment(source,lootingMultiplier,allowDrops);
		}
		List<ItemStack> drops = capturedDrops;
		captureSpecialItems = false;
		capturedDrops = null;
		return drops;
	}

	@Inject(method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;",
					at = @At("HEAD"),
					cancellable = true)
	private void onDropStack(ItemStack stack, float offset, CallbackInfoReturnable<ItemEntity> info) {
		if (this.captureSpecialItems && !stack.isEmpty() && !((Entity)(Object)this).world.isClient) {
			this.capturedDrops.add(stack);
			info.setReturnValue(null);
		}
	}
}
