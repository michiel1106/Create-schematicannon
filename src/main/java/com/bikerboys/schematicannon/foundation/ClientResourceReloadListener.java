package com.bikerboys.schematicannon.foundation;

import com.bikerboys.schematicannon.SchematicannonClient;


import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class ClientResourceReloadListener implements ResourceManagerReloadListener {

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		SchematicannonClient.invalidateRenderers();
	}

}
