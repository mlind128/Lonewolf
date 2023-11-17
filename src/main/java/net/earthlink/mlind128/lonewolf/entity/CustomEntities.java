package net.earthlink.mlind128.lonewolf.entity;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.entity.blackWolf.BlackWolfEntity;
import net.earthlink.mlind128.lonewolf.entity.werewolf.WerewolfEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomEntities {

	public static final DeferredRegister<EntityType<?>> ENTITY_REGISTER =
			DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Lonewolf.MOD_ID);

	public static final RegistryObject<EntityType<BlackWolfEntity>> BLACK_WOLF = ENTITY_REGISTER.register("black_wolf",
			() -> EntityType.Builder.of(BlackWolfEntity::new, MobCategory.CREATURE).sized(0.75f, 0.95f).build("black_wolf"));

	public static final ModelLayerLocation BLACK_WOLF_LAYER = new ModelLayerLocation(
			new ResourceLocation(Lonewolf.MOD_ID, "black_wolf_layer"), "black_wolf_layer");

	public static final RegistryObject<EntityType<WerewolfEntity>> WEREWOLF = ENTITY_REGISTER.register("werewolf",
			() -> EntityType.Builder.of(WerewolfEntity::new, MobCategory.CREATURE).sized(0.75f, 1.5f).build("werewolf"));

	public static final ModelLayerLocation WEREWOLF_LAYER = new ModelLayerLocation(
			new ResourceLocation(Lonewolf.MOD_ID, "werewolf_layer"), "werewolf_layer");

}
