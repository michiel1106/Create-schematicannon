package com.simibubi.create.api.transformable;

import com.simibubi.create.foundation.StructureTransform;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface TransformableBlock {
	BlockState transform(BlockState state, StructureTransform transform);
}
