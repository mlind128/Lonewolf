package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class BlockCustomButton extends ButtonBlock {

	public BlockCustomButton(Block parentBlock) {
		super(Properties.copy(parentBlock), BlockSetType.STONE, 10, true);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}
}
