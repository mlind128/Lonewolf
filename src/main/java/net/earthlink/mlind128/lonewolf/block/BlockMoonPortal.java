package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.world.CustomDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class BlockMoonPortal extends Block {

	public BlockMoonPortal() {
		super(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).noLootTable().noOcclusion());
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (player.canChangeDimensions()) {

			if (player.level() instanceof ServerLevel serverLevel) {
				boolean onMoon = player.level().dimension() == CustomDimensions.MOON;
				ServerLevel targetWorld = serverLevel.getServer().getLevel(onMoon ? Level.OVERWORLD : CustomDimensions.MOON);

				if (targetWorld != null && !player.isPassenger())
					player.changeDimension(targetWorld, new ITeleporter() {

						@Override
						public Entity placeEntity(Entity entity, ServerLevel current, ServerLevel destination, float yaw, Function<Boolean, Entity> repositionEntity) {
							entity = repositionEntity.apply(false);

							BlockPos finalPos = new BlockPos(blockPos.getX(), 50, blockPos.getZ());

							int i = 0;
							while (destination.getBlockState(finalPos).getBlock() != Blocks.AIR &&
									destination.getBlockState(finalPos.above()).getBlock() != Blocks.AIR &&
									!destination.getBlockState(finalPos).canBeReplaced(Fluids.WATER) &&
									!destination.getBlockState(finalPos.above()).canBeReplaced(Fluids.WATER) && i < 30) {

								finalPos = finalPos.above(2);
								i++;
							}

							entity.setPos(finalPos.getX(), finalPos.getY(), finalPos.getZ());

							return entity;
						}
					});
			}

			return InteractionResult.SUCCESS;

		} else
			return InteractionResult.CONSUME;

	}
}
