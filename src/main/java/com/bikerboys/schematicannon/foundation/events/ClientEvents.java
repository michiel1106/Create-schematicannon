package com.bikerboys.schematicannon.foundation.events;

import java.util.function.Supplier;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.bikerboys.schematicannon.Schematicannon;
import com.bikerboys.schematicannon.SchematicannonClient;
import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardValueSettingsHandler;
import com.bikerboys.schematicannon.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.bikerboys.schematicannon.foundation.blockEntity.behaviour.scrollValue.ScrollValueRenderer;
import com.bikerboys.schematicannon.foundation.item.TooltipModifier;
import com.bikerboys.schematicannon.foundation.utility.CameraAngleAnimationService;
import com.bikerboys.schematicannon.foundation.utility.ServerSpeedProvider;
import com.bikerboys.schematicannon.foundation.utility.TickBasedCache;

import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.config.ui.BaseConfigScreen;
import net.createmod.catnip.levelWrappers.WrappedClientLevel;
import net.createmod.catnip.render.DefaultSuperRenderTypeBuffer;
import net.createmod.catnip.render.SuperRenderTypeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@SuppressWarnings("ALL")
@EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
	@SubscribeEvent
	public static void onTickPre(ClientTickEvent.Pre event) {
		onTick( true);
	}

	@SubscribeEvent
	public static void onTickPost(ClientTickEvent.Post event) {
		onTick(false);
	}

	public static boolean isGameActive() {
		return Minecraft.getInstance().level != null && Minecraft.getInstance().player != null;
	}

	public static void onTick(boolean isPreEvent) {
		if (!isGameActive())
			return;

		SchematicannonClient.SCHEMATIC_SENDER.tick();
		SchematicannonClient.SCHEMATIC_AND_QUILL_HANDLER.tick();
		SchematicannonClient.SCHEMATIC_HANDLER.tick();

		ServerSpeedProvider.clientTick();
		ScrollValueRenderer.tick();

		CameraAngleAnimationService.tick();
		ClipboardValueSettingsHandler.clientTick();
		SchematicannonClient.VALUE_SETTINGS_HANDLER.tick();
		ScrollValueHandler.tick();

		TickBasedCache.clientTick();
	}


	@SubscribeEvent
	public static void onLoadWorld(LevelEvent.Load event) {
		LevelAccessor world = event.getLevel();
		if (world.isClientSide() && world instanceof ClientLevel && !(world instanceof WrappedClientLevel)) {
			SchematicannonClient.invalidateRenderers();
			AnimationTickHolder.reset();
		}
	}

	@SubscribeEvent
	public static void onUnloadWorld(LevelEvent.Unload event) {
		if (!event.getLevel()
			.isClientSide())
			return;
		SchematicannonClient.invalidateRenderers();
		AnimationTickHolder.reset();
	}

	@SubscribeEvent
	public static void onRenderWorld(RenderLevelStageEvent event) {
		if (event.getStage() != Stage.AFTER_PARTICLES)
			return;

		PoseStack ms = event.getPoseStack();
		ms.pushPose();
		SuperRenderTypeBuffer buffer = DefaultSuperRenderTypeBuffer.getInstance();
		Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
			.getPosition();

		SchematicannonClient.SCHEMATIC_HANDLER.render(ms, buffer, camera);


		buffer.draw();
		RenderSystem.enableCull();
		ms.popPose();
	}

	@SubscribeEvent
	public static void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
		float partialTicks = AnimationTickHolder.getPartialTicks();

		if (CameraAngleAnimationService.isYawAnimating())
			event.setYaw(CameraAngleAnimationService.getYaw(partialTicks));

		if (CameraAngleAnimationService.isPitchAnimating())
			event.setPitch(CameraAngleAnimationService.getPitch(partialTicks));
	}

	@SubscribeEvent
	public static void addToItemTooltip(ItemTooltipEvent event) {
		if (event.getEntity() == null)
			return;

		Item item = event.getItemStack().getItem();
		TooltipModifier modifier = TooltipModifier.REGISTRY.get(item);
		if (modifier != null && modifier != TooltipModifier.EMPTY) {
			modifier.modify(event);
		}

	}








	@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
	public static class ModBusEvents {

		@SubscribeEvent
		public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
			event.registerReloadListener(SchematicannonClient.RESOURCE_RELOAD_LISTENER);
		}


		@SubscribeEvent
		public static void registerGuiOverlays(RegisterGuiLayersEvent event) {

			event.registerAbove(VanillaGuiLayers.HOTBAR, Schematicannon.asResource("value_settings"), SchematicannonClient.VALUE_SETTINGS_HANDLER);
			event.registerAbove(VanillaGuiLayers.HOTBAR, Schematicannon.asResource("schematic"), SchematicannonClient.SCHEMATIC_HANDLER);
		}


		@SubscribeEvent
		public static void onLoadComplete(FMLLoadCompleteEvent event) {
			ModContainer createContainer = ModList.get()
				.getModContainerById(Schematicannon.ID)
				.orElseThrow(() -> new IllegalStateException("Schematicannon mod container missing on LoadComplete"));
			Supplier<IConfigScreenFactory> configScreen = () -> (mc, previousScreen) -> new BaseConfigScreen(previousScreen, Schematicannon.ID);
			createContainer.registerExtensionPoint(IConfigScreenFactory.class, configScreen);
		}

	}
}
