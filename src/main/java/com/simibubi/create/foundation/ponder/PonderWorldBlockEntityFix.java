package com.simibubi.create.foundation.ponder;

import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;

import net.createmod.ponder.api.level.PonderLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PonderWorldBlockEntityFix {

	public static void fixControllerBlockEntities(PonderLevel world) {
		for (BlockEntity blockEntity : world.getBlockEntities()) {



			if (blockEntity instanceof IMultiBlockEntityContainer multiBlockEntity) {
				BlockPos lastKnown = multiBlockEntity.getLastKnownPos();
				BlockPos current = blockEntity.getBlockPos();
				if (lastKnown == null || current == null)
					continue;
				if (multiBlockEntity.isController())
					continue;
				if (!lastKnown.equals(current)) {
					BlockPos newControllerPos = multiBlockEntity.getController().offset(current.subtract(lastKnown));
					multiBlockEntity.setController(newControllerPos);
				}
			}

		}
	}

}
