package com.bikerboys.schematicannon.foundation.events;

import com.bikerboys.schematicannon.SchematicannonClient;

import net.minecraft.client.Minecraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(Dist.CLIENT)
public class InputEvents {

	@SubscribeEvent
	public static void onKeyInput(InputEvent.Key event) {
		if (Minecraft.getInstance().screen != null)
			return;

		int key = event.getKey();
		boolean pressed = !(event.getAction() == 0);

		SchematicannonClient.SCHEMATIC_HANDLER.onKeyInput(key, pressed);
	}

	@SubscribeEvent
	public static void onMouseScrolled(InputEvent.MouseScrollingEvent event) {
		if (Minecraft.getInstance().screen != null)
			return;

		double delta = event.getScrollDelta();
//		CollisionDebugger.onScroll(delta);
		boolean cancelled = SchematicannonClient.SCHEMATIC_HANDLER.mouseScrolled(delta)
			|| SchematicannonClient.SCHEMATIC_AND_QUILL_HANDLER.mouseScrolled(delta);
		event.setCanceled(cancelled);
	}

	@SubscribeEvent
	public static void onMouseInput(InputEvent.MouseButton.Pre event) {
		if (Minecraft.getInstance().screen != null)
			return;

		int button = event.getButton();
		boolean pressed = !(event.getAction() == 0);

		if (SchematicannonClient.SCHEMATIC_HANDLER.onMouseInput(button, pressed))
			event.setCanceled(true);
		else if (SchematicannonClient.SCHEMATIC_AND_QUILL_HANDLER.onMouseInput(button, pressed))
			event.setCanceled(true);
	}



}
