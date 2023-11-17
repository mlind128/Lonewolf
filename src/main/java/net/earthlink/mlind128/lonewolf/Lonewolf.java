package net.earthlink.mlind128.lonewolf;

import com.mojang.logging.LogUtils;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.blockentity.CustomBlockEntities;
import net.earthlink.mlind128.lonewolf.creativetab.CustomCreativeTabs;
import net.earthlink.mlind128.lonewolf.effect.CustomEffects;
import net.earthlink.mlind128.lonewolf.enchant.CustomEnchants;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.earthlink.mlind128.lonewolf.entity.CustomVillagers;
import net.earthlink.mlind128.lonewolf.fluid.CustomFluids;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.listener.ForgeListener;
import net.earthlink.mlind128.lonewolf.listener.ModListener;
import net.earthlink.mlind128.lonewolf.menu.CustomMenu;
import net.earthlink.mlind128.lonewolf.network.Packets;
import net.earthlink.mlind128.lonewolf.painting.CustomPaintings;
import net.earthlink.mlind128.lonewolf.particle.CustomParticles;
import net.earthlink.mlind128.lonewolf.potion.CustomPotions;
import net.earthlink.mlind128.lonewolf.sound.CustomSounds;
import net.earthlink.mlind128.lonewolf.world.CustomBiomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Random;

@Mod(Lonewolf.MOD_ID)
public class Lonewolf {

	public static final String MOD_ID = "lonewolf";
	public static Random RANDOM = new Random();

	private static final Logger LOGGER = LogUtils.getLogger();

	public Lonewolf() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		CustomItems.ITEM_REGISTER.register(modEventBus);
		CustomBlocks.BLOCK_REGISTER.register(modEventBus);
		CustomCreativeTabs.CREATIVE_TAB_REGISTER.register(modEventBus);
		CustomEnchants.ENCHANTMENT_REGISTER.register(modEventBus);
		CustomEffects.EFFECT_REGISTER.register(modEventBus);
		CustomPotions.POTION_REGISTER.register(modEventBus);
		CustomSounds.SOUND_REGISTER.register(modEventBus);
		CustomPaintings.PAINTING_REGISTER.register(modEventBus);
		CustomVillagers.INTEREST_REGISTER.register(modEventBus);
		CustomVillagers.PROFESSION_REGISTER.register(modEventBus);
		CustomParticles.PARTICLE_REGISTER.register(modEventBus);
		CustomFluids.FLUID_TYPE_REGISTER.register(modEventBus);
		CustomFluids.FLUIDS_REGISTER.register(modEventBus);
		CustomBlockEntities.BLOCK_ENTITY_REGISTER.register(modEventBus);
		CustomMenu.MENU_REGISTER.register(modEventBus);
		CustomEntities.ENTITY_REGISTER.register(modEventBus);

		Packets.register();
		CustomBiomes.register();

		modEventBus.register(new ModListener());
		MinecraftForge.EVENT_BUS.register(new ForgeListener());
	}

}
