package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.blockentity.CustomBlockEntities;
import net.earthlink.mlind128.lonewolf.blockentity.LightningBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BlockLightning extends BaseEntityBlock {
	public BlockLightning(Properties pProperties) {
		super(pProperties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new LightningBlockEntity(pos, state);
	}

	@Override
	public RenderShape getRenderShape(BlockState pState) {
		return RenderShape.MODEL;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
			LightningBlockEntity lightningBlockEntity = (LightningBlockEntity) level.getBlockEntity(pos);

			lightningBlockEntity.onUse((ServerPlayer) player, level, pos, state);
		}

		return super.use(state, level, pos, player, hand, hit);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return level.isClientSide() ? null : createTickerHelper(type, CustomBlockEntities.LIGHTNING.get(), new BlockEntityTicker<>() {
			@Override
			public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
				((LightningBlockEntity) blockEntity).tick(level, pos, state);
			}
		});
	}
}
