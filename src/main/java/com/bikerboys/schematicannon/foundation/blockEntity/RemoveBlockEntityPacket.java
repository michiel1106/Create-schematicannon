package com.bikerboys.schematicannon.foundation.blockEntity;

import com.bikerboys.schematicannon.AllPackets;
import com.bikerboys.schematicannon.foundation.networking.BlockEntityDataPacket;

import io.netty.buffer.ByteBuf;

import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

public class RemoveBlockEntityPacket extends BlockEntityDataPacket<SyncedBlockEntity> {
	public static final StreamCodec<ByteBuf, RemoveBlockEntityPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(
			RemoveBlockEntityPacket::new, packet -> packet.pos
	);

	public RemoveBlockEntityPacket(BlockPos pos) {
		super(pos);
	}

	@Override
	protected void handlePacket(SyncedBlockEntity be) {
		if (!be.hasLevel()) {
			be.setRemoved();
			return;
		}

		be.getLevel()
			.removeBlockEntity(pos);
	}

	@Override
	public PacketTypeProvider getTypeProvider() {
		return AllPackets.REMOVE_TE;
	}
}
