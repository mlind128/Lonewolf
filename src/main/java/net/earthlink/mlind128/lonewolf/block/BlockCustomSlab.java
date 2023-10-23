package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class BlockCustomSlab extends SlabBlock {

	public BlockCustomSlab(Block parentBlock) {
		super(Properties.copy(parentBlock));
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this, pState.getValue(TYPE) == SlabType.DOUBLE ? 2 : 1));
	}
}
