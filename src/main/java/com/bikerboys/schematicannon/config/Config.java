package com.bikerboys.schematicannon.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
	private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();


	public static final ModConfigSpec.BooleanValue CREATIVE_PRINT_INCLUDES_AIR = BUILDER
		.comment("Whether placing a Schematic directly in Creative Mode should replace world blocks with Air")
		.define("creativePrintIncludesAir", false);

	public static final ModConfigSpec.IntValue MAX_SCHEMATICS = BUILDER
		.comment("The amount of Schematics a player can upload until previous ones are overwritten.")
		.defineInRange("maxSchematics", 10, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue MAX_TOTAL_SCHEMATIC_SIZE = BUILDER
		.comment("[in KiloBytes]", "The maximum allowed file size of uploaded Schematics.")
		.defineInRange("maxTotalSchematicSize", 256, 16, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue MAX_SCHEMATIC_PACKET_SIZE = BUILDER
		.comment("[in Bytes]", "The maximum packet size uploaded Schematics are split into.")
		.defineInRange("maxSchematicPacketSize", 1024, 256, 32767);

	public static final ModConfigSpec.IntValue SCHEMATIC_IDLE_TIMEOUT = BUILDER
		.comment("Amount of game ticks without new packets arriving until an active schematic upload process is discarded.")
		.defineInRange("schematicIdleTimeout", 600, 100, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue SCHEMATICANNON_DELAY = BUILDER
		.comment("Amount of game ticks between shots of the cannon. Higher => Slower")
		.defineInRange("schematicannonDelay", 10, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec.IntValue SCHEMATICANNON_SHOTS_PER_GUNPOWDER = BUILDER
		.comment("Amount of blocks a Schematicannon can print per Gunpowder item provided.")
		.defineInRange("schematicannonShotsPerGunpowder", 400, 1, Integer.MAX_VALUE);

	public static final ModConfigSpec SPEC = BUILDER.build();

}
