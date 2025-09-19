package com.bikerboys.schematicannon.foundation.gui.menu;

import com.bikerboys.schematicannon.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClearMenuPacket extends SimplePacketBase {

	public ClearMenuPacket() {}

	public ClearMenuPacket(FriendlyByteBuf buffer) {}

	@Override
	public void write(FriendlyByteBuf buffer) {}

	@Override
	public boolean handle(Context context) {
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player == null)
				return;
			if (!(player.containerMenu instanceof IClearableMenu))
				return;
			((IClearableMenu) player.containerMenu).clearContents();
		});
		return true;
	}

}
