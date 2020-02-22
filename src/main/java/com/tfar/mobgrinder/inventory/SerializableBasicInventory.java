package com.tfar.mobgrinder.inventory;

import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class SerializableBasicInventory extends BasicInventory implements Serializable<CompoundTag> {

	public SerializableBasicInventory(int slots) {
		super(slots);
	}

	@Override
	public CompoundTag serialize() {
		ListTag listTag = new ListTag();
		for (int i = 0; i < getInvSize(); i++) {
			if (!getInvStack(i).isEmpty()) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putInt("Slot", i);
				getInvStack(i).toTag(itemTag);
				listTag.add(itemTag);
			}
		}
		CompoundTag nbt = new CompoundTag();
		nbt.put("Items", listTag);
		return nbt;
	}

	@Override
	public void deserialize(CompoundTag tag) {
		ListTag listTag = tag.getList("Items", 10);
		for (int i = 0; i < listTag.size(); i++) {
			CompoundTag itemTags = listTag.getCompound(i);
			int slot = itemTags.getInt("Slot");
			if (slot >= 0 && slot < getInvSize()) {
				setInvStack(slot, ItemStack.fromTag(itemTags));
			}
		}
	}
}
