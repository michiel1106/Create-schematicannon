package com.bikerboys.schematicannon.foundation.mixin.client;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.bikerboys.schematicannon.foundation.block.render.BlockDestructionProgressExtension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;

@Mixin(BlockDestructionProgress.class)
public class BlockDestructionProgressMixin implements BlockDestructionProgressExtension {
	@Unique
	private Set<BlockPos> schematicannon$extraPositions;

	@Override
	public Set<BlockPos> schematicannon$getExtraPositions() {
		return schematicannon$extraPositions;
	}

	@Override
	public void schematicannon$setExtraPositions(Set<BlockPos> positions) {
		schematicannon$extraPositions = positions;
	}
}
