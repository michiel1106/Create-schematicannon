package com.simibubi.create.content.schematics.cannon;

import java.util.Optional;

import com.simibubi.create.foundation.utility.BlockHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class LaunchedItem {

	public int totalTicks;
	public int ticksRemaining;
	public BlockPos target;
	public ItemStack stack;

	private LaunchedItem(BlockPos start, BlockPos target, ItemStack stack) {
		this(target, stack, ticksForDistance(start, target), ticksForDistance(start, target));
	}

	private static int ticksForDistance(BlockPos start, BlockPos target) {
		return (int) (Math.max(10, Math.sqrt(Math.sqrt(target.distSqr(start))) * 4f));
	}

	LaunchedItem() {}

	private LaunchedItem(BlockPos target, ItemStack stack, int ticksLeft, int total) {
		this.target = target;
		this.stack = stack;
		this.totalTicks = total;
		this.ticksRemaining = ticksLeft;
	}

	public boolean update(Level world) {
		if (ticksRemaining > 0) {
			ticksRemaining--;
			return false;
		}
		if (world.isClientSide)
			return false;

		place(world);
		return true;
	}

	public CompoundTag serializeNBT() {
		CompoundTag c = new CompoundTag();
		c.putInt("TotalTicks", totalTicks);
		c.putInt("TicksLeft", ticksRemaining);
		c.put("Stack", stack.serializeNBT());
		c.put("Target", NbtUtils.writeBlockPos(target));
		return c;
	}

	public static LaunchedItem fromNBT(CompoundTag c, HolderGetter<Block> holderGetter) {
		LaunchedItem launched = c.contains("BlockState") ? new LaunchedItem.ForBlockState() : new LaunchedItem.ForEntity();

		launched.readNBT(c, holderGetter);
		return launched;
	}

	abstract void place(Level world);

	void readNBT(CompoundTag c, HolderGetter<Block> holderGetter) {
		target = NbtUtils.readBlockPos(c.getCompound("Target"));
		ticksRemaining = c.getInt("TicksLeft");
		totalTicks = c.getInt("TotalTicks");
		stack = ItemStack.of(c.getCompound("Stack"));
	}

	public static class ForBlockState extends LaunchedItem {
		public BlockState state;
		public CompoundTag data;

		ForBlockState() {}

		public ForBlockState(BlockPos start, BlockPos target, ItemStack stack, BlockState state, CompoundTag data) {
			super(start, target, stack);
			this.state = state;
			this.data = data;
		}

		@Override
		public CompoundTag serializeNBT() {
			CompoundTag serializeNBT = super.serializeNBT();
			serializeNBT.put("BlockState", NbtUtils.writeBlockState(state));
			if (data != null) {
				data.remove("x");
				data.remove("y");
				data.remove("z");
				data.remove("id");
				serializeNBT.put("Data", data);
			}
			return serializeNBT;
		}

		@Override
		void readNBT(CompoundTag nbt, HolderGetter<Block> holderGetter) {
			super.readNBT(nbt, holderGetter);
			state = NbtUtils.readBlockState(holderGetter, nbt.getCompound("BlockState"));
			if (nbt.contains("Data", Tag.TAG_COMPOUND)) {
				data = nbt.getCompound("Data");
			}
		}

		@Override
		void place(Level world) {
			BlockHelper.placeSchematicBlock(world, state, target, stack, data);
		}

	}



	public static class ForEntity extends LaunchedItem {
		public Entity entity;
		private CompoundTag deferredTag;

		ForEntity() {}

		public ForEntity(BlockPos start, BlockPos target, ItemStack stack, Entity entity) {
			super(start, target, stack);
			this.entity = entity;
		}

		@Override
		public boolean update(Level world) {
			if (deferredTag != null && entity == null) {
				try {
					Optional<Entity> loadEntityUnchecked = EntityType.create(deferredTag, world);
					if (!loadEntityUnchecked.isPresent())
						return true;
					entity = loadEntityUnchecked.get();
				} catch (Exception var3) {
					return true;
				}
				deferredTag = null;
			}
			return super.update(world);
		}

		@Override
		public CompoundTag serializeNBT() {
			CompoundTag serializeNBT = super.serializeNBT();
			if (entity != null)
				serializeNBT.put("Entity", entity.serializeNBT());
			return serializeNBT;
		}

		@Override
		void readNBT(CompoundTag nbt, HolderGetter<Block> holderGetter) {
			super.readNBT(nbt, holderGetter);
			if (nbt.contains("Entity"))
				deferredTag = nbt.getCompound("Entity");
		}

		@Override
		void place(Level world) {
			if (entity != null)
				world.addFreshEntity(entity);
		}

	}

}
