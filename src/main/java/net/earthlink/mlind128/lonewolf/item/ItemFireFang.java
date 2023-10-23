package net.earthlink.mlind128.lonewolf.item;

import net.earthlink.mlind128.lonewolf.model.Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ByteTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemFireFang extends Item {

	public static final String TAG_USED = "used";

	public ItemFireFang() {
		super(new Properties().stacksTo(1));
	}

	@Override
	public Component getName(ItemStack stack) {
		return Component.literal("Fire Fang");
	}

	@Override
	public void appendHoverText(ItemStack item, @Nullable Level world, List<Component> components, TooltipFlag flag) {
		components.add(Component.literal(isUsed(item) ? "Used" : "ready"));
	}

	@Override
	public InteractionResult useOn(UseOnContext event) {

		Player player = event.getPlayer();
		Level level = event.getLevel();
		BlockPos position = event.getClickedPos();


		if (!level.isClientSide() && event.getHand() == InteractionHand.MAIN_HAND) {
			BlockState state = level.getBlockState(position);

			if (state.is(Tags.TO_LAVA)) {
				level.setBlockAndUpdate(position, Blocks.LAVA.defaultBlockState());

				ServerPlayer serverPlayer = (ServerPlayer) player;

				if (serverPlayer.gameMode.isSurvival())
					player.getItemInHand(event.getHand()).shrink(1);
			}
		}

		return InteractionResult.CONSUME;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack item = player.getItemInHand(hand);

		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

			if (isUsed(item)) {
				player.sendSystemMessage(Component.literal("You have already used this fire fang!"));

				return InteractionResultHolder.fail(item);
			}

			Fireball fireball = new LargeFireball(level, player, 0, 0, 0, 2);
			fireball.shootFromRotation(player, player.getXRot(), player.getYRot(),
					1.0F, 2F, 0.5F);

			level.addFreshEntity(fireball);

			item.addTagElement(TAG_USED, ByteTag.ONE);
		}

		return InteractionResultHolder.consume(item);
	}

	private boolean isUsed(ItemStack item) {
		return item.hasTag() && item.getTag().contains(TAG_USED);
	}
}
