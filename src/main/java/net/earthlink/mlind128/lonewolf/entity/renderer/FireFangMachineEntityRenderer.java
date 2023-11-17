package net.earthlink.mlind128.lonewolf.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.earthlink.mlind128.lonewolf.block.BlockFireFangMachine;
import net.earthlink.mlind128.lonewolf.blockentity.FireFangMachineBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class FireFangMachineEntityRenderer implements BlockEntityRenderer<FireFangMachineBlockEntity> {

	@Override
	public void render(FireFangMachineBlockEntity entity, float tick, PoseStack pose, MultiBufferSource buffer, int light, int overlay) {
		ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
		ItemStack itemStack = entity.getRenderedItem();
		Level level = entity.getLevel();

		pose.pushPose();

		pose.translate(0.5F, 1.01F, 0.5F);
		pose.scale(0.55F, 0.55F, 0.55F);

		pose.mulPose(Axis.YN.rotationDegrees(entity.getBlockState().getValue(BlockFireFangMachine.FACING).toYRot()));
		pose.mulPose(Axis.XP.rotationDegrees(270));

		BlockPos pos = entity.getBlockPos().relative(Direction.UP);
		int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
		int skyLight = level.getBrightness(LightLayer.SKY, pos);

		renderer.renderStatic(
				itemStack,
				ItemDisplayContext.FIXED,
				LightTexture.pack(blockLight, skyLight),
				OverlayTexture.NO_OVERLAY,
				pose,
				buffer,
				entity.getLevel(),
				1);

		pose.popPose();
	}
}
