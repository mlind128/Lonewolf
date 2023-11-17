package net.earthlink.mlind128.lonewolf.menu;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.util.SimpleMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomMenu {

	public static final DeferredRegister<MenuType<?>> MENU_REGISTER =
			DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lonewolf.MOD_ID);

	public static final RegistryObject<MenuType> FIRE_FANG_MACHINE = MENU_REGISTER.register("fire_fang_machine_menu",
			() -> IForgeMenuType.create(new IContainerFactory<>() {
				@Override
				public AbstractContainerMenu create(int windowId, Inventory inv, FriendlyByteBuf data) {
					return new SimpleMenu(CustomMenu.FIRE_FANG_MACHINE.get(), windowId, inv, data);
				}
			}));

}
