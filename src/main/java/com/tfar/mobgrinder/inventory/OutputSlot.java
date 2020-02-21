package com.tfar.mobgrinder.inventory;

import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot {
	public OutputSlot(Inventory itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean canInsert(ItemStack stack) {
		return false;
	}
}
