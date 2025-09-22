package com.bikerboys.schematicannon.api.transformable;

import com.bikerboys.schematicannon.foundation.StructureTransform;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface TransformableBlockEntity {
	void transform(BlockEntity blockEntity, StructureTransform transform);
}
