package com.tfar.mobgrinder.inventory;

import com.tfar.mobgrinder.RegistryObjects;
import com.tfar.mobgrinder.Utils;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class EntityHolderSlot extends Slot {
	public EntityHolderSlot(Inventory itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return stack.getItem() == RegistryObjects.spawner_soul;
	}
}
