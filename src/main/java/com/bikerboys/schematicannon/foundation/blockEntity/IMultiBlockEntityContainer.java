package com.bikerboys.schematicannon.foundation.blockEntity;

import net.minecraft.core.BlockPos;

public interface IMultiBlockEntityContainer {

	BlockPos getController();
	boolean isController();
	void setController(BlockPos pos);
	BlockPos getLastKnownPos();


	int getHeight();
	void setHeight(int height);
	int getWidth();
	void setWidth(int width);

}
