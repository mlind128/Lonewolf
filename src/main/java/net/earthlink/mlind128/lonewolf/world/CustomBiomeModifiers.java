package net.earthlink.mlind128.lonewolf.world;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class CustomBiomeModifiers {

	public static final ResourceKey<BiomeModifier> ADD_FANG_ORE =
			ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Lonewolf.MOD_ID, "add_fang_ore"));

	public static final ResourceKey<BiomeModifier> ADD_NETHER_FANG_ORE =
			ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Lonewolf.MOD_ID, "add_nether_fang_ore"));

	public static final ResourceKey<BiomeModifier> ADD_END_FANG_ORE =
			ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Lonewolf.MOD_ID, "add_end_fang_ore"));

	public static final ResourceKey<BiomeModifier> ADD_MAPLE_TREE =
			ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Lonewolf.MOD_ID, "add_maple_tree"));

	public static void generateData(BootstapContext<BiomeModifier> context) {
		HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

		context.register(ADD_FANG_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
				HolderSet.direct(placedFeatures.getOrThrow(CustomPlacedFeatures.FANG_ORE)),
				GenerationStep.Decoration.UNDERGROUND_ORES));

		context.register(ADD_NETHER_FANG_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(BiomeTags.IS_NETHER),
				HolderSet.direct(placedFeatures.getOrThrow(CustomPlacedFeatures.NETHER_FANG_ORE)),
				GenerationStep.Decoration.UNDERGROUND_ORES));

		context.register(ADD_END_FANG_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(BiomeTags.IS_END),
				HolderSet.direct(placedFeatures.getOrThrow(CustomPlacedFeatures.END_FANG_ORE)),
				GenerationStep.Decoration.UNDERGROUND_ORES));

		context.register(ADD_MAPLE_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
				biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
				HolderSet.direct(placedFeatures.getOrThrow(CustomPlacedFeatures.MAPLE_TREE_INJECTED)),
				GenerationStep.Decoration.VEGETAL_DECORATION));
	}
}
