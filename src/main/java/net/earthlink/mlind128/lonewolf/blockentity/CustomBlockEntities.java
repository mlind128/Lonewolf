package net.earthlink.mlind128.lonewolf.blockentity;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER =
			DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Lonewolf.MOD_ID);

	public static final RegistryObject<BlockEntityType> LIGHTNING = BLOCK_ENTITY_REGISTER.register("ligktning_block_entity",
			() -> BlockEntityType.Builder.of(LightningBlockEntity::new, CustomBlocks.LIGHTNING.get()).build(null));

	public static final RegistryObject<BlockEntityType> FIRE_FANG_MACHINE = BLOCK_ENTITY_REGISTER.register("fire_fang_machine_block_entity",
			() -> BlockEntityType.Builder.of(FireFangMachineBlockEntity::new, CustomBlocks.FIRE_FANG_MACHINE.get()).build(null));
}
