package com.bikerboys.schematicannon;

import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardBlockEntity;
import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonBlockEntity;
import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonRenderer;
import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonVisual;
import com.bikerboys.schematicannon.content.schematics.table.SchematicTableBlockEntity;
import com.bikerboys.schematicannon.foundation.data.SchematicannonRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
	private static final SchematicannonRegistrate REGISTRATE = Schematicannon.registrate();

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
