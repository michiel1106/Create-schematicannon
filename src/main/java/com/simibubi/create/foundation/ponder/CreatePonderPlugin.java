package com.simibubi.create.foundation.ponder;

import com.simibubi.create.Create;

import net.createmod.ponder.api.level.PonderLevel;
import net.createmod.ponder.api.registration.IndexExclusionHelper;
import net.createmod.ponder.api.registration.PonderPlugin;

public class CreatePonderPlugin implements PonderPlugin {

	@Override
	public String getModId() {
		return Create.ID;
	}



	@Override
	public void onPonderLevelRestore(PonderLevel ponderLevel) {
		PonderWorldBlockEntityFix.fixControllerBlockEntities(ponderLevel);
	}

	@Override
	public void indexExclusions(IndexExclusionHelper helper) {

	}
}
