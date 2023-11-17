package net.earthlink.mlind128.lonewolf.listener;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.BlockCustomLeaves;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.blockentity.CustomBlockEntities;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.earthlink.mlind128.lonewolf.entity.blackWolf.BlackWolfEntity;
import net.earthlink.mlind128.lonewolf.entity.blackWolf.BlackWolfModel;
import net.earthlink.mlind128.lonewolf.entity.blackWolf.BlackWolfRenderer;
import net.earthlink.mlind128.lonewolf.entity.renderer.FireFangMachineEntityRenderer;
import net.earthlink.mlind128.lonewolf.entity.werewolf.WerewolfEntity;
import net.earthlink.mlind128.lonewolf.entity.werewolf.WerewolfModel;
import net.earthlink.mlind128.lonewolf.entity.werewolf.WerewolfRenderer;
import net.earthlink.mlind128.lonewolf.fluid.CustomFluids;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.item.ItemFireFang;
import net.earthlink.mlind128.lonewolf.menu.CustomMenu;
import net.earthlink.mlind128.lonewolf.model.SimpleBrewingRecipe;
import net.earthlink.mlind128.lonewolf.model.Thirst;
import net.earthlink.mlind128.lonewolf.model.ThirstProvider;
import net.earthlink.mlind128.lonewolf.model.keys;
import net.earthlink.mlind128.lonewolf.particle.CustomParticles;
import net.earthlink.mlind128.lonewolf.particle.ParticleHammer;
import net.earthlink.mlind128.lonewolf.potion.CustomPotions;
import net.earthlink.mlind128.lonewolf.util.SimpleMenu;
import net.earthlink.mlind128.lonewolf.world.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import terrablender.api.SurfaceRuleManager;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class ModListener {

	@SubscribeEvent
	public void onCommonSetup(FMLCommonSetupEvent event) {
		ComposterBlock.COMPOSTABLES.put(CustomItems.WILD_MEAT.get(), 0.75F);
		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(CustomBlocks.BULL_THISTLE.getId(), CustomBlocks.POTTED_BULL_THISTLE);

		BrewingRecipeRegistry.addRecipe(
				new SimpleBrewingRecipe(Potions.AWKWARD, CustomItems.RICE.get(), CustomPotions.SPIDER_CLIMBING.get()));

		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Lonewolf.MOD_ID, CustomSurfaceRules.compileRules());
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

				ItemBlockRenderTypes.setRenderLayer(CustomFluids.SOURCE_ACID.get(), RenderType.translucent());
				ItemBlockRenderTypes.setRenderLayer(CustomFluids.FLOWING_ACID.get(), RenderType.translucent());

				MenuScreens.register(CustomMenu.FIRE_FANG_MACHINE.get(), SimpleMenu.Screen::new);

				EntityRenderers.register(CustomEntities.BLACK_WOLF.get(), BlackWolfRenderer::new);

				EntityRenderers.register(CustomEntities.WEREWOLF.get(), WerewolfRenderer::new);
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

	@SubscribeEvent
	public void onParticlesRegister(RegisterParticleProvidersEvent event) {
		System.out.println("Hello from onParticlesRegister");

		event.registerSpriteSet(CustomParticles.HAMMER.get(), new ParticleEngine.SpriteParticleRegistration<>() {
			@Override
			public ParticleProvider<SimpleParticleType> create(SpriteSet texture) {
				return new ParticleHammer.Provider(texture);
			}
		});
	}

	@SubscribeEvent
	public void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(CustomBlockEntities.FIRE_FANG_MACHINE.get(),
				context -> new FireFangMachineEntityRenderer());
	}

	@SubscribeEvent
	public void onLayerDefinitionsRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(CustomEntities.BLACK_WOLF_LAYER, BlackWolfModel::createBodyLayer);
		event.registerLayerDefinition(CustomEntities.WEREWOLF_LAYER, WerewolfModel::createBodyLayer);
	}

	@SubscribeEvent
	public void onAttributesRegister(EntityAttributeCreationEvent event) {
		event.put(CustomEntities.BLACK_WOLF.get(), BlackWolfEntity.createAttributes().build());
		event.put(CustomEntities.WEREWOLF.get(), WerewolfEntity.createAttributes().build());
	}

	@SubscribeEvent
	public void onSpawnPlacementRegister(SpawnPlacementRegisterEvent event) {
		event.register(CustomEntities.BLACK_WOLF.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				BlackWolfEntity::checkSpawnRules,
				SpawnPlacementRegisterEvent.Operation.REPLACE);

		event.register(CustomEntities.WEREWOLF.get(),
				SpawnPlacements.Type.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				WerewolfEntity::checkSpawnRules,
				SpawnPlacementRegisterEvent.Operation.REPLACE);
	}

	@SubscribeEvent
	public void onColorHandlerBlockRegistered(RegisterColorHandlersEvent.Block event) {
		event.register(BlockCustomLeaves::getColor, CustomBlocks.MAPLE_LEAVES.get());
	}

	@SubscribeEvent
	public void onColorHandlerItemRegistered(RegisterColorHandlersEvent.Item event) {
		event.register((stack, index) -> FoliageColor.getDefaultColor(), CustomBlocks.MAPLE_LEAVES.get());
	}

	@SubscribeEvent
	public void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

		DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(output, lookup,
				new RegistrySetBuilder()
						.add(Registries.BIOME, CustomBiomes::generateData)
						.add(ForgeRegistries.Keys.BIOME_MODIFIERS, CustomBiomeModifiers::generateData)
						.add(Registries.CONFIGURED_FEATURE, CustomConfiguredFeatures::generateData)
						.add(Registries.PLACED_FEATURE, CustomPlacedFeatures::generateData)
						.add(Registries.LEVEL_STEM, CustomDimensions::generateStem)
						.add(Registries.DIMENSION_TYPE, CustomDimensions::generateDimension)
				, Set.of(Lonewolf.MOD_ID));

		generator.addProvider(event.includeServer(), provider);
	}
}
