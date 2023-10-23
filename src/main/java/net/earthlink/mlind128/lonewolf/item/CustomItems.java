package net.earthlink.mlind128.lonewolf.item;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.armor.CustomArmor;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.model.Tags;
import net.earthlink.mlind128.lonewolf.sound.CustomSounds;
import net.earthlink.mlind128.lonewolf.tier.CustomTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CustomItems {

	public static final DeferredRegister<Item> ITEM_REGISTER =
			DeferredRegister.create(ForgeRegistries.ITEMS, Lonewolf.MOD_ID);

	public static final RegistryObject<Item> FANG = ITEM_REGISTER.register("fang",
			() -> new ItemFang());

	public static final RegistryObject<Item> FIRE_FANG = ITEM_REGISTER.register("fire_fang",
			() -> new ItemFireFang());

	public static final RegistryObject<Item> WILD_MEAT = ITEM_REGISTER.register("wild_meat",
			() -> new ItemWildMeat());

	public static final RegistryObject<Item> RICE_SEEDS = ITEM_REGISTER.register("rice_seeds",
			() -> new ItemNameBlockItem(CustomBlocks.RICE_CROP.get(), new Item.Properties()));

	public static final RegistryObject<Item> RICE = ITEM_REGISTER.register("rice",
			() -> new Item((new Item.Properties().food(
					new FoodProperties.Builder()
							.nutrition(1)
							.saturationMod(0.3F)
							.effect(new MobEffectInstance(MobEffects.POISON, 40, 1), 1).build()
			))));

	public static final RegistryObject<Item> FANG_SWORD = ITEM_REGISTER.register("fang_sword",
			() -> new ItemFangSword(CustomTiers.FANG, 4, -2.4F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_PICKAXE = ITEM_REGISTER.register("fang_pickaxe",
			() -> new PickaxeItem(CustomTiers.FANG, 1, -2.8F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_SHOVEL = ITEM_REGISTER.register("fang_shovel",
			() -> new ShovelItem(CustomTiers.FANG, 1.5F, -3.0F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_AXE = ITEM_REGISTER.register("fang_axe",
			() -> new AxeItem(CustomTiers.FANG, 5.0F, -3.0F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_HOE = ITEM_REGISTER.register("fang_hoe",
			() -> new HoeItem(CustomTiers.FANG, -4, 0.0F, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_PAXEL = ITEM_REGISTER.register("fang_paxel",
			() -> new ItemPaxel(CustomTiers.FANG, 4, -2.4F, Tags.MINEABLE_PAXEL, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_HAMMER = ITEM_REGISTER.register("fang_hammer",
			() -> new ItemHammer(CustomTiers.FANG, 1, -2.8F, Tags.MINEABLE_PAXEL, new Item.Properties().fireResistant()));

	public static final RegistryObject<Item> FANG_BOW = ITEM_REGISTER.register("fang_bow",
			() -> new ItemFangBow(new Item.Properties().durability(640)));

	public static final RegistryObject<Item> FANG_SHIELD = ITEM_REGISTER.register("fang_shield",
			() -> new ShieldItem(new Item.Properties().durability(640)));

	public static final RegistryObject<Item> FANG_HELMET = ITEM_REGISTER.register("fang_helmet",
			() -> new ItemFullArmorEffect(CustomArmor.FANG, ArmorItem.Type.HELMET, new Item.Properties()));

	public static final RegistryObject<Item> FANG_CHESTPLATE = ITEM_REGISTER.register("fang_chestplate",
			() -> new ArmorItem(CustomArmor.FANG, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

	public static final RegistryObject<Item> FANG_LEGGINGS = ITEM_REGISTER.register("fang_leggings",
			() -> new ArmorItem(CustomArmor.FANG, ArmorItem.Type.LEGGINGS, new Item.Properties()));

	public static final RegistryObject<Item> FANG_BOOTS = ITEM_REGISTER.register("fang_boots",
			() -> new ArmorItem(CustomArmor.FANG, ArmorItem.Type.BOOTS, new Item.Properties()));

	public static final RegistryObject<Item> FANG_HORSE_ARMOR = ITEM_REGISTER.register("fang_horse_armor",
			() -> new HorseArmorItem(12, new ResourceLocation(Lonewolf.MOD_ID, "textures/entity/horse/armor/horse_armor_fang.png"), new Item.Properties()));

	public static final RegistryObject<Item> WATER_BOTTLE = ITEM_REGISTER.register("water_bottle",
			() -> new ItemWaterBottle());

	public static final RegistryObject<Item> MUSIC_DISK_TEST = ITEM_REGISTER.register("music_disk_test",
			() -> new RecordItem(0, () -> CustomSounds.MUSIC_TEST.get(), new Item.Properties().stacksTo(1), 160));
}
