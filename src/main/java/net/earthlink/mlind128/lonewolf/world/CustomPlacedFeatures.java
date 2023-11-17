package net.earthlink.mlind128.lonewolf.world;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class CustomPlacedFeatures {

	public static final ResourceKey<PlacedFeature> FANG_ORE =
			ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "fang_ore_placed"));

	public static final ResourceKey<PlacedFeature> NETHER_FANG_ORE =
			ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "nether_fang_ore_placed"));

	public static final ResourceKey<PlacedFeature> END_FANG_ORE =
			ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "end_fang_ore_placed"));

	public static final ResourceKey<PlacedFeature> MAPLE_TREE_INJECTED =
			ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "maple_tree_injected"));

	public static final ResourceKey<PlacedFeature> MAPLE_TREE =
			ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "maple_tree"));

	public static void generateData(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		context.register(FANG_ORE, new PlacedFeature(
				configuredFeatures.getOrThrow(CustomConfiguredFeatures.OVERWORLD_FANG_ORE),
				createPlacement(12, -64, 80)));

		context.register(NETHER_FANG_ORE, new PlacedFeature(
				configuredFeatures.getOrThrow(CustomConfiguredFeatures.NETHER_FANG_ORE),
				createPlacement(9, -64, 80)));

		context.register(END_FANG_ORE, new PlacedFeature(
				configuredFeatures.getOrThrow(CustomConfiguredFeatures.END_FANG_ORE),
				createPlacement(7, -64, 80)));

		context.register(MAPLE_TREE_INJECTED, new PlacedFeature(
				configuredFeatures.getOrThrow(CustomConfiguredFeatures.MAPLE_TREE),
				VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 1), CustomBlocks.MAPLE_SAPLING.get())));

		context.register(MAPLE_TREE, new PlacedFeature(
				configuredFeatures.getOrThrow(CustomConfiguredFeatures.MAPLE_TREE),
				VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 1), CustomBlocks.MAPLE_SAPLING.get())));

	}

	public static List<PlacementModifier> createPlacement(int count, int from, int to) {
		return List.of(CountPlacement.of(count),
				InSquarePlacement.spread(),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(from), VerticalAnchor.absolute(to)),
				BiomeFilter.biome());
	}
}
