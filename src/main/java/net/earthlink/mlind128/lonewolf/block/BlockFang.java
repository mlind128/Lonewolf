package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class BlockFang extends Block {

	public BlockFang() {
		super(BlockBehaviour.Properties.copy(Blocks.ACACIA_PLANKS));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter pLevel, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("Very hard and sharp"));
	}

	@Override
	public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
		int min = 3;
		int max = 10;
		Random random = new Random();

		return min + random.nextInt(max - min + 1);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(CustomItems.FANG.get(), 9));
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

		System.out.println("client: " + level.isClientSide() + "hand: " + hand);

		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

			boolean sneaking = player.isShiftKeyDown();
			Holder.Reference<SoundEvent> sound = sneaking ? SoundEvents.NOTE_BLOCK_BASS : SoundEvents.NOTE_BLOCK_FLUTE;

			level.playSound(null, pos, sound.get(), SoundSource.BLOCKS, 1F, 1F);

			BlockPos spawnLocation = level.getSharedSpawnPos();

			player.teleportTo(
					spawnLocation.getX(),
					spawnLocation.getY(),
					spawnLocation.getZ());

			player.sendSystemMessage(Component.literal("[Waypoint] Teleported to spawn."));

			return InteractionResult.SUCCESS;
		}

		return super.use(state, level, pos, player, hand, hit);
	}
}

