package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockCustomLeaves extends LeavesBlock {

	private final Random random = new Random();

	public BlockCustomLeaves() {
		super(Properties.copy(Blocks.OAK_LEAVES));
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
		List<ItemStack> drops = new ArrayList<>();

		if (random.nextInt(10) == 0)
			drops.add(new ItemStack(CustomBlocks.MAPLE_SAPLING.get()));
		
		return drops;
	}

	public static int getColor(BlockState state, BlockAndTintGetter level, BlockPos blockPos, int index) {
		if (level == null || blockPos == null)
			return FoliageColor.getDefaultColor();

		return BiomeColors.getAverageFoliageColor(level, blockPos);
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 150;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 40;
	}
}
