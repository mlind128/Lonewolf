package net.earthlink.mlind128.lonewolf.model;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class Tags {

	public static final TagKey<Block> TO_LAVA =
			TagKey.create(Registries.BLOCK, new ResourceLocation(Lonewolf.MOD_ID, "to_lava"));

	public static final TagKey<Block> NEEDS_FANG_TOOL =
			TagKey.create(Registries.BLOCK, new ResourceLocation(Lonewolf.MOD_ID, "needs_fang_tool"));

	public static final TagKey<Block> MINEABLE_PAXEL =
			TagKey.create(Registries.BLOCK, new ResourceLocation(Lonewolf.MOD_ID, "mineable/paxel"));
}
