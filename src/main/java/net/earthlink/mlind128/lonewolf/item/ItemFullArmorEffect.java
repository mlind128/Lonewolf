package net.earthlink.mlind128.lonewolf.item;

import net.earthlink.mlind128.lonewolf.armor.CustomArmor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemFullArmorEffect extends ArmorItem {
	public ItemFullArmorEffect(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {

		if (!level.isClientSide() && level.getGameTime() % (20 * 30) == 0) {
			Inventory inventory = player.getInventory();

			ArmorItem boots = getArmorItem(inventory.getArmor(0));
			ArmorItem leggings = getArmorItem(inventory.getArmor(1));
			ArmorItem chestplate = getArmorItem(inventory.getArmor(2));
			ArmorItem helmet = getArmorItem(inventory.getArmor(3));

			System.out.println("Here");

			if (boots == null || leggings == null || chestplate == null || helmet == null) {
				System.out.println("Not sufficient armor");

				return;
			}

			if (boots.getMaterial() == CustomArmor.FANG &&
					leggings.getMaterial() == CustomArmor.FANG &&
					chestplate.getMaterial() == CustomArmor.FANG &&
					helmet.getMaterial() == CustomArmor.FANG)
				player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 30, 1));

		}
	}

	private ArmorItem getArmorItem(ItemStack itemStack) {
		return itemStack.getItem() instanceof ArmorItem ? (ArmorItem) itemStack.getItem() : null;
	}
}
