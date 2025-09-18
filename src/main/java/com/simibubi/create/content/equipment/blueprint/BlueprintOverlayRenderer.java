package com.simibubi.create.content.equipment.blueprint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.content.equipment.blueprint.BlueprintEntity.BlueprintCraftingInventory;
import com.simibubi.create.content.equipment.blueprint.BlueprintEntity.BlueprintSection;
import com.simibubi.create.foundation.gui.AllGuiTextures;

import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Pair;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

// TODO - Split up into specific overlays
public class BlueprintOverlayRenderer {

	public static final IGuiOverlay OVERLAY = BlueprintOverlayRenderer::renderOverlay;

	static boolean active;
	static boolean empty;
	static boolean noOutput;
	static boolean lastSneakState;
	static BlueprintSection lastTargetedSection;

	static Map<ItemStack, ItemStack[]> cachedRenderedFilters = new IdentityHashMap<>();
	static List<Pair<ItemStack, Boolean>> ingredients = new ArrayList<>();
	static List<ItemStack> results = new ArrayList<>();
	static boolean resultCraftable = false;

	public static void tick() {
		Minecraft mc = Minecraft.getInstance();

		BlueprintSection last = lastTargetedSection;
		lastTargetedSection = null;
		active = false;
		noOutput = false;

		if (mc.gameMode.getPlayerMode() == GameType.SPECTATOR)
			return;

		HitResult mouseOver = mc.hitResult;
		if (mouseOver == null)
			return;
		if (mouseOver.getType() != Type.ENTITY)
			return;

		EntityHitResult entityRay = (EntityHitResult) mouseOver;
		if (!(entityRay.getEntity() instanceof BlueprintEntity blueprintEntity))
			return;

		BlueprintSection sectionAt = blueprintEntity.getSectionAt(entityRay.getLocation()
			.subtract(blueprintEntity.position()));

		lastTargetedSection = last;
		active = true;

		boolean sneak = mc.player.isShiftKeyDown();
		if (sectionAt != lastTargetedSection || AnimationTickHolder.getTicks() % 10 == 0 || lastSneakState != sneak)
			rebuild(sectionAt, sneak);

		lastTargetedSection = sectionAt;
		lastSneakState = sneak;
	}





	public static void rebuild(BlueprintSection sectionAt, boolean sneak) {
		cachedRenderedFilters.clear();
		ItemStackHandler items = sectionAt.getItems();
		boolean empty = true;
		for (int i = 0; i < 9; i++) {
			if (!items.getStackInSlot(i)
				.isEmpty()) {
				empty = false;
				break;
			}
		}

		BlueprintOverlayRenderer.empty = empty;
		BlueprintOverlayRenderer.results.clear();

		if (empty)
			return;

		boolean firstPass = true;
		boolean success = true;
		Minecraft mc = Minecraft.getInstance();
		ItemStackHandler playerInv = new ItemStackHandler(mc.player.getInventory()
			.getContainerSize());
		for (int i = 0; i < playerInv.getSlots(); i++)
			playerInv.setStackInSlot(i, mc.player.getInventory()
				.getItem(i)
				.copy());

		int amountCrafted = 0;
		Optional<CraftingRecipe> recipe = Optional.empty();
		Map<Integer, ItemStack> craftingGrid = new HashMap<>();
		ingredients.clear();
		ItemStackHandler missingItems = new ItemStackHandler(64);
		ItemStackHandler availableItems = new ItemStackHandler(64);
		List<ItemStack> newlyAdded = new ArrayList<>();
		List<ItemStack> newlyMissing = new ArrayList<>();
		boolean invalid = false;

		do {
			craftingGrid.clear();
			newlyAdded.clear();
			newlyMissing.clear();


			if (success) {
				CraftingContainer craftingInventory = new BlueprintCraftingInventory(craftingGrid);
				if (!recipe.isPresent())
					recipe = mc.level.getRecipeManager()
						.getRecipeFor(RecipeType.CRAFTING, craftingInventory, mc.level);
				ItemStack resultFromRecipe = recipe.filter(r -> r.matches(craftingInventory, mc.level))
					.map(r -> r.assemble(craftingInventory, mc.level.registryAccess()))
					.orElse(ItemStack.EMPTY);

				if (resultFromRecipe.isEmpty()) {
					if (!recipe.isPresent())
						invalid = true;
					success = false;
				} else if (resultFromRecipe.getCount() + amountCrafted > 64) {
					success = false;
				} else {
					amountCrafted += resultFromRecipe.getCount();
					if (results.isEmpty())
						results.add(resultFromRecipe.copy());
					else
						results.get(0)
							.grow(resultFromRecipe.getCount());
					resultCraftable = true;
					firstPass = false;
				}
			}

			if (success || firstPass) {
				newlyAdded.forEach(s -> ItemHandlerHelper.insertItemStacked(availableItems, s, false));
				newlyMissing.forEach(s -> ItemHandlerHelper.insertItemStacked(missingItems, s, false));
			}

			if (!success) {
				if (firstPass) {
					results.clear();
					if (!invalid)
						results.add(items.getStackInSlot(9));
					resultCraftable = false;
				}
				break;
			}

			if (!sneak)
				break;

		} while (success);

		for (int i = 0; i < 9; i++) {
			ItemStack available = availableItems.getStackInSlot(i);
			if (available.isEmpty())
				continue;
			ingredients.add(Pair.of(available, true));
		}
		for (int i = 0; i < 9; i++) {
			ItemStack missing = missingItems.getStackInSlot(i);
			if (missing.isEmpty())
				continue;
			ingredients.add(Pair.of(missing, false));
		}
	}

	public static void renderOverlay(ForgeGui gui, GuiGraphics graphics, float partialTicks, int width, int height) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.screen != null)
			return;

		if (!active || empty)
			return;


		int w = 21 * ingredients.size();

		if (!noOutput) {
			w += 21 * results.size();
			w += 30;
		}

		int x = (width - w) / 2;
		int y = height - 100;


		// Ingredients
		for (Pair<ItemStack, Boolean> pair : ingredients) {
			RenderSystem.enableBlend();
			(pair.getSecond() ? AllGuiTextures.HOTSLOT_ACTIVE : AllGuiTextures.HOTSLOT).render(graphics, x, y);
			ItemStack itemStack = pair.getFirst();
			x += 21;
		}

		if (noOutput)
			return;

		// Arrow
		x += 5;
		RenderSystem.enableBlend();
			AllGuiTextures.HOTSLOT_ARROW_BAD.render(graphics, x, y + 4);
		x += 25;

		// Outputs
		if (results.isEmpty()) {
			AllGuiTextures.HOTSLOT.render(graphics, x, y);
			GuiGameElement.of(Items.BARRIER)
				.at(x + 3, y + 3)
				.render(graphics);
		} else {
			for (ItemStack result : results) {
				AllGuiTextures slot = resultCraftable ? AllGuiTextures.HOTSLOT_SUPER_ACTIVE : AllGuiTextures.HOTSLOT;
				slot.render(graphics, resultCraftable ? x - 1 : x, resultCraftable ? y - 1 : y);
				drawItemStack(graphics, mc, x, y, result, null);
				x += 21;
			}
		}


		RenderSystem.disableBlend();
	}

	public static void drawItemStack(GuiGraphics graphics, Minecraft mc, int x, int y, ItemStack itemStack,
									 String count) {


		GuiGameElement.of(itemStack)
			.at(x + 3, y + 3)
			.render(graphics);
		graphics.renderItemDecorations(mc.font, itemStack, x + 3, y + 3, count);
	}

	private static ItemStack[] getItemsMatchingFilter(ItemStack filter) {
		return cachedRenderedFilters.computeIfAbsent(filter, itemStack -> {

			return new ItemStack[0];
		});
	}

}
