package com.tfar.mobgrinder.inventory;

public interface Serializable<T> {
	T serialize();
	void deserialize(T t);
}
