package com.bikerboys.schematicannon.foundation.mixin;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.bikerboys.schematicannon.Schematicannon;
import com.bikerboys.schematicannon.api.registry.CreateBuiltInRegistries;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@Mixin(BuiltInRegistries.class)
public class BuiltInRegistriesMixin {
	static {
		CreateBuiltInRegistries.init();
	}

	@WrapOperation(method = "validate", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;forEach(Ljava/util/function/Consumer;)V"))
	private static <T extends Registry<?>> void create$ourRegistriesAreNotEmpty(Registry<T> instance, Consumer<T> consumer, Operation<Void> original) {
		Consumer<T> callback = (t) -> {
			if (!t.key().location().getNamespace().equals(Schematicannon.ID))
				consumer.accept(t);
		};

		original.call(instance, callback);
	}
}
