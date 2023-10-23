package net.earthlink.mlind128.lonewolf.block;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class CustomBlocks {

	public static final DeferredRegister<Block> BLOCK_REGISTER =
			DeferredRegister.create(ForgeRegistries.BLOCKS, Lonewolf.MOD_ID);

	public static final RegistryObject<Block> FANG_BLOCK = registerBlock("fang_block",
			() -> new BlockFang());

	public static final RegistryObject<Block> FANG_ORE = registerBlock("fang_ore",
			() -> new BlockFangOre());

	public static final RegistryObject<Block> NETHER_FANG_ORE = registerBlock("nether_fang_ore",
			() -> new BlockCustomOre(Blocks.NETHER_GOLD_ORE));


	public static final RegistryObject<Block> FANG_STAIRS = registerBlock("fang_stairs",
			() -> new BlockCustomStairs(Blocks.OAK_STAIRS));

	public static final RegistryObject<Block> FANG_SLAB = registerBlock("fang_slab",
			() -> new BlockCustomSlab(Blocks.OAK_SLAB));

	public static final RegistryObject<Block> FANG_BUTTON = registerBlock("fang_button",
			() -> new BlockCustomButton(Blocks.STONE_BUTTON));

	public static final RegistryObject<Block> FANG_DOOR = registerBlock("fang_door",
			() -> new BlockCustomDoor(Blocks.ACACIA_DOOR));

	public static final RegistryObject<Block> FANG_FENCE = registerBlock("fang_fence",
			() -> new BlockCustomFence(Blocks.ACACIA_FENCE));

	public static final RegistryObject<Block> FANG_FENCE_GATE = registerBlock("fang_fence_gate",
			() -> new BlockCustomFenceGate(Blocks.ACACIA_FENCE_GATE));

	public static final RegistryObject<Block> FANG_PRESSURE_PLATE = registerBlock("fang_pressure_plate",
			() -> new BlockCustomPressurePlate(Blocks.ACACIA_PRESSURE_PLATE));

	public static final RegistryObject<Block> FANG_TRAPDOOR = registerBlock("fang_trapdoor",
			() -> new BlockCustomTrapdoor(Blocks.ACACIA_TRAPDOOR));

	public static final RegistryObject<Block> FANG_WALL = registerBlock("fang_wall",
			() -> new BlockCustomWall(Blocks.COBBLESTONE_WALL));

	public static final RegistryObject<Block> RICE_CROP = BLOCK_REGISTER.register("rice_crop",
			() -> new BlockRice());

	public static final RegistryObject<Block> BULL_THISTLE = registerBlock("bull_thistle",
			() -> new BlockCustomFlower(MobEffects.HARM, 20));

	public static final RegistryObject<Block> POTTED_BULL_THISTLE = registerBlock("potted_bull_thistle",
			() -> new BlockCustomFlowerPot(BULL_THISTLE));

	public static final RegistryObject<Block> FIRE_FANG_LAMP = registerBlock("fire_fang_lamp",
			() -> new BlockCustomLamp());

	public static final RegistryObject<Block> FANG_CHAIR = registerBlock("fang_chair",
			() -> new BlockFangChair(Blocks.OAK_FENCE));

	private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
		RegistryObject<Block> registeredObject = BLOCK_REGISTER.register(name, supplier);
		CustomItems.ITEM_REGISTER.register(name, () -> new BlockItem(registeredObject.get(), new Item.Properties()));

		return registeredObject;
	}
}
