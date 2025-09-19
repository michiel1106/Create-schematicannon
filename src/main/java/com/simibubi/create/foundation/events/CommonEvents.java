package com.simibubi.create.foundation.events;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.pack.DynamicPack;
import com.simibubi.create.foundation.pack.DynamicPackSource;
import com.simibubi.create.foundation.utility.ServerSpeedProvider;
import com.simibubi.create.foundation.utility.TickBasedCache;

import net.createmod.catnip.data.WorldAttached;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CommonEvents {

	@SubscribeEvent
	public static void onServerTick(ServerTickEvent event) {
		if (event.phase == Phase.START)
			return;
		Create.SCHEMATIC_RECEIVER.tick();
		ServerSpeedProvider.serverTick();

		TickBasedCache.tick();
	}

	@SubscribeEvent
	public static void onChunkUnloaded(ChunkEvent.Unload event) {
	}


	@SubscribeEvent
	public static void playerLoggedOut(PlayerLoggedOutEvent event) {
		Player player = event.getEntity();
	}




	@SubscribeEvent
	public static void serverStopping(ServerStoppingEvent event) {
		Create.SCHEMATIC_RECEIVER.shutdown();
	}


	@SubscribeEvent
	public static void onUnloadWorld(LevelEvent.Unload event) {
		LevelAccessor world = event.getLevel();
		WorldAttached.invalidateWorld(world);
	}





	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {



		@SubscribeEvent
		public static void addPackFinders(AddPackFindersEvent event) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				DynamicPack dynamicPack = new DynamicPack("create:dynamic_data", PackType.SERVER_DATA);
				event.addRepositorySource(new DynamicPackSource("create:dynamic_data", PackType.SERVER_DATA, Pack.Position.BOTTOM, dynamicPack));
			}
		}
	}
}
