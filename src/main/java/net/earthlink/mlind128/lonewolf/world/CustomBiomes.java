package net.earthlink.mlind128.lonewolf.world;


import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

public class CustomBiomes {

	public static final ResourceKey<Biome> MAPLE_FOREST =
			ResourceKey.create(Registries.BIOME, new ResourceLocation(Lonewolf.MOD_ID, "maple_forest"));

	public static final ResourceKey<Biome> MOON_LAND =
			ResourceKey.create(Registries.BIOME, new ResourceLocation(Lonewolf.MOD_ID, "moon_land"));

	public static void register() {
		Regions.register(new Region(new ResourceLocation(Lonewolf.MOD_ID, "overworld"), RegionType.OVERWORLD, 10) {

			@Override
			public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
				this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
					builder.replaceBiome(Biomes.PLAINS, CustomBiomes.MAPLE_FOREST);
				});
			}
		});

	}

	public static void generateData(BootstapContext<Biome> context) {
		addMapleForest(context);
		addMoonLand(context);

	}

	public static void addMapleForest(BootstapContext<Biome> context) {
		MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
		mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(CustomEntities.BLACK_WOLF.get(), 10, 4, 4));
		mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(CustomEntities.WEREWOLF.get(), 5, 1, 1));

		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(context
				.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
		BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
		BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
		BiomeDefaultFeatures.addDefaultSprings(builder);
		BiomeDefaultFeatures.addSurfaceFreezing(builder);
		BiomeDefaultFeatures.plainsSpawns(mobs);
		BiomeDefaultFeatures.addPlainGrass(builder);
		builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_PUMPKIN);
		//builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CustomPlacedFeatures.MAPLE_TREE);
		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultSoftDisks(builder);
		BiomeDefaultFeatures.addPlainVegetation(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);
		BiomeDefaultFeatures.addDefaultExtraVegetation(builder);

		context.register(MAPLE_FOREST, new Biome.BiomeBuilder()
				.hasPrecipitation(true)
				.temperature(0.8F)
				.downfall(0.4F)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(0xe10ce8)
						.fogColor(0x8084e0)

						.grassColorOverride(0x568c3e)
						.foliageColorOverride(0xa6ed85)

						.waterColor(0x2e7be8)
						.waterFogColor(0x1f3ac2)

						.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
				.mobSpawnSettings(mobs.build())
				.generationSettings(builder.build())
				.build());
	}

	public static void addMoonLand(BootstapContext<Biome> context) {
		HolderGetter<PlacedFeature> feature = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carver = context.lookup(Registries.CONFIGURED_CARVER);

		BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(feature, carver);

		BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
		BiomeDefaultFeatures.addDefaultCrystalFormations(builder);

		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_GRAVEL);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_UPPER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_DIORITE_LOWER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_UPPER);
		builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_ANDESITE_LOWER);

		BiomeDefaultFeatures.addDefaultOres(builder);
		BiomeDefaultFeatures.addDefaultMushrooms(builder);

		MobSpawnSettings.Builder mobs = new MobSpawnSettings.Builder();
		mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(CustomEntities.BLACK_WOLF.get(), 10, 4, 4));
		mobs.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(CustomEntities.WEREWOLF.get(), 5, 1, 1));

		context.register(MOON_LAND, new Biome.BiomeBuilder()
				.hasPrecipitation(false)
				.temperature(0.5F)
				.downfall(0.5F)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(7972607)
						.fogColor(12638463)

						.grassColorOverride(9470285)
						.foliageColorOverride(10387789)

						.waterColor(-3355648)
						.waterFogColor(-3355648)

						.ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).build())
				.mobSpawnSettings(mobs.build())
				.generationSettings(builder.build())
				.build());
	}
}
