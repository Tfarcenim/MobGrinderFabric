package com.tfar.mobgrinder.inventory;

import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;

import java.util.stream.IntStream;

public class NoInsertInventory extends SerializableBasicInventory {

	public NoInsertInventory(int slots) {
		super(slots);
	}

	//no
	@Override
	public ItemStack add(ItemStack itemStack) {
		return itemStack;
	}

	public boolean full() {
		return IntStream.range(0,getInvSize()).mapToObj(this::getInvStack).noneMatch(ItemStack::isEmpty);
	}

	public ItemStack addItem(ItemStack stack, boolean simulate) {
		ItemStack itemStack2 = stack.copy();
		this.addToExistingSlot(itemStack2);
		if (itemStack2.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.addToNewSlot(itemStack2);
			return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
		}
	}

	private void addToNewSlot(ItemStack stack) {
		for(int i = 0; i < this.getInvSize(); ++i) {
			ItemStack itemStack = this.getInvStack(i);
			if (itemStack.isEmpty()) {
				this.setInvStack(i, stack.copy());
				stack.setCount(0);
				return;
			}
		}

	}

	private void addToExistingSlot(ItemStack stack) {
		for(int i = 0; i < this.getInvSize(); ++i) {
			ItemStack itemStack = this.getInvStack(i);
			if (ItemStack.areItemsEqualIgnoreDamage(itemStack, stack)) {
				this.transfer(stack, itemStack);
				if (stack.isEmpty()) {
					return;
				}
			}
		}

	}

	private void transfer(ItemStack source, ItemStack target) {
		int i = Math.min(this.getInvMaxStackAmount(), target.getMaxCount());
		int j = Math.min(source.getCount(), i - target.getCount());
		if (j > 0) {
			target.increment(j);
			source.decrement(j);
			this.markDirty();
		}

	}

}
