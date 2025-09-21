package com.simibubi.create;

import com.simibubi.create.content.schematics.client.ClientSchematicLoader;
import com.simibubi.create.content.schematics.client.SchematicAndQuillHandler;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.foundation.ClientResourceReloadListener;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsClient;
import com.simibubi.create.foundation.model.ModelSwapper;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBufferCache;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = Create.ID, dist = Dist.CLIENT)
public class CreateClient {

	public static final ModelSwapper MODEL_SWAPPER = new ModelSwapper();

	public static final ClientSchematicLoader SCHEMATIC_SENDER = new ClientSchematicLoader();
	public static final SchematicHandler SCHEMATIC_HANDLER = new SchematicHandler();
	public static final SchematicAndQuillHandler SCHEMATIC_AND_QUILL_HANDLER = new SchematicAndQuillHandler();

	public static final ValueSettingsClient VALUE_SETTINGS_HANDLER = new ValueSettingsClient();

	public static final ClientResourceReloadListener RESOURCE_RELOAD_LISTENER = new ClientResourceReloadListener();

	public CreateClient(IEventBus modEventBus) {
		onCtorClient(modEventBus);
	}

	public static void onCtorClient(IEventBus modEventBus) {
		IEventBus neoEventBus = NeoForge.EVENT_BUS;

		modEventBus.addListener(CreateClient::clientInit);
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
