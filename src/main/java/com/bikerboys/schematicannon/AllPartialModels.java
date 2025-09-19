package com.bikerboys.schematicannon;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class AllPartialModels {

	public static final PartialModel

	SCHEMATICANNON_CONNECTOR = block("schematicannon/connector"), SCHEMATICANNON_PIPE = block("schematicannon/pipe"),

	CRAFTING_BLUEPRINT_1x1 = entity("crafting_blueprint_small"),
	CRAFTING_BLUEPRINT_2x2 = entity("crafting_blueprint_medium"),
	CRAFTING_BLUEPRINT_3x3 = entity("crafting_blueprint_large");







	private static PartialModel block(String path) {
		return PartialModel.of(Schematicannon.asResource("block/" + path));
	}

	private static PartialModel entity(String path) {
		return PartialModel.of(Schematicannon.asResource("entity/" + path));
	}

	public static void init() {
		// init static fields
	}

}
