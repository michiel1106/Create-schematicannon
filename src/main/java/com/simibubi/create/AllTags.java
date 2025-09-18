package com.simibubi.create;

import static com.simibubi.create.AllTags.NameSpace.FORGE;
import static com.simibubi.create.AllTags.NameSpace.MOD;
import static com.simibubi.create.AllTags.NameSpace.QUARK;
import static com.simibubi.create.AllTags.NameSpace.TIC;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Nullable;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class AllTags {
	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static <T> TagKey<T> optionalTag(IForgeRegistry<T> registry,
											ResourceLocation id) {
		return TagKey.create(registry.getRegistryKey(), id);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static <T> TagKey<T> forgeTag(IForgeRegistry<T> registry, String path) {
		return optionalTag(registry, FORGE.id(path));
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Block> forgeBlockTag(String path) {
		return forgeTag(ForgeRegistries.BLOCKS, path);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Item> forgeItemTag(String path) {
		return forgeTag(ForgeRegistries.ITEMS, path);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Fluid> forgeFluidTag(String path) {
		return forgeTag(ForgeRegistries.FLUIDS, path);
	}

	public enum NameSpace {

		MOD(Create.ID),
		FORGE("forge"),
		TIC("tconstruct"),
		QUARK("quark"),
		GS("galosphere"),
		CURIOS("curios");

		public final String id;

		NameSpace(String id) {
			this.id = id;
		}

		public ResourceLocation id(String path) {
			return new ResourceLocation(this.id, path);
		}

		public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
			return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
		}
	}

	public enum AllBlockTags {

		BRITTLE,
		CASING,
		COPYCAT_ALLOW,
		COPYCAT_DENY,
		FAN_PROCESSING_CATALYSTS_BLASTING(MOD, "fan_processing_catalysts/blasting"),
		FAN_PROCESSING_CATALYSTS_HAUNTING(MOD, "fan_processing_catalysts/haunting"),
		FAN_PROCESSING_CATALYSTS_SMOKING(MOD, "fan_processing_catalysts/smoking"),
		FAN_PROCESSING_CATALYSTS_SPLASHING(MOD, "fan_processing_catalysts/splashing"),
		FAN_TRANSPARENT,
		GIRDABLE_TRACKS,
		MOVABLE_EMPTY_COLLIDER,
		NON_MOVABLE,
		NON_BREAKABLE,
		PASSIVE_BOILER_HEATERS,
		SAFE_NBT,
		SEATS,
		POSTBOXES,
		TABLE_CLOTHS,
		TOOLBOXES,
		TRACKS,
		TREE_ATTACHMENTS,
		VALVE_HANDLES,
		WINDMILL_SAILS,
		WRENCH_PICKUP,
		CHEST_MOUNTED_STORAGE,
		SIMPLE_MOUNTED_STORAGE,
		FALLBACK_MOUNTED_STORAGE_BLACKLIST,
		ROOTS,
		SUGAR_CANE_VARIANTS,
		NON_HARVESTABLE,
		SINGLE_BLOCK_INVENTORIES,
		CARDBOARD_STORAGE_BLOCKS(FORGE, "storage_blocks/cardboard"),
		ANDESITE_ALLOY_STORAGE_BLOCKS(FORGE, "storage_blocks/andesite_alloy"),

		STONE_ORES_IN_GROUND(FORGE, "ores_in_ground/stone"),
		DEEPSLATE_ORES_IN_GROUND(FORGE, "ores_in_ground/deepslate"),

		CORALS,

		RELOCATION_NOT_SUPPORTED(FORGE),

		SLIMY_LOGS(TIC),
		NON_DOUBLE_DOOR(QUARK),

		;

		public final TagKey<Block> tag;

		AllBlockTags() {
			this(MOD);
		}

		AllBlockTags(NameSpace namespace) {
			this(namespace, null);
		}

		AllBlockTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.BLOCK, namespace.id(this, pathOverride));
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Block block) {
			return block.builtInRegistryHolder()
				.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack != null && stack.getItem() instanceof BlockItem blockItem && matches(blockItem.getBlock());
		}

		public boolean matches(BlockState state) {
			return state.is(tag);
		}

	}

	public enum AllItemTags {

		;

		public final TagKey<Item> tag;

		AllItemTags() {
			this(MOD);
		}

		AllItemTags(NameSpace namespace) {
			this(namespace, null);
		}

		AllItemTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.ITEM, namespace.id(this, pathOverride));
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Item item) {
			return item.builtInRegistryHolder()
				.is(tag);
		}

		public boolean matches(ItemStack stack) {
			return stack.is(tag);
		}

	}









}
