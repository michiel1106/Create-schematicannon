package com.simibubi.create.api.transformable;

import com.simibubi.create.foundation.StructureTransform;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface TransformableBlockEntity {
	void transform(BlockEntity blockEntity, StructureTransform transform);
}
