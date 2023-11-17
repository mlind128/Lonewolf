package net.earthlink.mlind128.lonewolf.world;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class CustomSurfaceRules {

	public static SurfaceRules.RuleSource compileRules() {
		return SurfaceRules.sequence(
				SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.isBiome(CustomBiomes.MAPLE_FOREST),
								SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState()))),

						SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SurfaceRules.state(Blocks.DIORITE.defaultBlockState()))),

				SurfaceRules.sequence(
						SurfaceRules.ifTrue(SurfaceRules.isBiome(CustomBiomes.MOON_LAND),
								SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(Blocks.STONE.defaultBlockState()))),

						SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SurfaceRules.state(Blocks.ANDESITE.defaultBlockState()))),
				
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0),
										SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())),
								SurfaceRules.state(Blocks.DIRT.defaultBlockState())))
		);
	}
}
