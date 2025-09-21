package com.simibubi.create.content.schematics;

import net.createmod.catnip.nbt.NBTHelper;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class SchematicAndQuillItem extends Item {

	public SchematicAndQuillItem(Properties properties) {
		super(properties);
	}

	public static void replaceStructureVoidWithAir(CompoundTag nbt) {
		String air = RegisteredObjectsHelper.getKeyOrThrow(Blocks.AIR)
			.toString();
		String structureVoid = RegisteredObjectsHelper.getKeyOrThrow(Blocks.STRUCTURE_VOID)
			.toString();

		NBTHelper.iterateCompoundList(nbt.getList("palette", 10), c -> {
			if (c.contains("Name") && c.getString("Name")
				.equals(structureVoid)) {
				c.putString("Name", air);
			}
		});
	}



}
