package com.bikerboys.schematicannon.foundation.data;


import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.bikerboys.schematicannon.foundation.item.TooltipModifier;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder.BlockEntityFactory;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;

public class SchematicannonRegistrate extends AbstractRegistrate<SchematicannonRegistrate> {
	private static final Map<RegistryEntry<?>, RegistryObject<CreativeModeTab>> TAB_LOOKUP = Collections.synchronizedMap(new IdentityHashMap<>());

	@Nullable
	protected Function<Item, TooltipModifier> currentTooltipModifierFactory;
	@Nullable
	protected RegistryObject<CreativeModeTab> currentTab;

	protected SchematicannonRegistrate(String modid) {
		super(modid);
	}

	public static SchematicannonRegistrate create(String modid) {
		SchematicannonRegistrate registrate = new SchematicannonRegistrate(modid);
		return registrate;
	}



	public SchematicannonRegistrate setTooltipModifierFactory(@Nullable Function<Item, TooltipModifier> factory) {
		currentTooltipModifierFactory = factory;
		return self();
	}



	@Nullable
	public SchematicannonRegistrate setCreativeTab(RegistryObject<CreativeModeTab> tab) {
		currentTab = tab;
		return self();
	}



	@Override
	public SchematicannonRegistrate registerEventListeners(IEventBus bus) {
		return super.registerEventListeners(bus);
	}

	@Override
	protected <R, T extends R> RegistryEntry<T> accept(String name, ResourceKey<? extends Registry<R>> type,
													   Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator,
													   NonNullFunction<RegistryObject<T>, ? extends RegistryEntry<T>> entryFactory) {
		RegistryEntry<T> entry = super.accept(name, type, builder, creator, entryFactory);
		if (type.equals(Registries.ITEM) && currentTooltipModifierFactory != null) {
			// grab the factory here for the lambda, it can change between now and registration
			Function<Item, TooltipModifier> factory = currentTooltipModifierFactory;
			this.addRegisterCallback(name, Registries.ITEM, item -> {
				TooltipModifier modifier = factory.apply(item);
				TooltipModifier.REGISTRY.register(item, modifier);
			});
		}
		if (currentTab != null)
			TAB_LOOKUP.put(entry, currentTab);

		return entry;
	}

	@Override
	public <T extends BlockEntity> CreateBlockEntityBuilder<T, SchematicannonRegistrate> blockEntity(String name,
																									 BlockEntityFactory<T> factory) {
		return blockEntity(self(), name, factory);
	}

	@Override
	public <T extends BlockEntity, P> CreateBlockEntityBuilder<T, P> blockEntity(P parent, String name,
																				 BlockEntityFactory<T> factory) {
		return (CreateBlockEntityBuilder<T, P>) entry(name,
			(callback) -> CreateBlockEntityBuilder.create(this, parent, name, callback, factory));
	}

	@Override
	public <T extends Entity> CreateEntityBuilder<T, SchematicannonRegistrate> entity(String name,
																					  EntityType.EntityFactory<T> factory, MobCategory classification) {
		return this.entity(self(), name, factory, classification);
	}

	@Override
	public <T extends Entity, P> CreateEntityBuilder<T, P> entity(P parent, String name,
																  EntityType.EntityFactory<T> factory, MobCategory classification) {
		return (CreateEntityBuilder<T, P>) this.entry(name, (callback) -> {
			return CreateEntityBuilder.create(this, parent, name, callback, factory, classification);
		});
	}


}
