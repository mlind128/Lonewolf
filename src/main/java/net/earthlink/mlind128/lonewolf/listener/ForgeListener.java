package net.earthlink.mlind128.lonewolf.listener;

import com.mojang.brigadier.CommandDispatcher;
import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.command.HomeCommand;
import net.earthlink.mlind128.lonewolf.model.ThirstProvider;
import net.earthlink.mlind128.lonewolf.model.keys;
import net.earthlink.mlind128.lonewolf.network.PacketEatWildMeat;
import net.earthlink.mlind128.lonewolf.network.PacketToClientSyncThirst;
import net.earthlink.mlind128.lonewolf.network.Packets;
import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

import java.util.Arrays;

public final class ForgeListener {

	@SubscribeEvent
	public void onCommandsRegister(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

		HomeCommand.register(dispatcher);
	}

	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		CompoundTag oldPlayer = event.getOriginal().getPersistentData();
		CompoundTag newPlayer = event.getEntity().getPersistentData();

		if (oldPlayer.getIntArray("lonewolf.home_pos").length != 0)
			newPlayer.putIntArray("lonewolf.home_pos", oldPlayer.getIntArray("lonewolf.home_pos"));

		if (!oldPlayer.getString("lonewolf.home_level").isEmpty())
			newPlayer.putString("lonewolf.home_level", oldPlayer.getString("lonewolf.home_level"));

		System.out.println("@on player clone, copying "
				+ Arrays.toString(oldPlayer.getIntArray("lonawolf.home_pos"))
				+ " add " + oldPlayer.getString("lonewolf.home_level"));

		if (event.isWasDeath())
			event.getOriginal().getCapability(ThirstProvider.CAPABILITY).ifPresent(oldThirst -> {
				event.getEntity().getCapability(ThirstProvider.CAPABILITY).ifPresent(newThirst -> {
					newThirst.setThirst(oldThirst.getThirst());
				});
			});
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.Key event) {

		Player player = Minecraft.getInstance().player;

		if (keys.TEST.consumeClick())
			Packets.sendToServer(new PacketEatWildMeat(event.getKey()));
	}

	@SubscribeEvent
	public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();

		if (entity instanceof Player)
			if (!event.getObject().getCapability(ThirstProvider.CAPABILITY).isPresent())
				event.addCapability(new ResourceLocation(Lonewolf.MOD_ID, "properties"), new ThirstProvider());
	}

	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END)
			event.player.getCapability(ThirstProvider.CAPABILITY).ifPresent(thirst -> {
				if (!event.player.isCreative() && event.player.level().getGameTime() % (20 * 30) == 0) {
					thirst.setThirst(thirst.getThirst() - 1);
					Packets.sendToPlayer(new PacketToClientSyncThirst(thirst.getThirst()), (ServerPlayer) event.player);

					if (thirst.getThirst() == 0)
						event.player.hurt(event.player.damageSources().generic(), 1);

					Common.tell(event.player, "[Thirst] Subtracted thirst. Remaining: "
							+ thirst.getThirst() + ".");
				}
			});
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinLevelEvent event) {
		if (!event.getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer) {
			ServerPlayer player = (ServerPlayer) event.getEntity();

			player.getCapability(ThirstProvider.CAPABILITY).ifPresent(thirst -> {
				Packets.sendToPlayer(new PacketToClientSyncThirst(thirst.getThirst()), player);
			});
		}
	}
}
