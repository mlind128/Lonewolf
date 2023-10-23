package net.earthlink.mlind128.lonewolf.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EffectSpiderClimbing extends MobEffect {

	public EffectSpiderClimbing() {
		super(MobEffectCategory.BENEFICIAL, 0x871094);
	}

	@Override
	public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int pAmplifier) {
		if (entity.horizontalCollision) {
			Vec3 velocity = entity.getDeltaMovement();

			entity.setDeltaMovement(velocity.x * .9, .15, velocity.z * .9);
		}
	}
}
