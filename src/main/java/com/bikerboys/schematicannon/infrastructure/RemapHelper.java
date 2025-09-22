package com.bikerboys.schematicannon.infrastructure;

import static com.bikerboys.schematicannon.AllItems.SCHEMATIC;
import static com.bikerboys.schematicannon.AllItems.SCHEMATIC_AND_QUILL;
import static com.bikerboys.schematicannon.Schematicannon.asResource;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber
public class RemapHelper {
	private static final Map<String, ResourceLocation> reMap = new HashMap<>();

	static {


		reMap.put("empty_blueprint", SCHEMATIC.getId());
		reMap.put("blueprint_and_quill", SCHEMATIC_AND_QUILL.getId());
		reMap.put("blueprint", SCHEMATIC.getId());


	}


	@SubscribeEvent
	public static void remap(RegisterEvent event) {
		Registry<?> registry = event.getRegistry();

		if (registry == Registries.BLOCK || registry == Registries.ITEM) {
			reMap.forEach((string, resourceLocation) -> registry.addAlias(asResource(string), resourceLocation));
		}
	}
}
