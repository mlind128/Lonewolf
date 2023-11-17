package net.earthlink.mlind128.lonewolf.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import org.joml.Matrix4f;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Fluid renderer class
 *
 * @author https://github.com/mezz/JustEnoughItems
 */
public class FluidTankRenderer {
	private static final NumberFormat format = NumberFormat.getIntegerInstance();
	private static final int TEXTURE_SIZE = 16;
	private static final int MIN_FLUID_HEIGHT = 1;
	private final long capacity;

	public FluidTankRenderer(long capacity) {
		this.capacity = capacity;
	}

	public void render(FluidStack stack, GuiGraphics graphics, int x, int y, int width, int height) {
		RenderSystem.enableBlend();
		graphics.pose().pushPose();
		graphics.pose().translate(x, y, 0);
		drawFluid(graphics, width, height, stack);
		graphics.pose().popPose();
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.disableBlend();
	}

	private void drawFluid(GuiGraphics graphics, int width, int height, FluidStack fluidStack) {
		Fluid fluid = fluidStack.getFluid();
		if (fluid.isSame(Fluids.EMPTY))
			return;
		Minecraft minecraft = Minecraft.getInstance();
		IClientFluidTypeExtensions properties = IClientFluidTypeExtensions.of(fluidStack.getFluid());
		ResourceLocation fluidStill = properties.getStillTexture(fluidStack);
		TextureAtlasSprite sprite = minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(fluidStill);
		int fluidColor = properties.getTintColor(fluidStack);
		long amount = fluidStack.getAmount();
		long scaledAmount = (amount * height) / capacity;
		if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT)
			scaledAmount = MIN_FLUID_HEIGHT;
		if (scaledAmount > height)
			scaledAmount = height;
		this.drawTiledSprite(graphics, width, height, fluidColor, scaledAmount, sprite);
	}

	private void drawTiledSprite(GuiGraphics guiGraphics, int tiledWidth, int tiledHeight, int color, long scaledAmount, TextureAtlasSprite sprite) {
		RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
		Matrix4f matrix = guiGraphics.pose().last().pose();
		setShaderColor(color);
		final int xTileCount = tiledWidth / TEXTURE_SIZE;
		final int xRemainder = tiledWidth - (xTileCount * TEXTURE_SIZE);
		final long yTileCount = scaledAmount / TEXTURE_SIZE;
		final long yRemainder = scaledAmount - (yTileCount * TEXTURE_SIZE);
		final int yStart = tiledHeight;
		for (int xTile = 0; xTile <= xTileCount; xTile++)
			for (int yTile = 0; yTile <= yTileCount; yTile++) {
				int width = (xTile == xTileCount) ? xRemainder : TEXTURE_SIZE;
				long height = (yTile == yTileCount) ? yRemainder : TEXTURE_SIZE;
				int x = (xTile * TEXTURE_SIZE);
				int y = yStart - ((yTile + 1) * TEXTURE_SIZE);
				if (width > 0 && height > 0) {
					long maskTop = TEXTURE_SIZE - height;
					int maskRight = TEXTURE_SIZE - width;
					drawTextureWithMasking(matrix, x, y, sprite, maskTop, maskRight, 100);
				}
			}
	}

	private void setShaderColor(int color) {
		float red = (color >> 16 & 0xFF) / 255.0F;
		float green = (color >> 8 & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		float alpha = ((color >> 24) & 0xFF) / 255F;
		RenderSystem.setShaderColor(red, green, blue, alpha);
	}

	private void drawTextureWithMasking(Matrix4f matrix, float x, float y, TextureAtlasSprite sprite, long maskTop, long maskRight, float zLevel) {
		float uMin = sprite.getU0();
		float uMax = sprite.getU1();
		float vMin = sprite.getV0();
		float vMax = sprite.getV1();
		uMax = uMax - (maskRight / 16F * (uMax - uMin));
		vMax = vMax - (maskTop / 16F * (vMax - vMin));
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, x, y + 16, zLevel).uv(uMin, vMax).endVertex();
		buffer.vertex(matrix, x + 16 - maskRight, y + 16, zLevel).uv(uMax, vMax).endVertex();
		buffer.vertex(matrix, x + 16 - maskRight, y + maskTop, zLevel).uv(uMax, vMin).endVertex();
		buffer.vertex(matrix, x, y + maskTop, zLevel).uv(uMin, vMin).endVertex();
		tessellator.end();
	}

	public List<Component> getTooltip(FluidStack stack) {
		List<Component> tooltip = new ArrayList<>();
		Fluid fluidType = stack.getFluid();
		if (fluidType.isSame(Fluids.EMPTY))
			return tooltip;
		tooltip.add(stack.getDisplayName());
		long volume = (stack.getAmount() * 1000L) / FluidType.BUCKET_VOLUME;
		tooltip.add(Component.literal("§f" + format.format(volume) + "§8/§f" + format.format(capacity)));
		return tooltip;
	}
}
