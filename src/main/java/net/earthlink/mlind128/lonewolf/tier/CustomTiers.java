package net.earthlink.mlind128.lonewolf.tier;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.model.Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public final class CustomTiers {

	//Tiers

	public static final Tier FANG = TierSortingRegistry.registerTier(
			new ForgeTier(5, 3032, 12F, 6, 20,
					Tags.NEEDS_FANG_TOOL, () -> Ingredient.of(CustomItems.FANG.get())),
			new ResourceLocation(Lonewolf.MOD_ID, "fang"),
			List.of(Tiers.NETHERITE),
			List.of()
	);
}
