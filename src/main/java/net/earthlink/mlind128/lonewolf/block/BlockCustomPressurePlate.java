package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;

public class BlockCustomPressurePlate extends PressurePlateBlock {

	public BlockCustomPressurePlate(Block parentBlock) {
		super(Sensitivity.EVERYTHING, Properties.copy(parentBlock), BlockSetType.STONE);
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}
}
