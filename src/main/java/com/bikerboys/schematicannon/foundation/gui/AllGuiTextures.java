package com.bikerboys.schematicannon.foundation.gui;

import com.bikerboys.schematicannon.Schematicannon;

import net.createmod.catnip.gui.TextureSheetSegment;
import net.createmod.catnip.gui.UIRenderHelper;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum AllGuiTextures implements ScreenElement, TextureSheetSegment {

	// Inventories
	PLAYER_INVENTORY("player_inventory", 176, 108),

	SCHEMATIC("schematics", 10, 8, 192, 123),
	SCHEMATIC_TITLE("schematics_2", 205, 15),
	SCHEMATIC_SLOT("widgets", 54, 0, 16, 16),
	SCHEMATIC_PROMPT("schematics_2", 213, 79),
	HUD_BACKGROUND("overlay", 0, 0, 16, 16),

	SCHEMATIC_TABLE("schematics", 10, 139, 214, 85),
	SCHEMATIC_TABLE_PROGRESS("schematics", 10, 224, 84, 16),

	SCHEMATICANNON_TOP("schematics_2", 0, 77, 213, 42),
	SCHEMATICANNON_BOTTOM("schematics_2", 0, 119, 213, 99),
	SCHEMATICANNON_PROGRESS("schematics_2", 76, 239, 114, 16),
	SCHEMATICANNON_CHECKLIST_PROGRESS("schematics_2", 191, 240, 16, 14),
	SCHEMATICANNON_HIGHLIGHT("schematics_2", 1, 229, 26, 26),
	SCHEMATICANNON_FUEL("schematics_2", 28, 222, 47, 16),
	SCHEMATICANNON_FUEL_CREATIVE("schematics_2", 28, 239, 47, 16),

	CLIPBOARD("clipboard", 0, 0, 256, 256),
	CLIPBOARD_ADDRESS("widgets", 116, 7, 8, 8),
	CLIPBOARD_ADDRESS_INACTIVE("widgets", 125, 7, 8, 8),

	DATA_AREA_START("display_link", 0, 163, 2, 18),
	DATA_AREA_SPEECH("display_link", 8, 163, 5, 18),
	DATA_AREA("display_link", 3, 163, 1, 18),
	DATA_AREA_END("display_link", 5, 163, 2, 18),

	// Widgets
	BUTTON("widgets", 18, 18),
	BUTTON_HOVER("widgets", 18, 0, 18, 18),
	BUTTON_DOWN("widgets", 36, 0, 18, 18),
	BUTTON_GREEN("widgets", 72, 0, 18, 18),
	BUTTON_DISABLED("widgets", 90, 0, 18, 18),
	INDICATOR("widgets", 0, 18, 18, 6),
	INDICATOR_WHITE("widgets", 18, 18, 18, 6),
	INDICATOR_GREEN("widgets", 36, 18, 18, 6),
	INDICATOR_YELLOW("widgets", 54, 18, 18, 6),
	INDICATOR_RED("widgets", 72, 18, 18, 6);

	public final ResourceLocation location;
	private final int width;
	private final int height;
	private final int startX;
	private final int startY;

	AllGuiTextures(String location, int width, int height) {
		this(location, 0, 0, width, height);
	}

	AllGuiTextures(String location, int startX, int startY, int width, int height) {
		this(Schematicannon.ID, location, startX, startY, width, height);
	}

	AllGuiTextures(String namespace, String location, int startX, int startY, int width, int height) {
		this.location = new ResourceLocation(namespace, "textures/gui/" + location + ".png");
		this.width = width;
		this.height = height;
		this.startX = startX;
		this.startY = startY;
	}

	@Override
	public ResourceLocation getLocation() {
		return location;
	}

	@OnlyIn(Dist.CLIENT)
	public void render(GuiGraphics graphics, int x, int y) {
		graphics.blit(location, x, y, startX, startY, width, height);
	}

	@OnlyIn(Dist.CLIENT)
	public void render(GuiGraphics graphics, int x, int y, Color c) {
		bind();
		UIRenderHelper.drawColoredTexture(graphics, c, x, y, startX, startY, width, height);
	}

	@Override
	public int getStartX() {
		return startX;
	}

	@Override
	public int getStartY() {
		return startY;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
