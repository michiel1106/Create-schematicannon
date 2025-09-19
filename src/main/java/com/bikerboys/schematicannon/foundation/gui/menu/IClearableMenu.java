package com.bikerboys.schematicannon.foundation.gui.menu;

import com.bikerboys.schematicannon.AllPackets;

public interface IClearableMenu {

	default void sendClearPacket() {
		AllPackets.getChannel().sendToServer(new ClearMenuPacket());
	}

	void clearContents();

}
