package com.bikerboys.schematicannon;


import com.bikerboys.schematicannon.content.schematics.SchematicAndQuillItem;
import com.bikerboys.schematicannon.content.schematics.SchematicItem;
import com.bikerboys.schematicannon.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.Item;

public class AllItems {
	private static final CreateRegistrate REGISTRATE = Schematicannon.registrate();




	// Schematics

	public static final ItemEntry<Item> EMPTY_SCHEMATIC = REGISTRATE.item("empty_schematic", Item::new)
		.properties(p -> p.stacksTo(1))
		.register();

	public static final ItemEntry<SchematicAndQuillItem> SCHEMATIC_AND_QUILL =
		REGISTRATE.item("schematic_and_quill", SchematicAndQuillItem::new)
			.properties(p -> p.stacksTo(1))
			.register();

	public static final ItemEntry<SchematicItem> SCHEMATIC = REGISTRATE.item("schematic", SchematicItem::new)
		.properties(p -> p.stacksTo(1))
		.register();


	// Load this class

	public static void register() {
	}

}
