package com.bikerboys.schematicannon.api.schematic.requirement;

import org.jetbrains.annotations.Nullable;

import com.bikerboys.schematicannon.content.schematics.requirement.ItemRequirement;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface SpecialBlockItemRequirement {
	ItemRequirement getRequiredItems(BlockState state, @Nullable BlockEntity blockEntity);
}
