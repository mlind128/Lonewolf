package net.earthlink.mlind128.lonewolf.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;
import java.util.function.Predicate;

public final class HomeCommand {

	// /home <set/tp>

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("home");

		builder.requires(new Predicate<>() {
			@Override
			public boolean test(CommandSourceStack stack) {
				return stack.hasPermission(2);
			}
		});

		for (String argument : List.of("set", "tp"))
			builder
					.then(Commands.literal(argument)
							.executes((context) -> {
								return runCommand(context.getSource(), argument);
							}));

		// /home

		builder.executes((context) -> runCommand(context.getSource(), null));

		dispatcher.register(builder);
	}

	public static int runCommand(CommandSourceStack context, String argument) throws CommandSyntaxException {
		ServerPlayer player = context.getPlayer();
		MinecraftServer server = context.getServer();
		CompoundTag tag = player.getPersistentData();

		int[] homeLocation = tag.getIntArray("lonewolf.home_pos");
		String levelName = tag.getString("lonewolf.home_level");

		boolean isSet = homeLocation.length != 0;

		if ("tp".equals(argument)) {

			if (isSet) {
				ServerLevel savedLevel = null;

				for (ServerLevel otherLevel : server.getAllLevels()) {
					if (levelName.equals(otherLevel.dimension().registry() + "/" + otherLevel.dimension().location()))
						savedLevel = otherLevel;
				}

				if (savedLevel == null) {
					Common.tell(player, "[Home] Your home location is in a dimension that no longer exists.");

					return 0;
				}

				player.teleportTo(savedLevel, homeLocation[0], homeLocation[1], homeLocation[2], 0, 0);
				Common.tell(player, "[Home] You have been teleported to your home location.");

			} else
				Common.tell(player, "[Home] No home location has been set.");

		} else if ("set".equals(argument)) {

			ServerLevel level = player.serverLevel();
			BlockPos pos = player.blockPosition();

			tag.putString("lonewolf.home_level",
					level.dimension().registry() + "/" + level.dimension().location());

			tag.putIntArray("lonewolf.home_pos", new int[]{pos.getX(), pos.getY(), pos.getZ()});

			Common.tell(player, "[home] New home location set.");

		} else {
			if (isSet)
				Common.tell(player, "[Home] Your home location is set to " + levelName + " x:" + homeLocation[0] + " y:" + homeLocation[1] + " z:" + homeLocation[2]);
			else
				Common.tell(player, "[Home] No home has been set. Use '/home set' to set it.");

		}

		return 0;

	}
}
