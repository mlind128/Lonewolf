package net.earthlink.mlind128.lonewolf.enchant;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomEnchants {

	public static final DeferredRegister<Enchantment> ENCHANTMENT_REGISTER =
			DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Lonewolf.MOD_ID);

	public static final RegistryObject<Enchantment> LIGHTNING = ENCHANTMENT_REGISTER.register("lightning",
			() -> new EnchantLightning());
}
