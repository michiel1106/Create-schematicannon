package com.bikerboys.schematicannon.foundation.events;

import com.bikerboys.schematicannon.SchematicannonClient;

import net.minecraft.client.Minecraft;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

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

		double delta = event.getScrollDeltaY();
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
