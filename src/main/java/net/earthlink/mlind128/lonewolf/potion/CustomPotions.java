package net.earthlink.mlind128.lonewolf.potion;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.effect.CustomEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomPotions {

	public static final DeferredRegister<Potion> POTION_REGISTER =
			DeferredRegister.create(ForgeRegistries.POTIONS, Lonewolf.MOD_ID);

	public static final RegistryObject<Potion> SPIDER_CLIMBING = POTION_REGISTER.register("spider_climbing_potion",
			() -> new Potion(new MobEffectInstance(CustomEffects.SPIDER_CLIMBING.get(), 20 * 60, 0)));
}
