package net.earthlink.mlind128.lonewolf.entity.blackWolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BlackWolfRenderer extends MobRenderer<BlackWolfEntity, BlackWolfModel<BlackWolfEntity>> {

	public BlackWolfRenderer(EntityRendererProvider.Context context) {
		super(context, new BlackWolfModel<>(context.bakeLayer(CustomEntities.BLACK_WOLF_LAYER)), 0.5F);
	}

	@Override
	public void render(BlackWolfEntity entity, float yaw, float ticks, PoseStack stack, MultiBufferSource buffer, int light) {

		float scale = entity.isBaby() ? 0.75F : 1.25F;
		stack.scale(scale, scale, scale);

		super.render(entity, yaw, ticks, stack, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(BlackWolfEntity blackWolf) {
		return blackWolf.getBlackWolfType().getTexture();
	}
}
