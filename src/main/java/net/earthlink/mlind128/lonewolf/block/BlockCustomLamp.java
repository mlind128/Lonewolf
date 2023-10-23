package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class BlockCustomLamp extends Block {

	public static final BooleanProperty POWERED = BooleanProperty.create("powered");

	public BlockCustomLamp() {
		super(Properties.copy(Blocks.REDSTONE_LAMP)
				.lightLevel(new ToIntFunction<>() {
					@Override
					public int applyAsInt(BlockState state) {
						return state.getValue(POWERED) ? 15 : 0;
					}
				}));

		RedstoneLampBlock asd;

		registerDefaultState(defaultBlockState().setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos position, Block block, BlockPos pos, boolean isMoving) {
		if (!world.isClientSide()) {
			boolean isPowered = state.getValue(POWERED);

			if (isPowered != world.hasNeighborSignal(position))
				if (isPowered)
					world.scheduleTick(position, this, 4);
				else
					world.setBlock(position, state.cycle(POWERED), 2);
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pPos, RandomSource pRandom) {
		if (state.getValue(POWERED) && !world.hasNeighborSignal(pPos))
			world.setBlock(pPos, state.cycle(POWERED), 2);
	}
}
