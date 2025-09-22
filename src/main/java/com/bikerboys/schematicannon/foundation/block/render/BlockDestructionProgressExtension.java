package com.bikerboys.schematicannon.foundation.block.render;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;

public interface BlockDestructionProgressExtension {
	@Nullable
	Set<BlockPos> schematicannon$getExtraPositions();

	void schematicannon$setExtraPositions(@Nullable Set<BlockPos> positions);
}
