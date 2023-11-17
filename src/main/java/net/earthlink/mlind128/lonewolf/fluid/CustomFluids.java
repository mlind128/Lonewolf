package net.earthlink.mlind128.lonewolf.fluid;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class CustomFluids {

	public static final DeferredRegister<FluidType> FLUID_TYPE_REGISTER =
			DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Lonewolf.MOD_ID);

	public static final DeferredRegister<Fluid> FLUIDS_REGISTER =
			DeferredRegister.create(ForgeRegistries.FLUIDS, Lonewolf.MOD_ID);

	public static final RegistryObject<FluidType> ACID_FLUID_TYPE = FLUID_TYPE_REGISTER.register("acid_fluid_type",
			() -> new SimpleFluid(
					0xe07f38,
					FluidType.Properties.create().lightLevel(5)));

	public static final RegistryObject<FlowingFluid> SOURCE_ACID = FLUIDS_REGISTER.register("acid_fluid",
			() -> new ForgeFlowingFluid.Source(CustomFluids.ACID_FLUID_PROPERTIES));

	public static final RegistryObject<FlowingFluid> FLOWING_ACID = FLUIDS_REGISTER.register("flowing_acid_fluid",
			() -> new ForgeFlowingFluid.Flowing(CustomFluids.ACID_FLUID_PROPERTIES));

	public static final ForgeFlowingFluid.Properties ACID_FLUID_PROPERTIES =
			new ForgeFlowingFluid.Properties(CustomFluids.ACID_FLUID_TYPE, SOURCE_ACID, FLOWING_ACID)
					.block(CustomBlocks.ACID_BLOCK)
					.bucket(CustomItems.ACID_BUCKET);
}
