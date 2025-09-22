package com.bikerboys.schematicannon.foundation.events;

import com.bikerboys.schematicannon.Schematicannon;
import com.bikerboys.schematicannon.foundation.pack.DynamicPack;
import com.bikerboys.schematicannon.foundation.pack.DynamicPackSource;
import com.bikerboys.schematicannon.foundation.utility.TickBasedCache;

import net.createmod.catnip.data.WorldAttached;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.level.LevelAccessor;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@EventBusSubscriber
public class CommonEvents {

	@SubscribeEvent
	public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
		Schematicannon.SCHEMATIC_RECEIVER.tick();
		TickBasedCache.tick();
	}



	@SubscribeEvent
	public static void serverStopping(ServerStoppingEvent event) {
		Schematicannon.SCHEMATIC_RECEIVER.shutdown();
	}

	@SubscribeEvent
	public static void onLoadWorld(LevelEvent.Load event) {
		LevelAccessor world = event.getLevel();
	}

	@SubscribeEvent
	public static void onUnloadWorld(LevelEvent.Unload event) {
		LevelAccessor world = event.getLevel();
		WorldAttached.invalidateWorld(world);
	}




	@EventBusSubscriber
	public static class ModBusEvents {
		@SubscribeEvent
		public static void addPackFinders(AddPackFindersEvent event) {
			if (event.getPackType() == PackType.SERVER_DATA) {
				DynamicPack dynamicPack = new DynamicPack("schematicannon:dynamic_data", PackType.SERVER_DATA);
				event.addRepositorySource(new DynamicPackSource("schematicannon:dynamic_data", PackType.SERVER_DATA, Pack.Position.BOTTOM, dynamicPack));
			}
		}

	}
}
