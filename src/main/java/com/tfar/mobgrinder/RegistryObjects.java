package com.tfar.mobgrinder;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class RegistryObjects {

	public static final String name = "mob_grinder";

	public static final Block mob_grinder = new MobGrinderBlock(Block.Settings.copy(Blocks.IRON_BLOCK));
	public static final Item mob_grinder_item = new BlockItem(mob_grinder,new Item.Settings().group(ItemGroup.COMBAT));
	public static final Item spawner_soul = new ShardItem(new Item.Settings().group(ItemGroup.MATERIALS));
	//public static final ContainerType<MobGrinderMenu> mob_grinder_menu = new ContainerType<>((windowId, inv) ->
//					new MobGrinderMenu(windowId, inv, inv.player.world, ));
	public static final BlockEntityType<?> mob_grinder_block_entity =
					BlockEntityType.Builder.create(MobGrinderBlockEntity::new,mob_grinder).build(null);

}
