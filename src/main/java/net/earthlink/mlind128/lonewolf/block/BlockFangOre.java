package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;
import java.util.Random;

public class BlockFangOre extends Block {

	public BlockFangOre() {
		super(Properties.copy(Blocks.DIAMOND_ORE));
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
		return List.of(new ItemStack(CustomItems.FANG.get(), 1));
	}

}

