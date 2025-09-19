package com.bikerboys.schematicannon;

import java.util.Random;

import org.slf4j.Logger;

import com.bikerboys.schematicannon.content.schematics.ServerSchematicLoader;
import com.bikerboys.schematicannon.foundation.CreateNBTProcessors;
import com.bikerboys.schematicannon.foundation.data.SchematicannonRegistrate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;

import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Schematicannon.ID)
public class Schematicannon {
	public static final String ID = "schematicannon";
	public static final String NAME = "Schematicannon";

	public static final Logger LOGGER = LogUtils.getLogger();

	private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	/**
	 * Use the {@link Random} of a local {@link Level} or {@link Entity} or create one
	 */
	@Deprecated
	public static final Random RANDOM = new Random();


	private static final SchematicannonRegistrate REGISTRATE = SchematicannonRegistrate.create(ID)
		.defaultCreativeTab(CreativeModeTabs.REDSTONE_BLOCKS);

	public static final ServerSchematicLoader SCHEMATIC_RECEIVER = new ServerSchematicLoader();


	public Schematicannon() {
		onCtor();
	}

	public static void onCtor() {
		LOGGER.info("{} {} initializing! Commit hash: {}", NAME, com.bikerboys.schematicannon.CreateBuildInfo.VERSION, com.bikerboys.schematicannon.CreateBuildInfo.GIT_COMMIT);



		ModLoadingContext modLoadingContext = ModLoadingContext.get();

		IEventBus modEventBus = FMLJavaModLoadingContext.get()
			.getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		AllSoundEvents.prepare();
		AllBlocks.register();
		AllItems.register();
		AllMenuTypes.register();
		AllBlockEntityTypes.register();
		AllParticleTypes.register(modEventBus);
		AllStructureProcessorTypes.register(modEventBus);
		AllPackets.registerPackets();


		AllSchematicStateFilters.registerDefaults();

		ForgeMod.enableMilkFluid();


		modEventBus.addListener(Schematicannon::init);
		modEventBus.addListener(AllSoundEvents::register);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SchematicannonClient.onCtorClient(modEventBus, forgeEventBus));

	}

	public static void init(final FMLCommonSetupEvent event) {
		CreateNBTProcessors.register();


	}


	public static LangBuilder lang() {
		return new LangBuilder(ID);
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}

	public static SchematicannonRegistrate registrate() {
		if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("com.bikerboys.schematicannon"))
			throw new UnsupportedOperationException("Other mods are not permitted to use schematicannons registrate instance.");
		return REGISTRATE;
	}
}
