package net.earthlink.mlind128.lonewolf.particle;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CustomParticles {

	public static final DeferredRegister<ParticleType<?>> PARTICLE_REGISTER =
			DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Lonewolf.MOD_ID);

	public static final RegistryObject<SimpleParticleType> HAMMER = PARTICLE_REGISTER.register("particle_hammer",
			() -> new SimpleParticleType(false));
}
