package com.simibubi.create.api.registry;

import com.simibubi.create.Create;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Keys for registries added by Create.
 *
 * @see CreateBuiltInRegistries
 */
public class CreateRegistries {


	private static <T> ResourceKey<Registry<T>> key(String name) {
		return ResourceKey.createRegistryKey(Create.asResource(name));
	}
}
