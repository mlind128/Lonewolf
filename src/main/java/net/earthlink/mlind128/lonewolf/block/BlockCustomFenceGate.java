package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class BlockCustomFenceGate extends FenceGateBlock {

	public BlockCustomFenceGate(Block parentBlock) {
		super(Properties.copy(parentBlock), SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_DOOR_CLOSE);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}
}
