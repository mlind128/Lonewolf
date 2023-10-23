package net.earthlink.mlind128.lonewolf.effect;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomEffects {

	public static final DeferredRegister<MobEffect> EFFECT_REGISTER =
			DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Lonewolf.MOD_ID);


	public static final RegistryObject<MobEffect> SPIDER_CLIMBING = EFFECT_REGISTER.register("spider_climbing",
			() -> new EffectSpiderClimbing());
}
