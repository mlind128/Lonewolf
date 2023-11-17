package net.earthlink.mlind128.lonewolf.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleHammer extends TextureSheetParticle {

	protected ParticleHammer(ClientLevel level, double x, double y, double z, SpriteSet texture, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed);

		this.setSpriteFromAge(texture);

		// how far out they go
		this.friction = 0.8F;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		private final SpriteSet spriteSet;

		public Provider(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			return new ParticleHammer(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
		}
	}
}
