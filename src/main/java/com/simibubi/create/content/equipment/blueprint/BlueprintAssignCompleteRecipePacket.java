package com.simibubi.create.content.equipment.blueprint;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.network.NetworkEvent.Context;

public class BlueprintAssignCompleteRecipePacket extends SimplePacketBase {

	private final ResourceLocation recipeID;

	public BlueprintAssignCompleteRecipePacket(ResourceLocation recipeID) {
		this.recipeID = recipeID;
	}

	public BlueprintAssignCompleteRecipePacket(FriendlyByteBuf buffer) {
		recipeID = buffer.readResourceLocation();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(recipeID);
	}

	@Override
	public boolean handle(Context context) {
		return false;
	}


}
