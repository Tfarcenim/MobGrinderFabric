package com.tfar.mobgrinder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class MobGrinderClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenProviderRegistry.INSTANCE.registerFactory(MobGrinder.id,
						(syncId, identifier, player, buf) -> new MobGrinderScreen(
										new MobGrinderMenu(syncId, player.inventory,player.world, buf.readBlockPos()), player.inventory,
										new TranslatableText(RegistryObjects.mob_grinder.getTranslationKey())));
	}
}
