package com.bikerboys.schematicannon;

import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonMenu;
import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonScreen;
import com.bikerboys.schematicannon.content.schematics.table.SchematicTableMenu;
import com.bikerboys.schematicannon.content.schematics.table.SchematicTableScreen;
import com.tterrag.registrate.builders.MenuBuilder.ForgeMenuFactory;
import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class AllMenuTypes {

	public static final MenuEntry<SchematicTableMenu> SCHEMATIC_TABLE =
		register("schematic_table", SchematicTableMenu::new, () -> SchematicTableScreen::new);

	public static final MenuEntry<SchematicannonMenu> SCHEMATICANNON =
		register("schematicannon", SchematicannonMenu::new, () -> SchematicannonScreen::new);


	private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
		String name, ForgeMenuFactory<C> factory, NonNullSupplier<ScreenFactory<C, S>> screenFactory) {
		return Schematicannon.registrate()
			.menu(name, factory, screenFactory)
			.register();
	}

	public static void register() {
	}

}
