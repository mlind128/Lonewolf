package net.earthlink.mlind128.lonewolf.util;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleEntityBlock extends BaseEntityBlock {

	protected SimpleEntityBlock(Properties properties) {
		super(properties);
	}

	/**
	 * Return your block entity type here
	 *
	 * @return
	 */
	protected abstract RegistryObject<BlockEntityType> getBlockEntityType();

	@Override
	public final RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (!level.isClientSide()) {
			SimpleBlockEntity machine = (SimpleBlockEntity) level.getBlockEntity(pos);
			machine.showTo((ServerPlayer) player, pos);
		}
		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@NotNull
	@Override
	public final BlockEntityTicker getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType type) {
		return level.isClientSide() ? null :
				createTickerHelper(type, this.getBlockEntityType().get(), new BlockEntityTicker<>() {
					@Override
					public void tick(Level level, BlockPos pos, BlockState state, BlockEntity tileEntity) {
						try {
							((SimpleBlockEntity) tileEntity).onTick((ServerLevel) level, pos, state);
						} catch (Throwable t) {
							t.printStackTrace();
						}
					}
				});
	}

	@Override
	public final void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean moving) {
		if (state.getBlock() != newState.getBlock()) {
			SimpleBlockEntity machine = (SimpleBlockEntity) level.getBlockEntity(pos);
			machine.dropContent();
		}
		super.onRemove(state, level, pos, newState, moving);
	}
}
