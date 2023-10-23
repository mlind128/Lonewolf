package net.earthlink.mlind128.lonewolf.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public final class Common {

	public static void tell(Player player, String message) {
		player.sendSystemMessage(Component.literal(message));
	}

	public static List<BlockPos> getBlocksInDirection(BlockPos center, Direction direction, int range) {
		List<BlockPos> locations = new ArrayList<>();

		for (int x = -range; x <= +range; x++)
			for (int y = -range; y <= +range; y++) {

				int newX = center.getX();
				int newY = center.getY();
				int newZ = center.getZ();

				switch (direction) {
					case DOWN:
					case UP:
						newX = center.getX() + x;
						newY = center.getY();
						newZ = center.getZ() + y;
						break;
					case NORTH:
					case SOUTH:
						newX = center.getX() + x;
						newY = center.getY() + y;
						newZ = center.getZ();
						break;
					case EAST:
					case WEST:
						newX = center.getX();
						newY = center.getY() + y;
						newZ = center.getZ() + x;
						break;
				}

				locations.add(new BlockPos(newX, newY, newZ));
			}

		return locations;
	}
}
