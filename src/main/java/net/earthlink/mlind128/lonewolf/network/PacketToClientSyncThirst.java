package net.earthlink.mlind128.lonewolf.network;

import net.earthlink.mlind128.lonewolf.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PacketToClientSyncThirst implements Packet {

	private int thirst;

	public PacketToClientSyncThirst() {
	}

	public PacketToClientSyncThirst(int thirst) {
		this.thirst = thirst;
	}

	@Override
	public void read(FriendlyByteBuf buf) {
		thirst = buf.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(thirst);
	}

	@Override
	public void handle(ServerPlayer player) {
		ClientPlayerData.thirst = this.thirst;
	}
}
