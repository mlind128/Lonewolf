package net.earthlink.mlind128.lonewolf.creativetab;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CustomCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB_REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lonewolf.MOD_ID);

	public static final RegistryObject<CreativeModeTab> WOLF_FANG = CREATIVE_TAB_REGISTER.register("fang_tab",
			() -> CreativeModeTab.builder()
					.title(Component.literal("Wolf Fang"))
					.icon(() -> new ItemStack(CustomItems.FANG.get()))
					.displayItems((parameters, output) -> {
						CustomBlocks.BLOCK_REGISTER.getEntries().forEach((block) -> {
							output.accept(block.get().asItem());
						});

						CustomItems.ITEM_REGISTER.getEntries().forEach((Item) -> {
							output.accept(Item.get());
						});
						//output.accept(CustomItems.FANG.get());
						//output.accept(CustomBlocks.WOLF_FANG_BLOCK.get());
						//output.accept(CustomBlocks.WOLF_FANG_STAIRS.get());
						//output.accept(CustomBlocks.WOLF_FANG_SLAB.get());
					})
					.build());
}
