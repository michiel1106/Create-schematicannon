package com.simibubi.create;

import java.util.Random;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.simibubi.create.foundation.CreateNBTProcessors;
import com.simibubi.create.foundation.data.CreateRegistrate;

import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
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

@Mod(Create.ID)
public class Create {
	public static final String ID = "create";
	public static final String NAME = "Create";

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


	private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
		.defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

	public static final ServerSchematicLoader SCHEMATIC_RECEIVER = new ServerSchematicLoader();


	public Create() {
		onCtor();
	}

	public static void onCtor() {
		LOGGER.info("{} {} initializing! Commit hash: {}", NAME, CreateBuildInfo.VERSION, CreateBuildInfo.GIT_COMMIT);

		ModLoadingContext modLoadingContext = ModLoadingContext.get();

		IEventBus modEventBus = FMLJavaModLoadingContext.get()
			.getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		AllSoundEvents.prepare();
		AllBlocks.register();
		AllItems.register();
		AllMenuTypes.register();
		AllEntityTypes.register();
		AllBlockEntityTypes.register();
		AllParticleTypes.register(modEventBus);
		AllStructureProcessorTypes.register(modEventBus);
		AllPackets.registerPackets();


		AllSchematicStateFilters.registerDefaults();

		ForgeMod.enableMilkFluid();


		modEventBus.addListener(Create::init);
		modEventBus.addListener(AllSoundEvents::register);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreateClient.onCtorClient(modEventBus, forgeEventBus));

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

	public static CreateRegistrate registrate() {
		if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("com.simibubi.create"))
			throw new UnsupportedOperationException("Other mods are not permitted to use create's registrate instance.");
		return REGISTRATE;
	}
}
