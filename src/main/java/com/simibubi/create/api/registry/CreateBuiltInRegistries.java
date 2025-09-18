package com.simibubi.create.api.registry;

import org.jetbrains.annotations.ApiStatus;

import com.mojang.serialization.Lifecycle;
import com.simibubi.create.foundation.mixin.accessor.BuiltInRegistriesAccessor;

import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.ResourceKey;

/**
 * Static registries added by Create.
 *
 * @see CreateRegistries
 */
public class CreateBuiltInRegistries {



	@SuppressWarnings("unchecked")
	private static <T> Registry<T> register(ResourceKey<Registry<T>> key, WritableRegistry<T> registry) {
		BuiltInRegistriesAccessor.create$getWRITABLE_REGISTRY().register(
			(ResourceKey<WritableRegistry<?>>) (Object) key, registry, Lifecycle.stable()
		);
		return registry;
	}

	@ApiStatus.Internal
	public static void init() {
		// make sure the class is loaded.
		// this method is called at the tail of BuiltInRegistries, injected by BuiltInRegistriesMixin.
	}
}
