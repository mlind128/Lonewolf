package net.earthlink.mlind128.lonewolf.entity;

import com.google.common.collect.ImmutableSet;
import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.function.Predicate;

public final class CustomVillagers {

	public static final DeferredRegister<PoiType> INTEREST_REGISTER =
			DeferredRegister.create(ForgeRegistries.POI_TYPES, Lonewolf.MOD_ID);

	public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTER =
			DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Lonewolf.MOD_ID);

	public static final RegistryObject<PoiType> INTEREST_JUKEBOX = INTEREST_REGISTER.register("interest_jukebox",
			() -> new PoiType(new HashSet<>(Blocks.JUKEBOX.getStateDefinition().getPossibleStates()), 1, 1));

	public static final RegistryObject<PoiType> INTEREST_HAY_BLOCK = INTEREST_REGISTER.register("interest_hay_block",
			() -> new PoiType(new HashSet<>(Blocks.HAY_BLOCK.getStateDefinition().getPossibleStates()), 1, 1));

	public static final RegistryObject<VillagerProfession> PROFESSION_DJ = PROFESSION_REGISTER.register("dj",
			() -> new VillagerProfession("dj",
					new Predicate<>() {

						@Override
						public boolean test(Holder<PoiType> poi) {
							return poi.get() == INTEREST_JUKEBOX.get();
						}
					},
					new Predicate<>() {

						@Override
						public boolean test(Holder<PoiType> poi) {
							return poi.get() == INTEREST_JUKEBOX.get();
						}
					},
					ImmutableSet.of(),
					ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_FARMER
			));

	public static final RegistryObject<VillagerProfession> PROFESSION_RANCHER = PROFESSION_REGISTER.register("rancher",
			() -> new VillagerProfession("rancher",
					new Predicate<>() {

						@Override
						public boolean test(Holder<PoiType> poi) {
							return poi.get() == INTEREST_HAY_BLOCK.get();
						}
					},
					new Predicate<>() {

						@Override
						public boolean test(Holder<PoiType> poi) {
							return poi.get() == INTEREST_HAY_BLOCK.get();
						}
					},
					ImmutableSet.of(),
					ImmutableSet.of(),
					SoundEvents.VILLAGER_WORK_FARMER
			));
}
