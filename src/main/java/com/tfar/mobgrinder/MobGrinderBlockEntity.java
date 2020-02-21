package com.tfar.mobgrinder;

import com.mojang.authlib.GameProfile;
import com.tfar.mobgrinder.MobGrinderConfigs.ServerConfig;
import com.tfar.mobgrinder.fakeplayer.FakePlayer;
import com.tfar.mobgrinder.fakeplayer.FakePlayerFactory;
import com.tfar.mobgrinder.inventory.NoInsertInventory;
import com.tfar.mobgrinder.inventory.SerializableBasicInventory;
import com.tfar.mobgrinder.mixin.LivingEntityAccessor;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Tickable;

import java.util.List;
import java.util.UUID;

public class MobGrinderBlockEntity extends BlockEntity implements NameableContainerProvider, Tickable, BlockEntityClientSerializable, Inventory {

	private static GameProfile PROFILE = new GameProfile(UUID.fromString("a42ac406-c797-4e0e-b147-f01ac5551be6"), "[MobGrinder]");

	public SerializableBasicInventory handler = new SerializableBasicInventory(2);

	public NoInsertInventory storage = new NoInsertInventory(9);

	public int xp = 0;

	public MobGrinderBlockEntity() {
		super(RegistryObjects.mob_grinder_block_entity);
	}

	@Override
	public Text getDisplayName() {
		return new LiteralText("grinder");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return new MobGrinderMenu(p_createMenu_1_, p_createMenu_2_, world, pos);
	}

	@Override
	public void tick() {
		if (!world.isClient) {
			if (world.getLevelProperties().getTime() % ServerConfig.config.timeBetweenKills != 0) return;
			ItemStack holder = handler.getInvStack(0);
			ItemStack sword = handler.getInvStack(1);
			if (!holder.isEmpty()) {
				if (holder.hasTag() && !storage.full()) {
					Utils.getEntityTypeFromStack(holder).ifPresent(entityType -> {
						LivingEntity entity = (LivingEntity) entityType.create(world);
						if (entity instanceof MobEntity) {
							((MobEntity) entity).initialize(world, world.getLocalDifficulty(pos)
											, SpawnType.NATURAL, null, null);
						}
						FakePlayer fakePlayer = FakePlayerFactory.get((ServerWorld) world, PROFILE);
						fakePlayer.setStackInHand(Hand.MAIN_HAND, sword);
						int looting = EnchantmentHelper.getLooting(fakePlayer);
						DamageSource source = DamageSource.player(fakePlayer);
						((LivingEntityInterface) entity).setAttackingPlayer(fakePlayer);

						List<ItemStack> drops = ((EntityInterface) entity).captureDrops(source, looting, true);
						int xp = ((LivingEntityAccessor) entity)._getCurrentExperience(fakePlayer);
						if (ServerConfig.config.damagePerKill > 0) sword.damage(1, fakePlayer, p -> {});
						if (EnchantmentHelper.getLevel(Enchantments.MENDING, sword) > 0) {
							int max = sword.getDamage();
							int repair = Math.min(max, xp);
							sword.setDamage(sword.getDamage() - repair);
							xp -= repair;
						}
						this.xp += xp;
						sync();
						drops.forEach(stack -> storage.addItem(stack, false));
					});
				}
			}
		}
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		CompoundTag inv = handler.serialize();
		tag.put("inv", inv);
		CompoundTag storage = this.storage.serialize();
		tag.put("storage", storage);
		tag.putInt("xp", xp);
		//if (this.customName != null) {
		//	tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
		//}

		return super.toTag(tag);
	}

	@Override
	public void fromTag(CompoundTag tag) {
		CompoundTag invTag = tag.getCompound("inv");
		handler.deserialize(invTag);
		CompoundTag storage = tag.getCompound("storage");
		this.storage.deserialize(storage);
		this.xp = tag.getInt("xp");
		//if (tag.contains("CustomName", 8)) {
		//	this.customName = ITextComponent.Serializer.fromJson(tag.getString("CustomName"));
		//}

		super.fromTag(tag);
	}

	@Override
	public void fromClientTag(CompoundTag compoundTag) {
		this.xp = compoundTag.getInt("xp");
	}

	@Override
	public CompoundTag toClientTag(CompoundTag compoundTag) {
		compoundTag.putInt("xp", xp);
		return compoundTag;
	}

	public void spawnXp() {
		int xpToDrop = Math.min(xp, 32767);
		ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), xpToDrop);
		world.spawnEntity(experienceOrbEntity);
		xp -= xpToDrop;
		sync();
	}

	@Override
	public int getInvSize() {
		return storage.getInvSize();
	}

	@Override
	public boolean isInvEmpty() {
		return storage.isInvEmpty();
	}

	@Override
	public ItemStack getInvStack(int slot) {
		return storage.getInvStack(slot);
	}

	@Override
	public ItemStack takeInvStack(int slot, int amount) {
		return storage.takeInvStack(slot, amount);
	}

	@Override
	public ItemStack removeInvStack(int slot) {
		return storage.removeInvStack(slot);
	}

	@Override
	public void setInvStack(int slot, ItemStack stack) {
		storage.setInvStack(slot, stack);
	}

	@Override
	public boolean canPlayerUseInv(PlayerEntity player) {
		return storage.canPlayerUseInv(player);
	}

	@Override
	public void clear() {
		storage.clear();
	}
}
