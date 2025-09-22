package com.bikerboys.schematicannon;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.bikerboys.schematicannon.content.schematics.SchematicProcessor;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllStructureProcessorTypes {
	private static final DeferredRegister<StructureProcessorType<?>> REGISTER = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, Schematicannon.ID);

	public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<SchematicProcessor>> SCHEMATIC = REGISTER.register("schematic", () -> () -> SchematicProcessor.CODEC);

	@Internal
	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}
}
