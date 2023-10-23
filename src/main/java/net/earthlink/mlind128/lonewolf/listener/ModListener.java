package net.earthlink.mlind128.lonewolf.listener;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.item.ItemFireFang;
import net.earthlink.mlind128.lonewolf.model.SimpleBrewingRecipe;
import net.earthlink.mlind128.lonewolf.model.Thirst;
import net.earthlink.mlind128.lonewolf.model.ThirstProvider;
import net.earthlink.mlind128.lonewolf.model.keys;
import net.earthlink.mlind128.lonewolf.potion.CustomPotions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class ModListener {

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		ComposterBlock.COMPOSTABLES.put(CustomItems.WILD_MEAT.get(), 0.75F);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(CustomBlocks.BULL_THISTLE.getId(), CustomBlocks.POTTED_BULL_THISTLE);

		BrewingRecipeRegistry.addRecipe(
				new SimpleBrewingRecipe(Potions.AWKWARD, CustomItems.RICE.get(), CustomPotions.SPIDER_CLIMBING.get()));
	}

	@SubscribeEvent
	public void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(new Runnable() {
			@Override
			public void run() {
				ItemProperties.register(CustomItems.FIRE_FANG.get(),
						new ResourceLocation(Lonewolf.MOD_ID + ":" + ItemFireFang.TAG_USED), new ClampedItemPropertyFunction() {
							@Override
							public float unclampedCall(ItemStack stack, ClientLevel level, LivingEntity entity, int seed) {
								return stack.hasTag() && stack.getTag().contains(ItemFireFang.TAG_USED) ? 1 : 0;
							}
						});

				ItemProperties.register(CustomItems.FANG_BOW.get(),
						new ResourceLocation("pull"), (stack, level, entity, seed) -> {
							if (entity == null) {
								return 0.0F;
							} else {
								return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
							}
						});

				ItemProperties.register(CustomItems.FANG_BOW.get(),
						new ResourceLocation("pulling"), (stack, level, entity, seed) -> {
							return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
						});

				ItemProperties.register(CustomItems.FANG_SHIELD.get(),
						new ResourceLocation("blocking"), (stack, level, entity, seed) -> {
							return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
						});
			}
		});
	}

	@SubscribeEvent
	public void onKeyRegister(RegisterKeyMappingsEvent event) {
		event.register(keys.TEST);
	}

	@SubscribeEvent
	public void onOverlayRegister(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll("thirst", Thirst.HUD);
	}

	@SubscribeEvent
	public void onCapabilitiesRegister(RegisterCapabilitiesEvent event) {
		event.register(ThirstProvider.class);
	}
}
