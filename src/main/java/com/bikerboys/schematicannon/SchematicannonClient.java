package com.bikerboys.schematicannon;

import com.bikerboys.schematicannon.content.schematics.client.ClientSchematicLoader;
import com.bikerboys.schematicannon.content.schematics.client.SchematicAndQuillHandler;
import com.bikerboys.schematicannon.content.schematics.client.SchematicHandler;
import com.bikerboys.schematicannon.foundation.ClientResourceReloadListener;
import com.bikerboys.schematicannon.foundation.blockEntity.behaviour.ValueSettingsClient;
import com.bikerboys.schematicannon.foundation.model.ModelSwapper;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBufferCache;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Schematicannon.ID, dist = Dist.CLIENT)
public class SchematicannonClient {

	public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();

	public static final ClientSchematicLoader SCHEMATIC_SENDER = new ClientSchematicLoader();
	public static final SchematicHandler SCHEMATIC_HANDLER = new SchematicHandler();
	public static final SchematicAndQuillHandler SCHEMATIC_AND_QUILL_HANDLER = new SchematicAndQuillHandler();

	public static final ValueSettingsClient VALUE_SETTINGS_HANDLER = new ValueSettingsClient();

	public static final ClientResourceReloadListener RESOURCE_RELOAD_LISTENER = new ClientResourceReloadListener();

	public SchematicannonClient(IEventBus modEventBus) {
		onCtorClient(modEventBus);
	}

	public static void onCtorClient(IEventBus modEventBus) {
		IEventBus neoEventBus = NeoForge.EVENT_BUS;

		modEventBus.addListener(SchematicannonClient::clientInit);
		modEventBus.addListener(AllParticleTypes::registerFactories);


		MODEL_SWAPPER.registerListeners(modEventBus);


	}

	public static void clientInit(final FMLClientSetupEvent event) {


		SuperByteBufferCache.getInstance().registerCompartment(CachedBuffers.PARTIAL);
		SuperByteBufferCache.getInstance().registerCompartment(CachedBuffers.DIRECTIONAL_PARTIAL);

		AllPartialModels.init();

	}



	public static void invalidateRenderers() {
		SCHEMATIC_HANDLER.updateRenderers();
	}



}
