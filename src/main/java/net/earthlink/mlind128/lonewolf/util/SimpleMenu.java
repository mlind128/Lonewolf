package net.earthlink.mlind128.lonewolf.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public final class SimpleMenu extends AbstractContainerMenu {

	private final SimpleBlockEntity blockEntity;
	private final Level level;
	private final ContainerData data;

	public SimpleMenu(MenuType menu, int id, Inventory inv, FriendlyByteBuf extraData) {
		this(menu, id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
	}

	public SimpleMenu(MenuType menu, int id, Inventory inv, BlockEntity entity, ContainerData data) {
		super(menu, id);
		this.blockEntity = (SimpleBlockEntity) entity;
		this.level = inv.player.level();
		this.data = data;
		for (int row = 0; row < 9; ++row)
			for (int column = 0; column < 3; ++column)
				this.addSlot(new Slot(inv, row + column * 9 + 9, 8 + row * 18, 84 + column * 18));
		for (int hotbar = 0; hotbar < 9; ++hotbar)
			this.addSlot(new Slot(inv, hotbar, 8 + hotbar * 18, 142));
		blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
			int index = 0;
			for (Tuple<Integer, Integer> slot : ((SimpleBlockEntity) entity).getSlotPositions())
				this.addSlot(new SlotItemHandler(handler, index++, slot.getA(), slot.getB()));
		});
		this.addDataSlots(data);
	}

	@Override
	public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
		int endSlot = 9 + (9 * 3);

		Slot slot = slots.get(i);
		int slotCount = this.blockEntity.getSlotPositions().size();

		if (!slot.hasItem())
			return ItemStack.EMPTY;
		ItemStack source = slot.getItem();
		ItemStack copy = source.copy();
		if (i < endSlot) {
			if (!this.moveItemStackTo(source, endSlot, endSlot + slotCount, false))
				return ItemStack.EMPTY;  // EMPTY_ITEM
		} else if (i < endSlot + slotCount) {
			if (!this.moveItemStackTo(source, 0, endSlot, false))
				return ItemStack.EMPTY;
		} else
			return ItemStack.EMPTY;
		if (source.getCount() == 0)
			slot.set(ItemStack.EMPTY);
		else
			slot.setChanged();
		slot.onTake(player, source);
		return copy;
	}

	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, this.blockEntity.getBlock());
	}

	public static class Screen extends AbstractContainerScreen<SimpleMenu> {
		private final ResourceLocation texture;

		public Screen(SimpleMenu menu, Inventory inventory, Component title) {
			super(menu, inventory, title);
			this.texture = new ResourceLocation(Lonewolf.MOD_ID, "textures/gui/" + menu.blockEntity.getTextureName() + ".png");
		}

		@Override
		protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, texture);
			int x = (width - imageWidth) / 2;
			int y = (height - imageHeight) / 2;
			guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

			this.menu.blockEntity.onRender(this.menu.data, guiGraphics, texture, x, y);
		}

		@Override
		public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
			this.renderBackground(graphics);
			super.render(graphics, mouseX, mouseY, delta);
			this.renderTooltip(graphics, mouseX, mouseY);
		}

		@Override
		protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
			super.renderLabels(guiGraphics, mouseX, mouseY);

			int x = (width - imageWidth) / 2;
			int y = (height - imageHeight) / 2;

			this.menu.blockEntity.onRenderLabels(guiGraphics, font, x, y, width, height, mouseX, mouseY);
		}
	}
}
