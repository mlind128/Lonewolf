package net.earthlink.mlind128.lonewolf.item;

import net.earthlink.mlind128.lonewolf.util.Common;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;

public class ItemHammer extends DiggerItem {

	private static final Set<BlockPos> minedBlocks = new HashSet<>();

	public ItemHammer(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, TagKey<Block> pBlocks, Properties pProperties) {
		super(pAttackDamageModifier, pAttackSpeedModifier, pTier, pBlocks, pProperties);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos position, LivingEntity entity) {
		if (!super.mineBlock(stack, level, state, position, entity))
			return false;

		if (!level.isClientSide && entity instanceof ServerPlayer) {
			ItemStack hand = entity.getMainHandItem();

			if (minedBlocks.contains(position))
				return false;

			Direction direction = entity.getDirection();
			float pitch = entity.getXRot();

			if (pitch > 50)
				direction = Direction.DOWN;

			else if (pitch < -50)
				direction = Direction.UP;

			for (BlockPos nearbyPos : Common.getBlocksInDirection(position, direction, 1)) {
				if (nearbyPos == position || !this.isCorrectToolForDrops(entity.getMainHandItem(), level.getBlockState(nearbyPos)))
					continue;

				minedBlocks.add(nearbyPos);
				((ServerPlayer) entity).gameMode.destroyBlock(nearbyPos);
				minedBlocks.remove(nearbyPos);
			}

			if (level.random.nextInt(10) == 0) {
				LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
				lightningBolt.setVisualOnly(true);
				lightningBolt.setPos(position.getX(), position.getY(), position.getZ());

				level.addFreshEntity(lightningBolt);
			}
		}
		return true;
	}
}
