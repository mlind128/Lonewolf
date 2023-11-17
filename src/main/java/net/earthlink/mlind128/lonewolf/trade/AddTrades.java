package net.earthlink.mlind128.lonewolf.trade;

import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public final class AddTrades {

	public static void music(List<VillagerTrades.ItemListing> trades) {

		trades.add(new VillagerTrades.ItemListing() {

			@Override
			public MerchantOffer getOffer(Entity trader, RandomSource random) {
				return new MerchantOffer(
						new ItemStack(Items.EMERALD, 2),
						new ItemStack(CustomItems.MUSIC_DISK_TEST.get()),
						3,
						10,
						0.05F);
			}
		});
	}

	public static void spawner(List<VillagerTrades.ItemListing> trades) {

		trades.add(new VillagerTrades.ItemListing() {

			@Override
			public MerchantOffer getOffer(Entity trader, RandomSource random) {
				return new MerchantOffer(
						new ItemStack(Items.EMERALD, 6),
						new ItemStack(Items.SPAWNER),
						1,
						30,
						0.05F);
			}
		});
	}

	public static void chicken(List<VillagerTrades.ItemListing> trades) {

		trades.add(new VillagerTrades.ItemListing() {

			@Override
			public MerchantOffer getOffer(Entity trader, RandomSource random) {
				return new MerchantOffer(
						new ItemStack(Items.WHEAT_SEEDS, 2),
						new ItemStack(Items.CHICKEN_SPAWN_EGG),
						5,
						30,
						0.05F);
			}
		});
	}
}
