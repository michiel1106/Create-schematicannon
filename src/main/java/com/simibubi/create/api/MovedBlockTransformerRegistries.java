package com.simibubi.create.api;

import com.simibubi.create.StructureTransform;
import com.simibubi.create.api.registry.SimpleRegistry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**

 */
public class MovedBlockTransformerRegistries {
	public static final SimpleRegistry<Block, BlockTransformer> BLOCK_TRANSFORMERS = SimpleRegistry.create();
	public static final SimpleRegistry<BlockEntityType<?>, BlockEntityTransformer> BLOCK_ENTITY_TRANSFORMERS = SimpleRegistry.create();

	@FunctionalInterface
	public interface BlockTransformer {
		BlockState transform(BlockState state, StructureTransform transform);
	}

	@FunctionalInterface
	public interface BlockEntityTransformer {
		void transform(BlockEntity be, StructureTransform transform);
	}
}
