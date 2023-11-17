package net.earthlink.mlind128.lonewolf.entity.werewolf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WerewolfModel<T extends WerewolfEntity> extends HierarchicalModel<T> {

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "warewolf"), "main");
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart mane;
	private final ModelPart leg1;
	private final ModelPart leg2;
	private final ModelPart leg3;
	private final ModelPart leg4;
	private final ModelPart tail;

	public WerewolfModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.mane = root.getChild("mane");
		this.leg1 = root.getChild("leg1");
		this.leg2 = root.getChild("leg2");
		this.leg3 = root.getChild("leg3");
		this.leg4 = root.getChild("leg4");
		this.tail = root.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -5.0F, -4.0F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(18, 16).addBox(2.0F, -7.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(18, 16).addBox(-2.0F, -7.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(2, 12).addBox(-0.5F, -2.02F, -7.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 7.5F, -4.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(49, 21).addBox(0.0F, -0.5F, -6.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 2.0F));

		PartDefinition body_rotation = body.addOrReplaceChild("body_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition body_rotation_r1 = body_rotation.addOrReplaceChild("body_rotation_r1", CubeListBuilder.create().texOffs(20, 16).addBox(-3.0F, -12.7705F, -12.3221F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 1.0F, -1.0908F, 0.0F, 0.0F));

		PartDefinition mane = partdefinition.addOrReplaceChild("mane", CubeListBuilder.create(), PartPose.offset(-1.0F, 14.0F, 2.0F));

		PartDefinition mane_rotation = mane.addOrReplaceChild("mane_rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition mane_rotation_r1 = mane_rotation.addOrReplaceChild("mane_rotation_r1", CubeListBuilder.create().texOffs(23, 2).addBox(-4.0F, -14.4237F, -5.0535F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 2.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(2, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 16.0F, 1.0F));

		PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(2, 20).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 16.0F, 1.0F));

		PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(2, 20).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 9.0F, -4.0F));

		PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(2, 20).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 9.0F, -4.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 3.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(11, 20).addBox(-1.0F, -1.3597F, -0.9176F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 1.0F, 0.829F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		this.animateWalk(WEREWOLF_WALK, limbSwing, limbSwingAmount, 2f, 2.45f);
		this.animate(entity.getIdleAnimationState(), WEREWOLF_IDLE, ageInTicks, 1f);
		this.animate(entity.getAttackAnimationState(), WEREWOLF_ATTACK, ageInTicks, 1f);
		this.animate(entity.getSitAnimationState(), WEREWOLF_SIT, ageInTicks, 1f);
	}

	private void applyHeadRotation(T entity, float netHeadYaw, float headPitch, float ageInTicks) {
		netHeadYaw = Mth.clamp(netHeadYaw, -30.0F, 30.0F);
		headPitch = Mth.clamp(headPitch, -25.0F, 45.0F);

		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
	}


	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		mane.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg3.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leg4.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public static final AnimationDefinition WEREWOLF_WALK = AnimationDefinition.Builder.withLength(1f).looping()
			.addAnimation("leg1",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg3",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg4",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(-20f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("tail",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, -20f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 20f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition WEREWOLF_ATTACK = AnimationDefinition.Builder.withLength(0.5f)
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, -2f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body_rotation",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("mane",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("mane_rotation",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg3",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, -1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg4",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, -1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.125f, KeyframeAnimations.degreeVec(30f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.375f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition WEREWOLF_IDLE = AnimationDefinition.Builder.withLength(1f).looping()
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 0f, -1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("mane",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -1f, -1f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("jaw",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0.25f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(15f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
	public static final AnimationDefinition WEREWOLF_SIT = AnimationDefinition.Builder.withLength(0.5f)
			.addAnimation("head",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 8f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("body_rotation",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -6f, 9f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("mane_rotation",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 8f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg1",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -7f, 9f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg1",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg2",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -7f, 9f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg2",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(-90f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg3",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 8f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("leg4",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -5f, 8f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("tail",
					new AnimationChannel(AnimationChannel.Targets.POSITION,
							new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -6f, 9f),
									AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("tail",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
}
