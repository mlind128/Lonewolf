package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;

public class BlockRice extends CropBlock {

	private static final int MAX_AGE = 4;
	private static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

	public BlockRice() {
		super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return super.getBaseSeedId();
	}

	@Override
	protected IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}

	@Override
	protected int getBonemealAgeIncrease(Level pLevel) {
		return 1;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
		List<ItemStack> drops = new ArrayList<>();
		int age = state.getValue(AGE);

		if (age == MAX_AGE) {
			drops.add(new ItemStack(CustomItems.RICE.get(), 1 + Lonewolf.RANDOM.nextInt(2)));
		}

		drops.add(new ItemStack(CustomItems.RICE_SEEDS.get()));

		return drops;
	}
}
