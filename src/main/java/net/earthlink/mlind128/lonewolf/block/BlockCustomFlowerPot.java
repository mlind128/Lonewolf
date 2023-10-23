package net.earthlink.mlind128.lonewolf.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.List;
import java.util.function.Supplier;

public class BlockCustomFlowerPot extends FlowerPotBlock {
	public BlockCustomFlowerPot(Supplier<? extends Block> parent) {
		super(() -> (FlowerPotBlock) Blocks.FLOWER_POT, parent, Properties.copy(parent.get()));
	}

	@Override
	public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
		return List.of(new ItemStack(this));
	}
}
