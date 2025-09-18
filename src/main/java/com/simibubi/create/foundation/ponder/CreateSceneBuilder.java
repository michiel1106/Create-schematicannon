package com.simibubi.create.foundation.ponder;

import java.util.function.Supplier;


import com.simibubi.create.foundation.ponder.element.ExpandedParrotElement;

import net.createmod.catnip.data.FunctionalHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.ParrotElement;
import net.createmod.ponder.api.element.ParrotPose;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.PonderSceneBuilder;
import net.createmod.ponder.foundation.element.ElementLinkImpl;
import net.createmod.ponder.foundation.instruction.CreateParrotInstruction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class CreateSceneBuilder extends PonderSceneBuilder {

	private final SpecialInstructions special;

	public CreateSceneBuilder(SceneBuilder baseSceneBuilder) {
		this(baseSceneBuilder.getScene());
	}

	private CreateSceneBuilder(PonderScene ponderScene) {
		super(ponderScene);
		special = new SpecialInstructions();
	}



	public SpecialInstructions special() {
		return special;
	}



	public class SpecialInstructions extends PonderSpecialInstructions {

		@Override
		public ElementLink<ParrotElement> createBirb(Vec3 location, Supplier<? extends ParrotPose> pose) {
			ElementLink<ParrotElement> link = new ElementLinkImpl<>(ParrotElement.class);
			ParrotElement parrot = ExpandedParrotElement.create(location, pose);
			addInstruction(new CreateParrotInstruction(10, Direction.DOWN, parrot));
			addInstruction(scene -> scene.linkElement(parrot, link));
			return link;
		}

		public ElementLink<ParrotElement> birbOnTurntable(BlockPos pos) {
			return createBirb(VecHelper.getCenterOf(pos), () -> new ParrotSpinOnComponentPose(pos));
		}

		public ElementLink<ParrotElement> birbOnSpinnyShaft(BlockPos pos) {
			return createBirb(VecHelper.getCenterOf(pos)
				.add(0, 0.5, 0), () -> new ParrotSpinOnComponentPose(pos));
		}

		public void conductorBirb(ElementLink<ParrotElement> birb, boolean conductor) {
			addInstruction(scene -> scene.resolveOptional(birb)
				.map(FunctionalHelper.filterAndCast(ExpandedParrotElement.class))
				.ifPresent(expandedBirb -> expandedBirb.setConductor(conductor)));
		}

		public static class ParrotSpinOnComponentPose extends ParrotPose {
			private final BlockPos componentPos;

			public ParrotSpinOnComponentPose(BlockPos componentPos) {
				this.componentPos = componentPos;
			}

			@Override
			public void tick(PonderScene scene, Parrot entity, Vec3 location) {
				BlockEntity blockEntity = scene.getWorld().getBlockEntity(componentPos);

				entity.yRotO = entity.getYRot();
			}
		}
	}

}
