package net.earthlink.mlind128.lonewolf.painting;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomPaintings {

	public static final DeferredRegister<PaintingVariant> PAINTING_REGISTER =
			DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Lonewolf.MOD_ID);

	public static final RegistryObject<PaintingVariant> PEANUT_RIP = PAINTING_REGISTER.register("peanut_rip",
			() -> new PaintingVariant(64, 64));
}
