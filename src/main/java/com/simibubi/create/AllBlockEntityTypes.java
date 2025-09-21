package com.simibubi.create;

import com.simibubi.create.content.equipment.clipboard.ClipboardBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonRenderer;
import com.simibubi.create.content.schematics.cannon.SchematicannonVisual;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
	private static final CreateRegistrate REGISTRATE = Create.registrate();

	// Schematics
	public static final BlockEntityEntry<SchematicannonBlockEntity> SCHEMATICANNON = REGISTRATE
		.blockEntity("schematicannon", SchematicannonBlockEntity::new)
		.visual(() -> SchematicannonVisual::new)
		.validBlocks(AllBlocks.SCHEMATICANNON)
		.renderer(() -> SchematicannonRenderer::new)
		.register();

	public static final BlockEntityEntry<SchematicTableBlockEntity> SCHEMATIC_TABLE = REGISTRATE
		.blockEntity("schematic_table", SchematicTableBlockEntity::new)
		.validBlocks(AllBlocks.SCHEMATIC_TABLE)
		.register();

	public static final BlockEntityEntry<ClipboardBlockEntity> CLIPBOARD = REGISTRATE
		.blockEntity("clipboard", ClipboardBlockEntity::new)
		.validBlocks(AllBlocks.CLIPBOARD)
		.register();

	public static void register() {
	}
}
