package com.simibubi.create;

import java.util.List;
import java.util.function.UnaryOperator;

import org.jetbrains.annotations.ApiStatus.Internal;

import com.mojang.serialization.Codec;
import com.simibubi.create.content.equipment.clipboard.ClipboardEntry;
import com.simibubi.create.content.equipment.clipboard.ClipboardOverrides.ClipboardType;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity.SchematicannonOptions;

import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AllDataComponents {
	private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Create.ID);

	public static final DataComponentType<ClipboardType> CLIPBOARD_TYPE = register(
			"clipboard_type",
			builder -> builder.persistent(ClipboardType.CODEC).networkSynchronized(ClipboardType.STREAM_CODEC)
	);

	public static final DataComponentType<List<List<ClipboardEntry>>> CLIPBOARD_PAGES = register(
			"clipboard_pages",
			builder -> builder.persistent(ClipboardEntry.CODEC.listOf().listOf()).networkSynchronized(CatnipStreamCodecBuilders.list(CatnipStreamCodecBuilders.list(ClipboardEntry.STREAM_CODEC)))
	);

	public static final DataComponentType<Unit> CLIPBOARD_READ_ONLY = register(
			"clipboard_read_only",
			builder -> builder.persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
	);

	public static final DataComponentType<CompoundTag> CLIPBOARD_COPIED_VALUES = register(
			"clipboard_copied_values",
			builder -> builder.persistent(CompoundTag.CODEC).networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
	);

	public static final DataComponentType<Integer> CLIPBOARD_PREVIOUSLY_OPENED_PAGE = register(
			"clipboard_previously_opened_page",
			builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
	);

	public static final DataComponentType<Boolean> SCHEMATIC_DEPLOYED = register(
			"schematic_deployed",
			builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL)
	);

	public static final DataComponentType<String> SCHEMATIC_OWNER = register(
			"schematic_owner",
			builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8)
	);

	public static final DataComponentType<String> SCHEMATIC_FILE = register(
			"schematic_file",
			builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8)
	);

	public static final DataComponentType<BlockPos> SCHEMATIC_ANCHOR = register(
			"schematic_anchor",
			builder -> builder.persistent(BlockPos.CODEC).networkSynchronized(BlockPos.STREAM_CODEC)
	);

	public static final DataComponentType<Rotation> SCHEMATIC_ROTATION = register(
			"schematic_rotation",
			builder -> builder.persistent(Rotation.CODEC).networkSynchronized(CatnipStreamCodecs.ROTATION)
	);

	public static final DataComponentType<Mirror> SCHEMATIC_MIRROR = register(
			"schematic_mirror",
			builder -> builder.persistent(Mirror.CODEC).networkSynchronized(CatnipStreamCodecs.MIRROR)
	);

	public static final DataComponentType<Vec3i> SCHEMATIC_BOUNDS = register(
			"schematic_bounds",
			builder -> builder.persistent(Vec3i.CODEC).networkSynchronized(CatnipStreamCodecs.VEC3I)
	);

	public static final DataComponentType<Integer> SCHEMATIC_HASH = register(
			"schematic_hash",
			builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
	);


	public static final DataComponentType<SchematicannonOptions> SCHEMATICANNON_OPTIONS = register(
			"schematicannon_options",
			builder -> builder.persistent(SchematicannonOptions.CODEC).networkSynchronized(SchematicannonOptions.STREAM_CODEC)
	);


	private static <T> DataComponentType<T> register(String name, UnaryOperator<Builder<T>> builder) {
		DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
		DATA_COMPONENTS.register(name, () -> type);
		return type;
	}

	@Internal
	public static void register(IEventBus modEventBus) {
		DATA_COMPONENTS.register(modEventBus);
	}
}
