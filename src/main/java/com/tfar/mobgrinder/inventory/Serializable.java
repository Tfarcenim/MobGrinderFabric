package com.tfar.mobgrinder.inventory;

import net.minecraft.nbt.CompoundTag;

public interface Serializable<T> {
	T serialize();
	void deserialize(CompoundTag tag);
}
