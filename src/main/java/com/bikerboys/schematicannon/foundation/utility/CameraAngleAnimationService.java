package com.bikerboys.schematicannon.foundation.utility;

import net.createmod.catnip.animation.LerpedFloat;
import net.minecraft.client.Minecraft;

public class CameraAngleAnimationService {

	private static final LerpedFloat yRotation = LerpedFloat.angular().startWithValue(0);
	private static final LerpedFloat xRotation = LerpedFloat.angular().startWithValue(0);

	private static Mode animationMode = Mode.LINEAR;
	private static float animationSpeed = -1;

	public static void tick() {

		yRotation.tickChaser();
		xRotation.tickChaser();

		if (Minecraft.getInstance().player != null) {
			if (!yRotation.settled())
				Minecraft.getInstance().player.setYRot(yRotation.getValue(1));

			if (!xRotation.settled())
				Minecraft.getInstance().player.setXRot(xRotation.getValue(1));
		}
	}

	public static boolean isYawAnimating() {
		return !yRotation.settled();
	}

	public static boolean isPitchAnimating() {
		return !xRotation.settled();
	}

	public static float getYaw(float partialTicks) {
		return yRotation.getValue(partialTicks);
	}

	public static float getPitch(float partialTicks) {
		return xRotation.getValue(partialTicks);
	}

	public enum Mode {
		LINEAR,
		EXPONENTIAL
	}
}
