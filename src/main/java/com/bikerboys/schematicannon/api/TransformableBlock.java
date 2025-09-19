package com.bikerboys.schematicannon.api;

import com.bikerboys.schematicannon.StructureTransform;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface TransformableBlock {
	BlockState transform(BlockState state, StructureTransform transform);
}
