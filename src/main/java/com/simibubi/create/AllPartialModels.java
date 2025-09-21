package com.simibubi.create;


import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AllPartialModels {

	public static final PartialModel

	SCHEMATICANNON_CONNECTOR = block("schematicannon/connector"), SCHEMATICANNON_PIPE = block("schematicannon/pipe")


	;









	private static PartialModel block(String path) {
		return PartialModel.of(Create.asResource("block/" + path));
	}


	public static void init() {
	}

}
