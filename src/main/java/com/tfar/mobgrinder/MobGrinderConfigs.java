package com.tfar.mobgrinder;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

public class MobGrinderConfigs {
	@Config(name = MobGrinder.MODID)
	public static class ServerConfig implements ConfigData {
		public static ServerConfig config;

		public int timeBetweenKills = 100;
		public int damagePerKill = 1;

		public static void register() {
			config = AutoConfig.getConfigHolder(ServerConfig.class).getConfig();
		}
	}
}
