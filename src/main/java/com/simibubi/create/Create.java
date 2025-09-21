package com.simibubi.create;

import java.util.Random;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.simibubi.create.api.schematic.state.AllSchematicStateFilters;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.simibubi.create.foundation.CreateNBTProcessors;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;

import net.createmod.catnip.lang.FontHelper;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Create.ID)
public class Create {
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

	/**
	 * <b>Other mods should not use this field!</b> If you are an addon developer, create your own instance of
	 * {@link CreateRegistrate}.
	 * </br
	 * If you were using this instance to register a callback listener use {@link CreateRegistrateRegistrationCallback#register} instead.
	 */
	private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
		.defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
		.setTooltipModifierFactory(item ->
			new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
				.andThen(TooltipModifier.mapNull(KineticStats.create(item)))
		);

	public static final ServerSchematicLoader SCHEMATIC_RECEIVER = new ServerSchematicLoader();

	public Create(IEventBus eventBus, ModContainer modContainer) {
		onCtor(eventBus, modContainer);
	}

	public static void onCtor(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("{} {} initializing! Commit hash: {}", NAME, CreateBuildInfo.VERSION, CreateBuildInfo.GIT_COMMIT);
		ModLoadingContext modLoadingContext = ModLoadingContext.get();

		REGISTRATE.registerEventListeners(modEventBus);

		AllSoundEvents.prepare();
		AllBlocks.register();
		AllItems.register();
		AllMenuTypes.register();
		AllEntityTypes.register();
		AllBlockEntityTypes.register();
		AllParticleTypes.register(modEventBus);
		AllStructureProcessorTypes.register(modEventBus);
		AllPackets.register();
		AllDataComponents.register(modEventBus);

		AllSchematicStateFilters.registerDefaults();



		modEventBus.addListener(Create::init);
		modEventBus.addListener(Create::onRegister);

		modEventBus.addListener(AllSoundEvents::register);

	}

	public static void init(final FMLCommonSetupEvent event) {
		CreateNBTProcessors.register();
	}

	public static void onRegister(final RegisterEvent event) {
	}

	public static LangBuilder lang() {
		return new LangBuilder(ID);
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}

	public static CreateRegistrate registrate() {
		if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("com.bikerboys.schematicannon"))
			throw new UnsupportedOperationException("Other mods are not permitted to use schematicannons registrate instance.");
		return REGISTRATE;
	}
}
