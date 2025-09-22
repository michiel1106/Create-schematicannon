package com.bikerboys.schematicannon;

import static net.minecraft.core.Direction.SOUTH;
import static net.minecraft.core.Direction.UP;

import java.util.function.BiFunction;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AllShapes {

	// Independent Shapers
	public static final VoxelShaper
		CASING_12PX = shape(0, 0, 0, 16, 12, 16).forDirectional(),
		CLIPBOARD_FLOOR = shape(3, 0, 1, 13, 1, 15).forHorizontal(SOUTH),
		CLIPBOARD_CEILING = shape(3, 15, 1, 13, 16, 15).forHorizontal(SOUTH),
		CLIPBOARD_WALL = shape(3, 1, 0, 13, 15, 1).forHorizontal(SOUTH)


	;

	// Internally Shared Shapes
	public static final VoxelShape
		SCHEMATICANNON_SHAPE = shape(1, 0, 1, 15, 8, 15).add(0.5, 8, 0.5, 15.5, 11, 15.5)
		.build(),
	TABLE_POLE_SHAPE = shape(4, 0, 4, 12, 2, 12).add(5, 2, 5, 11, 14, 11)
			.build();


	;

	// Static Block Shapes
	public static final VoxelShape

	SCAFFOLD_HALF = shape(0, 8, 0, 16, 16, 16).build(), SCAFFOLD_FULL = shape(SCAFFOLD_HALF).add(0, 0, 0, 2, 16, 2)
		.add(0, 0, 14, 2, 16, 16)
		.add(14, 0, 0, 16, 16, 2)
		.add(14, 0, 14, 16, 16, 16)
		.build();




	;

	// More Shapers
	public static final VoxelShaper



		SCHEMATICS_TABLE = shape(4, 0, 4, 12, 12, 12).add(0, 11, 2, 16, 14, 14)
			.forDirectional(SOUTH);




	private static Builder shape(VoxelShape shape) {
		return new Builder(shape);
	}

	private static Builder shape(double x1, double y1, double z1, double x2, double y2, double z2) {
		return shape(cuboid(x1, y1, z1, x2, y2, z2));
	}

	private static VoxelShape cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Block.box(x1, y1, z1, x2, y2, z2);
	}

	public static class Builder {

		private VoxelShape shape;

		public Builder(VoxelShape shape) {
			this.shape = shape;
		}

		public Builder add(VoxelShape shape) {
			this.shape = Shapes.or(this.shape, shape);
			return this;
		}

		public Builder add(double x1, double y1, double z1, double x2, double y2, double z2) {
			return add(cuboid(x1, y1, z1, x2, y2, z2));
		}

		public Builder erase(double x1, double y1, double z1, double x2, double y2, double z2) {
			this.shape = Shapes.join(shape, cuboid(x1, y1, z1, x2, y2, z2), BooleanOp.ONLY_FIRST);
			return this;
		}

		public VoxelShape build() {
			return shape;
		}

		public VoxelShaper build(BiFunction<VoxelShape, Direction, VoxelShaper> factory, Direction direction) {
			return factory.apply(shape, direction);
		}

		public VoxelShaper build(BiFunction<VoxelShape, Axis, VoxelShaper> factory, Axis axis) {
			return factory.apply(shape, axis);
		}

		public VoxelShaper forDirectional(Direction direction) {
			return build(VoxelShaper::forDirectional, direction);
		}

		public VoxelShaper forAxis() {
			return build(VoxelShaper::forAxis, Axis.Y);
		}

		public VoxelShaper forHorizontalAxis() {
			return build(VoxelShaper::forHorizontalAxis, Axis.Z);
		}

		public VoxelShaper forHorizontal(Direction direction) {
			return build(VoxelShaper::forHorizontal, direction);
		}

		public VoxelShaper forDirectional() {
			return forDirectional(UP);
		}

	}

}
