package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.blockentity.CustomBlockEntities;
import net.earthlink.mlind128.lonewolf.blockentity.FireFangMachineBlockEntity;
import net.earthlink.mlind128.lonewolf.util.SimpleEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

public class BlockFireFangMachine extends SimpleEntityBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	protected BlockFireFangMachine() {
		super(BlockBehaviour.Properties.copy(Blocks.STONE));

		registerDefaultState(defaultBlockState().setValue(FACING, Direction.SOUTH));
	}

	@Override
	protected RegistryObject<BlockEntityType> getBlockEntityType() {
		return CustomBlockEntities.FIRE_FANG_MACHINE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FireFangMachineBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	@javax.annotation.Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
}
