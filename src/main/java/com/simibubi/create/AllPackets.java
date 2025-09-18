package com.simibubi.create;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.simibubi.create.content.contraptions.sync.ClientMotionPacket;
import com.simibubi.create.content.equipment.blueprint.BlueprintAssignCompleteRecipePacket;
import com.simibubi.create.content.equipment.clipboard.ClipboardEditPacket;
import com.simibubi.create.content.schematics.cannon.ConfigureSchematicannonPacket;
import com.simibubi.create.content.schematics.packet.InstantSchematicPacket;
import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import com.simibubi.create.content.schematics.packet.SchematicSyncPacket;
import com.simibubi.create.content.schematics.packet.SchematicUploadPacket;
import com.simibubi.create.foundation.gui.menu.ClearMenuPacket;
import com.simibubi.create.foundation.gui.menu.GhostItemSubmitPacket;
import com.simibubi.create.foundation.networking.ISyncPersistentData;
import com.simibubi.create.foundation.networking.LeftClickPacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import com.simibubi.create.foundation.utility.ServerSpeedProvider;
import com.simibubi.create.infrastructure.command.SimpleCreateActions;

import net.createmod.catnip.net.ClientboundSimpleActionPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;
import net.minecraftforge.network.simple.SimpleChannel;

public enum AllPackets {

	// Client to Server
	CONFIGURE_SCHEMATICANNON(ConfigureSchematicannonPacket.class, ConfigureSchematicannonPacket::new, PLAY_TO_SERVER),
	PLACE_SCHEMATIC(SchematicPlacePacket.class, SchematicPlacePacket::new, PLAY_TO_SERVER),
	UPLOAD_SCHEMATIC(SchematicUploadPacket.class, SchematicUploadPacket::new, PLAY_TO_SERVER),

	CLEAR_CONTAINER(ClearMenuPacket.class, ClearMenuPacket::new, PLAY_TO_SERVER),

	BLUEPRINT_COMPLETE_RECIPE(BlueprintAssignCompleteRecipePacket.class, BlueprintAssignCompleteRecipePacket::new,
		PLAY_TO_SERVER),

	CLIENT_MOTION(ClientMotionPacket.class, ClientMotionPacket::new, PLAY_TO_SERVER),


	INSTANT_SCHEMATIC(InstantSchematicPacket.class, InstantSchematicPacket::new, PLAY_TO_SERVER),
	SYNC_SCHEMATIC(SchematicSyncPacket.class, SchematicSyncPacket::new, PLAY_TO_SERVER),
	LEFT_CLICK(LeftClickPacket.class, LeftClickPacket::new, PLAY_TO_SERVER),


	SUBMIT_GHOST_ITEM(GhostItemSubmitPacket.class, GhostItemSubmitPacket::new, PLAY_TO_SERVER),


	CLIPBOARD_EDIT(ClipboardEditPacket.class, ClipboardEditPacket::new, PLAY_TO_SERVER),



	// Server to Client

	SERVER_SPEED(ServerSpeedProvider.Packet.class, ServerSpeedProvider.Packet::new, PLAY_TO_CLIENT),


	PERSISTENT_DATA(ISyncPersistentData.PersistentDataPacket.class, ISyncPersistentData.PersistentDataPacket::new,
		PLAY_TO_CLIENT);



	static {
		ClientboundSimpleActionPacket.addAction("rainbowDebug", () -> SimpleCreateActions::rainbowDebug);
		ClientboundSimpleActionPacket.addAction("overlayReset", () -> SimpleCreateActions::overlayReset);
		ClientboundSimpleActionPacket.addAction("overlayScreen", () -> SimpleCreateActions::overlayScreen);
		ClientboundSimpleActionPacket.addAction("experimentalLighting", () -> SimpleCreateActions::experimentalLighting);
		ClientboundSimpleActionPacket.addAction("fabulousWarning", () -> SimpleCreateActions::fabulousWarning);
		ClientboundSimpleActionPacket.addAction("zoomMultiplier", () -> SimpleCreateActions::zoomMultiplier);
		ClientboundSimpleActionPacket.addAction("camAngleYawTarget", () -> value -> SimpleCreateActions.camAngleTarget(value, true));
		ClientboundSimpleActionPacket.addAction("camAnglePitchTarget", () -> value -> SimpleCreateActions.camAngleTarget(value, false));
		ClientboundSimpleActionPacket.addAction("camAngleFunction", () -> SimpleCreateActions::camAngleFunction);
	}

	public static final ResourceLocation CHANNEL_NAME = Create.asResource("main");
	public static final int NETWORK_VERSION = 3;
	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
	private static SimpleChannel channel;

	private final PacketType<?> packetType;

	<T extends SimplePacketBase> AllPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
											NetworkDirection direction) {
		packetType = new PacketType<>(type, factory, direction);
	}

	public static void registerPackets() {
		channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
			.serverAcceptedVersions(NETWORK_VERSION_STR::equals)
			.clientAcceptedVersions(NETWORK_VERSION_STR::equals)
			.networkProtocolVersion(() -> NETWORK_VERSION_STR)
			.simpleChannel();

		for (AllPackets packet : values())
			packet.packetType.register();
	}

	public static SimpleChannel getChannel() {
		return channel;
	}

	public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
		getChannel().send(
			PacketDistributor.NEAR.with(TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), range, world.dimension())),
			message);
	}

	private static class PacketType<T extends SimplePacketBase> {
		private static int index = 0;

		private final BiConsumer<T, FriendlyByteBuf> encoder;
		private final Function<FriendlyByteBuf, T> decoder;
		private final BiConsumer<T, Supplier<Context>> handler;
		private final Class<T> type;
		private final NetworkDirection direction;

		private PacketType(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
			encoder = T::write;
			decoder = factory;
			handler = (packet, contextSupplier) -> {
				Context context = contextSupplier.get();
				if (packet.handle(context)) {
					context.setPacketHandled(true);
				}
			};
			this.type = type;
			this.direction = direction;
		}

		private void register() {
			getChannel().messageBuilder(type, index++, direction)
				.encoder(encoder)
				.decoder(decoder)
				.consumerNetworkThread(handler)
				.add();
		}
	}

}
