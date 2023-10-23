package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.sound.CustomSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class BlockCustomDoor extends DoorBlock {

	public BlockCustomDoor(Block parentBlock) {
		super(Properties.copy(parentBlock), BlockSetType.OAK);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}

	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (!this.type().canOpenByHand()) {
			return InteractionResult.PASS;
		} else {
			pState = pState.cycle(OPEN);
			pLevel.setBlock(pPos, pState, 10);
			this.playSound(pPlayer, pLevel, pPos, pState.getValue(OPEN));
			pLevel.gameEvent(pPlayer, this.isOpen(pState) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
			return InteractionResult.sidedSuccess(pLevel.isClientSide);
		}
	}

	@Override
	public void setOpen(@Nullable Entity pEntity, Level pLevel, BlockState pState, BlockPos pPos, boolean pOpen) {
		if (pState.is(this) && pState.getValue(OPEN) != pOpen) {
			pLevel.setBlock(pPos, pState.setValue(OPEN, Boolean.valueOf(pOpen)), 10);
			this.playSound(pEntity, pLevel, pPos, pOpen);
			pLevel.gameEvent(pEntity, pOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
		}
	}

	@Override
	public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
		boolean flag = pLevel.hasNeighborSignal(pPos) || pLevel.hasNeighborSignal(pPos.relative(pState.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
		if (!this.defaultBlockState().is(pBlock) && flag != pState.getValue(POWERED)) {
			if (flag != pState.getValue(OPEN)) {
				this.playSound((Entity) null, pLevel, pPos, flag);
				pLevel.gameEvent((Entity) null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pPos);
			}

			pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Boolean.valueOf(flag)), 2);
		}

	}

	private void playSound(@Nullable Entity pSource, Level pLevel, BlockPos pPos, boolean pIsOpening) {
		pLevel.playSound(pSource, pPos, pIsOpening ?
				CustomSounds.DOOR_OPEN.get() : CustomSounds.DOOR_CLOSE.get(), SoundSource.BLOCKS, 1.0F, pLevel.getRandom().nextFloat() * 0.1F + 0.9F);
	}
}
