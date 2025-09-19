package com.bikerboys.schematicannon.api;

import com.bikerboys.schematicannon.StructureTransform;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface TransformableBlockEntity {
	void transform(BlockEntity blockEntity, StructureTransform transform);
}
