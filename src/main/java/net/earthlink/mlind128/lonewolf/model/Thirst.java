package net.earthlink.mlind128.lonewolf.model;

import net.earthlink.mlind128.lonewolf.ClientPlayerData;
import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public final class Thirst {

	private static final ResourceLocation THIRST
			= new ResourceLocation(Lonewolf.MOD_ID, "textures/hud/thirst.png");

	private static final ResourceLocation THIRST_FULL
			= new ResourceLocation(Lonewolf.MOD_ID, "textures/hud/thirst_full.png");

	public static final IGuiOverlay HUD = new IGuiOverlay() {
		@Override
		public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
			Player player = gui.getMinecraft().player;

			if (player == null || player.isCreative())
				return;

			int size = 12;
			int x = (screenWidth / 2) - 93 + 101;
			int y = screenHeight - 51;

			for (int i = 0; i < 10; i++) {
				ResourceLocation texture = ClientPlayerData.thirst > i ? THIRST_FULL : THIRST;

				guiGraphics.blit(
						texture,
						x + (i * 8),
						y,
						50,
						0, 0,
						size, size, size, size
				);
			}
		}
	};

	private int thirst;

	public int getThirst() {
		return thirst;
	}

	public void setThirst(int thirst) {
		this.thirst = Math.min(Math.max(thirst, 0), 10);
	}

	public void saveData(CompoundTag nbt) {
		nbt.putInt("thirst", thirst);
	}

	public void loadData(CompoundTag nbt) {
		thirst = nbt.getInt("thirst");
	}
}
