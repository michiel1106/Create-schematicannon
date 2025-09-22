package com.bikerboys.schematicannon;

import java.util.Locale;

import com.bikerboys.schematicannon.content.equipment.clipboard.ClipboardEditPacket;
import com.bikerboys.schematicannon.content.schematics.cannon.ConfigureSchematicannonPacket;
import com.bikerboys.schematicannon.content.schematics.packet.InstantSchematicPacket;
import com.bikerboys.schematicannon.content.schematics.packet.SchematicPlacePacket;
import com.bikerboys.schematicannon.content.schematics.packet.SchematicSyncPacket;
import com.bikerboys.schematicannon.content.schematics.packet.SchematicUploadPacket;
import com.bikerboys.schematicannon.foundation.blockEntity.RemoveBlockEntityPacket;
import com.bikerboys.schematicannon.foundation.blockEntity.behaviour.ValueSettingsPacket;
import com.bikerboys.schematicannon.foundation.networking.ISyncPersistentData;
import com.bikerboys.schematicannon.foundation.utility.ServerSpeedProvider;

import net.createmod.catnip.net.base.BasePacketPayload;
import net.createmod.catnip.net.base.CatnipPacketRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public enum AllPackets implements BasePacketPayload.PacketTypeProvider {
	// Client to Server
	CONFIGURE_SCHEMATICANNON(ConfigureSchematicannonPacket.class, ConfigureSchematicannonPacket.STREAM_CODEC),
	PLACE_SCHEMATIC(SchematicPlacePacket.class, SchematicPlacePacket.STREAM_CODEC),
	UPLOAD_SCHEMATIC(SchematicUploadPacket.class, SchematicUploadPacket.STREAM_CODEC),
	INSTANT_SCHEMATIC(InstantSchematicPacket.class, InstantSchematicPacket.STREAM_CODEC),
	SYNC_SCHEMATIC(SchematicSyncPacket.class, SchematicSyncPacket.STREAM_CODEC),
	VALUE_SETTINGS(ValueSettingsPacket.class, ValueSettingsPacket.STREAM_CODEC),
	CLIPBOARD_EDIT(ClipboardEditPacket.class, ClipboardEditPacket.STREAM_CODEC),

	REMOVE_TE(RemoveBlockEntityPacket.class, RemoveBlockEntityPacket.STREAM_CODEC),
	// Server to Client
	SERVER_SPEED(ServerSpeedProvider.Packet.class, ServerSpeedProvider.Packet.STREAM_CODEC),
	PERSISTENT_DATA(ISyncPersistentData.PersistentDataPacket.class, ISyncPersistentData.PersistentDataPacket.STREAM_CODEC),
	;




	private final CatnipPacketRegistry.PacketType<?> type;

	<T extends BasePacketPayload> AllPackets(Class<T> clazz, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
		String name = this.name().toLowerCase(Locale.ROOT);
		this.type = new CatnipPacketRegistry.PacketType<>(
				new CustomPacketPayload.Type<>(Schematicannon.asResource(name)),
				clazz, codec
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> getType() {
		return (CustomPacketPayload.Type<T>) this.type.type();
	}

	public static void register() {
		CatnipPacketRegistry packetRegistry = new CatnipPacketRegistry(Schematicannon.ID, CreateBuildInfo.VERSION);
		for (AllPackets packet : AllPackets.values()) {
			packetRegistry.registerPacket(packet.type);
		}
		packetRegistry.registerAllPackets();
	}
}
