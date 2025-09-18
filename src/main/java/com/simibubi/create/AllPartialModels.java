package com.simibubi.create;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simibubi.create.content.fluids.FluidTransportBehaviour;
import com.simibubi.create.content.kinetics.gantry.GantryShaftBlock;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.simibubi.create.content.logistics.box.PackageStyles.PackageStyle;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.data.Iterate;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class AllPartialModels {

	public static final PartialModel

	SCHEMATICANNON_CONNECTOR = block("schematicannon/connector"), SCHEMATICANNON_PIPE = block("schematicannon/pipe");




	public record GantryShaftKey(GantryShaftBlock.Part part, boolean powered, boolean flipped) {
		private ResourceLocation name() {
			String partName = part.getSerializedName();

			if (!(flipped || powered)) {
				// Non-generated
				return Create.asResource("block/gantry_shaft/block_" + partName);
			}

			String flipped = this.flipped ? "_flipped" : "";
			String powered = this.powered ? "_powered" : "";

			return Create.asResource("block/gantry_shaft_" + partName + powered + flipped);
		}
	}



	private static PartialModel block(String path) {
		return PartialModel.of(Create.asResource("block/" + path));
	}

	private static PartialModel entity(String path) {
		return PartialModel.of(Create.asResource("entity/" + path));
	}

	public static void init() {
		// init static fields
	}

}
