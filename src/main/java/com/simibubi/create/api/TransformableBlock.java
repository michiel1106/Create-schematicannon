package com.simibubi.create.api;

import com.simibubi.create.StructureTransform;

import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface TransformableBlock {
	BlockState transform(BlockState state, StructureTransform transform);
}
