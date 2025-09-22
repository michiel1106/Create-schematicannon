package com.bikerboys.schematicannon;


import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AllPartialModels {

	public static final PartialModel

	SCHEMATICANNON_CONNECTOR = block("schematicannon/connector"), SCHEMATICANNON_PIPE = block("schematicannon/pipe")


	;









	private static PartialModel block(String path) {
		return PartialModel.of(Schematicannon.asResource("block/" + path));
	}


	public static void init() {
	}

}
