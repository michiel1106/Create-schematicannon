package com.simibubi.create.api.registry;

import com.simibubi.create.Create;
import com.simibubi.create.api.contraption.ContraptionType;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.simibubi.create.api.contraption.storage.item.MountedItemStorageType;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.content.logistics.item.filter.attribute.ItemAttributeType;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/**
 * Keys for registries added by Create.
 *
 * @see CreateBuiltInRegistries
 */
public class CreateRegistries {
	public static final ResourceKey<Registry<ArmInteractionPointType>> ARM_INTERACTION_POINT_TYPE = key("arm_interaction_point_type");
	public static final ResourceKey<Registry<FanProcessingType>> FAN_PROCESSING_TYPE = key("fan_processing_type");
	public static final ResourceKey<Registry<ItemAttributeType>> ITEM_ATTRIBUTE_TYPE = key("item_attribute_type");
	public static final ResourceKey<Registry<MountedItemStorageType<?>>> MOUNTED_ITEM_STORAGE_TYPE = key("mounted_item_storage_type");
	public static final ResourceKey<Registry<MountedFluidStorageType<?>>> MOUNTED_FLUID_STORAGE_TYPE = key("mounted_fluid_storage_type");
	public static final ResourceKey<Registry<ContraptionType>> CONTRAPTION_TYPE = key("contraption_type");

	private static <T> ResourceKey<Registry<T>> key(String name) {
		return ResourceKey.createRegistryKey(Create.asResource(name));
	}
}
