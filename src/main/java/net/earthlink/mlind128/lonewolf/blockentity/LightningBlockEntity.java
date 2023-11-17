package net.earthlink.mlind128.lonewolf.blockentity;

import net.earthlink.mlind128.lonewolf.particle.CustomParticles;
import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LightningBlockEntity extends BlockEntity {


	public LightningBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(CustomBlockEntities.LIGHTNING.get(), pPos, pBlockState);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
	}

	public void onUse(ServerPlayer player, Level level, BlockPos pos, BlockState state) {
		Common.tell(player, "You clicked the lightning block!");
	}

	public void tick(Level level, BlockPos pos, BlockState state) {
		if (!level.isClientSide() && level.random.nextInt(50) == 0) {
			LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
			lightningBolt.setVisualOnly(true);

			BlockPos randomPos = pos.offset(level.random.nextInt(15) - 10, 0, level.random.nextInt(15) - 10);
			lightningBolt.setPos(randomPos.getX(), randomPos.getY(), randomPos.getZ());

			level.addFreshEntity(lightningBolt);

			for (int i = 0; i < 30; i++) {
				double x = pos.getX() + 0.5;
				double y = pos.getY() + 1;
				double z = pos.getZ() + 0.5;
				int count = 1;
				double xOffset = Math.cos(i * 15) * 0.15;
				double yOffset = 0.1;
				double zOffset = Math.sin(i * 15) * 0.15;
				double speed = 0.2;

				((ServerLevel) level).sendParticles(CustomParticles.HAMMER.get(), x, y, z, count, xOffset, yOffset, zOffset, speed);
			}
		}
	}
}
