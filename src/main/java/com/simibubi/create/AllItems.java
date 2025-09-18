package com.simibubi.create;

import com.simibubi.create.content.equipment.blueprint.BlueprintItem;
import com.simibubi.create.content.schematics.SchematicAndQuillItem;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.world.item.Item;

public class AllItems {
	private static final CreateRegistrate REGISTRATE = Create.registrate();

	static {
		REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
	}




	public static final ItemEntry<BlueprintItem> CRAFTING_BLUEPRINT =
		REGISTRATE.item("crafting_blueprint", BlueprintItem::new)
			.register();










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



	public static void register() {
	}

}
