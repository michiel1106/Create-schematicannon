package com.simibubi.create.api;

import com.simibubi.create.StructureTransform;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface TransformableBlockEntity {
	void transform(BlockEntity blockEntity, StructureTransform transform);
}
