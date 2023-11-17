package net.earthlink.mlind128.lonewolf.entity.werewolf;

import com.mojang.blaze3d.vertex.PoseStack;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WerewolfRenderer extends MobRenderer<WerewolfEntity, WerewolfModel<WerewolfEntity>> {

	public WerewolfRenderer(EntityRendererProvider.Context context) {
		super(context, new WerewolfModel<>(context.bakeLayer(CustomEntities.WEREWOLF_LAYER)), 0.5F);
	}

	@Override
	public void render(WerewolfEntity entity, float yaw, float ticks, PoseStack stack, MultiBufferSource buffer, int light) {

		float scale = entity.isBaby() ? 0.75F : 1.25F;
		stack.scale(scale, scale, scale);

		super.render(entity, yaw, ticks, stack, buffer, light);
	}

	@Override
	public ResourceLocation getTextureLocation(WerewolfEntity blackWolf) {
		return blackWolf.getWerewolfType().getTexture();
	}
}
