package com.bikerboys.schematicannon.api.transformable;

import com.bikerboys.schematicannon.foundation.StructureTransform;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface TransformableBlock {
	BlockState transform(BlockState state, StructureTransform transform);
}
