package com.bikerboys.schematicannon.api.schematic.requirement;

import com.bikerboys.schematicannon.content.schematics.requirement.ItemRequirement;

import net.minecraft.world.level.block.state.BlockState;

public interface SpecialBlockEntityItemRequirement {
	ItemRequirement getRequiredItems(BlockState state);
}
