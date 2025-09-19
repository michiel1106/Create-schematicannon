package com.bikerboys.schematicannon;


import com.bikerboys.schematicannon.content.schematics.client.ClientSchematicLoader;
import com.bikerboys.schematicannon.content.schematics.client.SchematicAndQuillHandler;
import com.bikerboys.schematicannon.content.schematics.client.SchematicHandler;
import com.bikerboys.schematicannon.foundation.ClientResourceReloadListener;
import com.bikerboys.schematicannon.foundation.model.ModelSwapper;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBufferCache;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class SchematicannonClient {

	public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();

	public static final ClientSchematicLoader SCHEMATIC_SENDER = new ClientSchematicLoader();
	public static final SchematicHandler SCHEMATIC_HANDLER = new SchematicHandler();
	public static final SchematicAndQuillHandler SCHEMATIC_AND_QUILL_HANDLER = new SchematicAndQuillHandler();



	public static final ClientResourceReloadListener RESOURCE_RELOAD_LISTENER = new ClientResourceReloadListener();

	public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
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
