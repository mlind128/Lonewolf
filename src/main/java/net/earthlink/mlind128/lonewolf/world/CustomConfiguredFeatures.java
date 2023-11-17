package net.earthlink.mlind128.lonewolf.world;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class CustomConfiguredFeatures {

	public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FANG_ORE =
			ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "fang_ore"));

	public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_FANG_ORE =
			ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "nether_fang_ore"));

	public static final ResourceKey<ConfiguredFeature<?, ?>> END_FANG_ORE =
			ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "end_fang_ore"));

	public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE =
			ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Lonewolf.MOD_ID, "maple_tree"));

	public static void generateData(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplaceabeles = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplaceabeles = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		RuleTest netherrackReplaceabeles = new BlockMatchTest(Blocks.NETHERRACK);
		RuleTest endReplaceabeles = new BlockMatchTest(Blocks.END_STONE);

		List<OreConfiguration.TargetBlockState> overworldOres = List.of(
				OreConfiguration.target(stoneReplaceabeles, CustomBlocks.FANG_ORE.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceabeles, CustomBlocks.FANG_ORE.get().defaultBlockState()));

		context.register(OVERWORLD_FANG_ORE,
				new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(overworldOres, 9)));

		context.register(NETHER_FANG_ORE,
				new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(netherrackReplaceabeles, CustomBlocks.NETHER_FANG_ORE.get().defaultBlockState(), 9)));

		context.register(END_FANG_ORE,
				new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(endReplaceabeles, CustomBlocks.FANG_ORE.get().defaultBlockState(), 9)));

		context.register(MAPLE_TREE,
				new ConfiguredFeature<>(Feature.TREE,
						new TreeConfiguration.TreeConfigurationBuilder(
								BlockStateProvider.simple(Blocks.OAK_LOG),
								new StraightTrunkPlacer(5, 0, 2),

								BlockStateProvider.simple(CustomBlocks.MAPLE_LEAVES.get()),
								new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
								new TwoLayersFeatureSize(2, 1, 2)).build()));
	}
}
