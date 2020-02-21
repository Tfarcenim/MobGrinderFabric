package com.tfar.mobgrinder;

import com.tfar.mobgrinder.inventory.EntityHolderSlot;
import com.tfar.mobgrinder.inventory.OutputSlot;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MobGrinderMenu extends Container {

	public MobGrinderBlockEntity te;

	public MobGrinderMenu(int windowId, PlayerInventory inv, World world, BlockPos readBlockPos) {
		super(null, windowId);
		te = (MobGrinderBlockEntity) world.getBlockEntity(readBlockPos);
		addSlot(new EntityHolderSlot(te.handler, 0, 44, 17));
		addSlot(new Slot(te.handler, 1, 44, 53));

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				int index = x + 3 * y;
				addSlot(new OutputSlot(te.storage, index, 97 + 18 * x, 17 + 18 * y));
			}
		}
		addPlayerSlots(inv);
	}

	protected void addPlayerSlots(PlayerInventory playerinventory) {
		int yStart = 86;
		for (int row = 0; row < 3; ++row) {
			for (int col = 0; col < 9; ++col) {
				int x = 8 + col * 18;
				int y = row * 18 + yStart;
				this.addSlot(new Slot(playerinventory, col + row * 9 + 9, x, y));
			}
		}

		for (int row = 0; row < 9; ++row) {
			int x = 8 + row * 18;
			int y = yStart + 58;
			this.addSlot(new Slot(playerinventory, row, x, y));
		}
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@Override
	public ItemStack transferSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slotList.get(index);
		if (slot != null && slot.hasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 11) {
				if (!this.insertItem(itemstack1, 11, 47, true)) {
					return ItemStack.EMPTY;
				}
			} else if (index < 47) {
				if (!this.insertItem(itemstack1, 0, 2, false)) {
					return ItemStack.EMPTY;
				}
			}
			if (itemstack1.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return itemstack;
	}

}
