package net.earthlink.mlind128.lonewolf.enchant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;

public class EnchantLightning extends Enchantment {
	protected EnchantLightning() {
		super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, EquipmentSlot.values());
	}

	@Override
	public void doPostAttack(LivingEntity attacker, Entity target, int enchantLevel) {
		Level world = attacker.level();
		BlockPos position = target.blockPosition();

		int chance = enchantLevel == 1 ? 40 : 80;

		if (!world.isClientSide() && world.random.nextInt(100) < chance)
			EntityType.LIGHTNING_BOLT.spawn((ServerLevel) world, position, MobSpawnType.TRIGGERED);
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
}
