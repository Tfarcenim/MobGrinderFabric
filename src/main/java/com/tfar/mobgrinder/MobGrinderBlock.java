package com.tfar.mobgrinder;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


public class MobGrinderBlock extends Block implements BlockEntityProvider {
	public MobGrinderBlock(Settings properties) {
		super(properties);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult p_225533_6_) {
		if (!world.isClient) {
			final BlockEntity tile = world.getBlockEntity(pos);
			if (tile instanceof MobGrinderBlockEntity) {
				ContainerProviderRegistry.INSTANCE.openContainer(MobGrinder.id,player,packetByteBuf -> packetByteBuf.writeBlockPos(pos));
			}
		}
		return ActionResult.SUCCESS;
	}

	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof Inventory) {
				ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
				world.updateHorizontalAdjacent(pos, this);
			}
			super.onBlockRemoved(state, world, pos, newState, moved);
		}
	}

	@Override
	public BlockEntity createBlockEntity(BlockView view) {
		return new MobGrinderBlockEntity();
	}
}
