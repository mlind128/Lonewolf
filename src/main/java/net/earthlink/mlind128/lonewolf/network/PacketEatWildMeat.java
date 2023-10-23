package net.earthlink.mlind128.lonewolf.network;

import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

public class PacketEatWildMeat implements Packet {

	private int keyNumber;

	public PacketEatWildMeat() {
	}

	public PacketEatWildMeat(int keyNumber) {
		this.keyNumber = keyNumber;
	}

	@Override
	public void read(FriendlyByteBuf buf) {
		keyNumber = buf.readInt();
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeInt(keyNumber);
	}

	@Override
	public void handle(ServerPlayer player) {
		ServerLevel level = player.serverLevel();
		ItemStack hand = player.getMainHandItem();

		if (hand.getItem() == CustomItems.WILD_MEAT.get()) {
			Common.tell(player, "[PvP] Restoring health from a wild meat. (Pressed: " + keyNumber + ")");

			if (player.getHealth() < player.getMaxHealth()) {
				player.heal(2);
				hand.shrink(1);

				level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_EAT, SoundSource.PLAYERS,
						1, level.random.nextFloat() * 0.1F + 0.9F);
			}
		} else
			Common.tell(player, "[PvP] You are not holding a wild meat!");
	}
}
