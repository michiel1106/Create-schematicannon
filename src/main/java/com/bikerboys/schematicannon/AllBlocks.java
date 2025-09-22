package com.bikerboys.schematicannon;

import static com.bikerboys.schematicannon.foundation.data.ModelGen.customItemModel;
import static com.bikerboys.schematicannon.foundation.data.recipe.TagGen.axeOrPickaxe;
import static com.bikerboys.schematicannon.foundation.data.recipe.TagGen.pickaxeOnly;

import com.bikerboys.schematicannon.AllTags.AllBlockTags;
import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardBlock;
import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardBlockItem;
import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardOverrides;
import com.bikerboys.schematicannon.content.schematics.cannon.SchematicannonBlock;
import com.bikerboys.schematicannon.content.schematics.table.SchematicTableBlock;
import com.bikerboys.schematicannon.foundation.data.AssetLookup;
import com.bikerboys.schematicannon.foundation.data.CreateRegistrate;
import com.bikerboys.schematicannon.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

@SuppressWarnings("removal")
public class AllBlocks {
	private static final CreateRegistrate REGISTRATE = Schematicannon.registrate();



	// Schematics

	public static final BlockEntry<SchematicannonBlock> SCHEMATICANNON =
		REGISTRATE.block("schematicannon", SchematicannonBlock::new)
			.initialProperties(() -> Blocks.DISPENSER)
			.properties(p -> p.mapColor(MapColor.COLOR_GRAY))
			.transform(pickaxeOnly())
			.blockstate((ctx, prov) -> prov.simpleBlock(ctx.getEntry(), AssetLookup.partialBaseModel(ctx, prov)))
			.loot((lt, block) -> {
				Builder builder = LootTable.lootTable();
				LootItemCondition.Builder survivesExplosion = ExplosionCondition.survivesExplosion();
				lt.add(block, builder.withPool(LootPool.lootPool()
					.when(survivesExplosion)
					.setRolls(ConstantValue.exactly(1))
					.add(LootItem.lootTableItem(AllBlocks.SCHEMATICANNON.get()
							.asItem())
						.apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
							.include(AllDataComponents.SCHEMATICANNON_OPTIONS)))));
			})
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<SchematicTableBlock> SCHEMATIC_TABLE =
		REGISTRATE.block("schematic_table", SchematicTableBlock::new)
			.initialProperties(() -> Blocks.LECTERN)
			.properties(p -> p.mapColor(MapColor.PODZOL)
				.forceSolidOn())
			.transform(axeOrPickaxe())
			.blockstate((ctx, prov) -> prov.horizontalBlock(ctx.getEntry(), prov.models()
				.getExistingFile(ctx.getId()), 0))
			.simpleItem()
			.register();

	public static final BlockEntry<ClipboardBlock> CLIPBOARD = REGISTRATE.block("clipboard", ClipboardBlock::new)
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.forceSolidOn())
		.transform(axeOrPickaxe())
		.tag(AllBlockTags.SAFE_NBT.tag)
		.blockstate((c, p) -> p.horizontalFaceBlock(c.get(),
			s -> AssetLookup.partialBaseModel(c, p, s.getValue(ClipboardBlock.WRITTEN) ? "written" : "empty")))
		.loot((lt, b) -> lt.add(b, BlockLootSubProvider.noDrop()))
		.item(ClipboardBlockItem::new)
		.onRegister(ClipboardBlockItem::registerModelOverrides)
		.model((c, p) -> ClipboardOverrides.addOverrideModels(c, p))
		.build()
		.register();





	public static void register() {
	}

}
