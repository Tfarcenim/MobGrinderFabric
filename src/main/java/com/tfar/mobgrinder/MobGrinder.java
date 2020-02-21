package com.tfar.mobgrinder;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class MobGrinder implements ModInitializer {
	// Directly reference a log4j logger.

	public static final String MODID = "mobgrinder";
	public static final Identifier id = new Identifier(MODID,"mob_grinder");
	public static final Identifier xp = new Identifier(MODID,"drop_xp");

	private static final Identifier SPAWNER_LOOT_TABLE = new Identifier("minecraft", "blocks/spawner");

	public MobGrinder() {
	}

	@Override
	public void onInitialize() {
		AutoConfig.register(MobGrinderConfigs.ServerConfig.class, Toml4jConfigSerializer::new);
		MobGrinderConfigs.ServerConfig.register();

		Registry.register(Registry.BLOCK,new Identifier(MODID,"mob_grinder"),RegistryObjects.mob_grinder);
		Registry.register(Registry.ITEM,new Identifier(MODID,"mob_grinder"),RegistryObjects.mob_grinder_item);
		Registry.register(Registry.ITEM,new Identifier(MODID,"spawner_soul"),RegistryObjects.spawner_soul);

		ServerSidePacketRegistry.INSTANCE.register(xp, (packetContext, attachedData) -> {
			packetContext.getTaskQueue().execute(() -> {
				if(packetContext.getPlayer().container instanceof MobGrinderMenu){
					((MobGrinderMenu) packetContext.getPlayer().container).te.spawnXp();
				}
			});
		});

		/*LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
			if (SPAWNER_LOOT_TABLE.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
								.withRolls(ConstantLootTableRange.create(1))
								.withEntry(
												ItemEntry.builder(RegistryObjects.spawner_soul)
																.withFunction(ApplyBonusLootFunction.uniformBonusCount(
																				Enchantments.FORTUNE,4))
								);

				supplier.withPool(poolBuilder);
			}
		});*/

		Registry.register(Registry.BLOCK_ENTITY_TYPE,new Identifier(MODID,"mob_grinder"),RegistryObjects.mob_grinder_block_entity);
		ContainerProviderRegistry.INSTANCE.registerFactory(
						id, (syncId, id, player, buf) -> new MobGrinderMenu(syncId, player.inventory, player.world, buf.readBlockPos()));

	}
}
