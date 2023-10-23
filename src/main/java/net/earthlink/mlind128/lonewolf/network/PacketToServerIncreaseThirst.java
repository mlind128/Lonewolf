package net.earthlink.mlind128.lonewolf.network;

import net.earthlink.mlind128.lonewolf.model.ThirstProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class PacketToServerIncreaseThirst implements Packet {

	private int thirst;

	public PacketToServerIncreaseThirst() {
	}

	public PacketToServerIncreaseThirst(int thirst) {
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
		player.getCapability(ThirstProvider.CAPABILITY).ifPresent(thirst -> {
			thirst.setThirst(thirst.getThirst() + 5);
		});
	}
}
