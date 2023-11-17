package net.earthlink.mlind128.lonewolf.world;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class CustomDimensions {

	public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(Lonewolf.MOD_ID, "moon"));

	public static final ResourceKey<LevelStem> MOON_STEM = ResourceKey.create(Registries.LEVEL_STEM,
			new ResourceLocation(Lonewolf.MOD_ID, "moon"));

	public static final ResourceKey<DimensionType> MOON_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(Lonewolf.MOD_ID, "moon"));

	public static void generateDimension(BootstapContext<DimensionType> context) {
		context.register(MOON_TYPE, new DimensionType(
				OptionalLong.of(1000), // fixedTime
				false, // hasSkylight
				false, // hasCeiling
				false, // ultraWarm
				false, // natural
				1.0, // coordinateScale
				true, // bedWorks
				true, // respawnAnchorWorks
				0, // minY
				256, // height
				256, // logicalHeight
				BlockTags.INFINIBURN_END, // infiniburn
				BuiltinDimensionTypes.END_EFFECTS, // effectsLocation
				0.0F, //ambientLight
				new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)));
	}

	public static void generateStem(BootstapContext<LevelStem> context) {
		HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimType = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

		FixedBiomeSource biome = new FixedBiomeSource(biomeRegistry.getOrThrow(CustomBiomes.MOON_LAND));

		Holder<NoiseGeneratorSettings> noise = noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD);
		NoiseBasedChunkGenerator generator = new NoiseBasedChunkGenerator(biome, noise);

		context.register(MOON_STEM, new LevelStem(dimType.getOrThrow(CustomDimensions.MOON_TYPE), generator));
	}
}
