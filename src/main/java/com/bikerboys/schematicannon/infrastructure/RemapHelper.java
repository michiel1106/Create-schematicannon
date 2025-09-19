package com.bikerboys.schematicannon.infrastructure;

import static com.bikerboys.schematicannon.AllItems.SCHEMATIC;
import static com.bikerboys.schematicannon.AllItems.SCHEMATIC_AND_QUILL;

import java.util.HashMap;
import java.util.Map;

import com.bikerboys.schematicannon.Schematicannon;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.MissingMappingsEvent.Mapping;

@Mod.EventBusSubscriber
public class RemapHelper {
	private static final Map<String, ResourceLocation> reMap = new HashMap<>();

	static {
		reMap.put("empty_blueprint", SCHEMATIC.getId());
		reMap.put("blueprint_and_quill", SCHEMATIC_AND_QUILL.getId());
		reMap.put("blueprint", SCHEMATIC.getId());
	}


	@SubscribeEvent
	public static void remapBlocks(MissingMappingsEvent event) {
		for (Mapping<Block> mapping : event.getMappings(Registries.BLOCK, Schematicannon.ID)) {
			ResourceLocation key = mapping.getKey();
			String path = key.getPath();
			ResourceLocation remappedId = reMap.get(path);
			if (remappedId != null) {
				Block remapped = ForgeRegistries.BLOCKS.getValue(remappedId);
				if (remapped != null) {
					Schematicannon.LOGGER.warn("Remapping block '{}' to '{}'", key, remappedId);
					try {
						mapping.remap(remapped);
					} catch (Throwable t) {
						Schematicannon.LOGGER.warn("Remapping block '{}' to '{}' failed: {}", key, remappedId, t);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void remapItems(MissingMappingsEvent event) {
		for (Mapping<Item> mapping : event.getMappings(Registries.ITEM, Schematicannon.ID)) {
			ResourceLocation key = mapping.getKey();
			String path = key.getPath();
			ResourceLocation remappedId = reMap.get(path);
			if (remappedId != null) {
				Item remapped = ForgeRegistries.ITEMS.getValue(remappedId);
				if (remapped != null) {
					Schematicannon.LOGGER.warn("Remapping item '{}' to '{}'", key, remappedId);
					try {
						mapping.remap(remapped);
					} catch (Throwable t) {
						Schematicannon.LOGGER.warn("Remapping item '{}' to '{}' failed: {}", key, remappedId, t);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void remapFluids(MissingMappingsEvent event) {
		for (Mapping<Fluid> mapping : event.getMappings(Registries.FLUID, Schematicannon.ID)) {
			ResourceLocation key = mapping.getKey();
			String path = key.getPath();
			if (path.equals("milk"))
				mapping.remap(ForgeMod.MILK.get());
			else if (path.equals("flowing_milk"))
				mapping.remap(ForgeMod.FLOWING_MILK.get());
		}
	}


}
