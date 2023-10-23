package net.earthlink.mlind128.lonewolf.item;

import net.earthlink.mlind128.lonewolf.model.ThirstProvider;
import net.earthlink.mlind128.lonewolf.network.PacketToClientSyncThirst;
import net.earthlink.mlind128.lonewolf.network.Packets;
import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemWaterBottle extends Item {

	public ItemWaterBottle() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity entity) {
		Player player = entity instanceof Player ? (Player) entity : null;

		if (player instanceof ServerPlayer) {
			CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, pStack);

			player.getCapability(ThirstProvider.CAPABILITY).ifPresent(thirst -> {
				thirst.setThirst(thirst.getThirst() + 5);
				Packets.sendToPlayer(new PacketToClientSyncThirst(thirst.getThirst()), (ServerPlayer) player);
				Common.tell(player, "[Thirst] Your thirst is now " + thirst.getThirst());
			});

			//Packets.sendToServer(new PacketToServerIncreaseThirse());
		}

		if (player != null && !player.getAbilities().instabuild)
			pStack.shrink(1);

		if (player == null || !player.getAbilities().instabuild) {
			if (pStack.isEmpty())
				return new ItemStack(Items.GLASS_BOTTLE);

			if (player != null)
				player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
		}

		entity.gameEvent(GameEvent.DRINK);
		return pStack;
	}

	@Override
	public int getUseDuration(ItemStack pStack) {
		return 32;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack pStack) {
		return UseAnim.DRINK;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
		return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
	}
}
