package com.tfar.mobgrinder.mixin;

import com.tfar.mobgrinder.RegistryObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
	@Inject(
					locals = LocalCapture.CAPTURE_FAILSOFT,
					method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/loot/context/LootContext$Builder;)Ljava/util/List;",
					at = @At("TAIL")

	)
	private void addSouls(BlockState state, LootContext.Builder ctxBuilder, CallbackInfoReturnable<List<ItemStack>> cir,
																	Identifier identifier,
																	LootContext lootContext,
																	ServerWorld serverWorld,
																	LootTable table){
		if (state.getBlock() instanceof SpawnerBlock){
			Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
			int looting = entity instanceof LivingEntity ?
							EnchantmentHelper.getLooting((LivingEntity)lootContext.get(LootContextParameters.THIS_ENTITY)): 0;
			cir.getReturnValue().add(new ItemStack(RegistryObjects.spawner_soul, 4 * (1+ looting)));
		}
	}
}
