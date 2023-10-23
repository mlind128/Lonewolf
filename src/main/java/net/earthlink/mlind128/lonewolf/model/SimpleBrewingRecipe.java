package net.earthlink.mlind128.lonewolf.model;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class SimpleBrewingRecipe implements IBrewingRecipe {

	private final Potion base;
	private final Item ingredient;
	private final Potion output;

	public SimpleBrewingRecipe(Potion base, Item ingredient, Potion output) {
		this.base = base;
		this.ingredient = ingredient;
		this.output = output;
	}

	@Override
	public boolean isInput(ItemStack input) {
		return PotionUtils.getPotion(input).equals(this.base);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.getItem().equals(this.ingredient);
	}

	@Override
	public ItemStack getOutput(ItemStack base, ItemStack ingredient) {
		if (!this.isInput(base) || !this.isIngredient(ingredient))
			return ItemStack.EMPTY;

		ItemStack itemStack = new ItemStack(base.getItem());
		itemStack.setTag(new CompoundTag());
		
		return PotionUtils.setPotion(itemStack, this.output);
	}
}
