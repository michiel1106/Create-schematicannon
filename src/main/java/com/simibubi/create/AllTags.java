package com.simibubi.create;

import static com.simibubi.create.AllTags.NameSpace.MOD;

import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.Nullable;

import net.createmod.catnip.lang.Lang;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class AllTags {
	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
		return TagKey.create(registry.key(), id);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static <T> TagKey<T> commonTag(Registry<T> registry, String path) {
		return optionalTag(registry, ResourceLocation.fromNamespaceAndPath("c", path));
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Block> commonBlockTag(String path) {
		return commonTag(BuiltInRegistries.BLOCK, path);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Item> commonItemTag(String path) {
		return commonTag(BuiltInRegistries.ITEM, path);
	}

	@ScheduledForRemoval(inVersion = "1.21.1+ Port")
	@Deprecated(since = "6.0.7", forRemoval = true)
	public static TagKey<Fluid> commonFluidTag(String path) {
		return commonTag(BuiltInRegistries.FLUID, path);
	}

	public enum NameSpace {
		MOD(Create.ID);
		public final String id;

		NameSpace(String id) {
			this.id = id;
		}

		public ResourceLocation id(String path) {
			return ResourceLocation.fromNamespaceAndPath(this.id, path);
		}

		public ResourceLocation id(Enum<?> entry, @Nullable String pathOverride) {
			return this.id(pathOverride != null ? pathOverride : Lang.asId(entry.name()));
		}
	}

	public enum AllBlockTags {
		SAFE_NBT
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

	/**
	 */
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

	public enum AllFluidTags {
;
		public final TagKey<Fluid> tag;

		AllFluidTags() {
			this(MOD);
		}

		AllFluidTags(NameSpace namespace) {
			this(namespace, null);
		}

		AllFluidTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.FLUID, namespace.id(this, pathOverride));
		}

		@SuppressWarnings("deprecation")
		public boolean matches(Fluid fluid) {
			return fluid.is(tag);
		}

		public boolean matches(FluidState state) {
			return state.is(tag);
		}
	}

	public enum AllEntityTags {
		BLAZE_BURNER_CAPTURABLE,
		IGNORE_SEAT;

		public final TagKey<EntityType<?>> tag;

		AllEntityTags() {
			this(MOD);
		}

		AllEntityTags(NameSpace namespace) {
			this(namespace, null);
		}

		AllEntityTags(NameSpace namespace, @Nullable String pathOverride) {
			this.tag = TagKey.create(Registries.ENTITY_TYPE, namespace.id(this, pathOverride));
		}

		public boolean matches(EntityType<?> type) {
			return type.is(tag);
		}

		public boolean matches(Entity entity) {
			return matches(entity.getType());
		}
	}







}
