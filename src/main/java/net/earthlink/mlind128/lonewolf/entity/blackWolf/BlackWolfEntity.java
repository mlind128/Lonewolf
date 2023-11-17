package net.earthlink.mlind128.lonewolf.entity.blackWolf;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.earthlink.mlind128.lonewolf.entity.CustomEntities;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

public class BlackWolfEntity extends TamableAnimal {

	private static final EntityDataAccessor<Boolean> DATA_INTERESTED_ID = SynchedEntityData.defineId(BlackWolfEntity.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Byte> DATA_TYPE_ID = SynchedEntityData.defineId(BlackWolfEntity.class, EntityDataSerializers.BYTE);
	public static final Predicate<LivingEntity> PREY_SELECTOR = (p_289448_) -> {
		EntityType<?> entitytype = p_289448_.getType();
		return entitytype == EntityType.SHEEP || entitytype == EntityType.RABBIT || entitytype == EntityType.FOX;
	};

	private final AnimationState idleAnimationState = new AnimationState();
	private int idleAnimationTicks;
	private final AnimationState attackAnimationState = new AnimationState();
	private int attackAnimationTicks;
	private final AnimationState sitAnimationState = new AnimationState();
	private int warningSoundTicks;

	public BlackWolfEntity(EntityType type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new BlackWolfEntity.WolfPanicGoal(1.5D));
		this.goalSelector.addGoal(3, new BlackWolfMeleeAttackGoal());
		this.goalSelector.addGoal(4, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new TemptGoal(this, 1.25D, Ingredient.of(Items.BONE), false));
		this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
		this.targetSelector.addGoal(4, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
		this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, AbstractSkeleton.class, false));
	}

	PolarBear p;
	Wolf w;
	Creeper c;

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, (double) 0.3F)
				.add(Attributes.MAX_HEALTH, 10.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	private void playWarningSound() {
		if (this.warningSoundTicks <= 0) {
			this.playSound(SoundEvents.WOLF_GROWL, 1.0F, this.getVoicePitch());
			this.warningSoundTicks = 40;
		}

	}

	public static boolean checkSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8;
	}

	public enum Type {
		NORMAL(new ResourceLocation(Lonewolf.MOD_ID, "textures/entity/black_wolf/black_wolf.png")),
		thunder(new ResourceLocation(Lonewolf.MOD_ID, "textures/entity/black_wolf/thunder_wolf.png"));

		private final ResourceLocation texture;

		Type(ResourceLocation texture) {
			this.texture = texture;
		}

		public ResourceLocation getTexture() {
			return texture;
		}
	}

	class BlackWolfMeleeAttackGoal extends MeleeAttackGoal {

		public BlackWolfMeleeAttackGoal() {
			super(BlackWolfEntity.this, 1.25D, true);
		}

		@Override
		protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
			double d0 = this.getAttackReachSqr(pEnemy);
			if (pDistToEnemySqr <= d0 && this.isTimeToAttack()) {
				this.resetAttackCooldown();
				this.mob.doHurtTarget(pEnemy);
				BlackWolfEntity.this.setIsInterested(false);
			} else if (pDistToEnemySqr <= d0 * 2.0D) {
				if (this.isTimeToAttack()) {
					BlackWolfEntity.this.setIsInterested(false);
					this.resetAttackCooldown();
				}

				if (this.getTicksUntilNextAttack() <= 30) {
					BlackWolfEntity.this.setIsInterested(true);
					BlackWolfEntity.this.playWarningSound();
				}
			} else {
				this.resetAttackCooldown();
				BlackWolfEntity.this.setIsInterested(false);
			}

		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			BlackWolfEntity.this.setIsInterested(false);
			super.stop();
		}

		@Override
		protected double getAttackReachSqr(LivingEntity pAttackTarget) {
			return (double) (4.0F + pAttackTarget.getBbWidth());
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_INTERESTED_ID, false);
		this.entityData.define(DATA_TYPE_ID, (byte) 0);
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putByte("Type", this.entityData.get(DATA_TYPE_ID));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		this.entityData.set(DATA_TYPE_ID, tag.getByte("Type"));
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.WOLF_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.WOLF_DEATH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
		return pSize.height * 0.8F;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
	 * the animal type)
	 */
	@Override
	public boolean isFood(ItemStack pStack) {
		Item item = pStack.getItem();
		return item.isEdible() && pStack.getFoodProperties(this).isMeat();
	}

	/**
	 * Will return how many at most can spawn in a chunk at once.
	 */
	@Override
	public int getMaxSpawnClusterSize() {
		return 8;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob pOtherParent) {
		BlackWolfEntity blackWolf = CustomEntities.BLACK_WOLF.get().create(level);

		if (blackWolf != null) {
			UUID uuid = this.getOwnerUUID();
			if (uuid != null) {
				blackWolf.setOwnerUUID(uuid);
				blackWolf.setTame(true);
			}
		}
		return blackWolf;
	}

	public void setIsInterested(boolean pIsInterested) {
		this.entityData.set(DATA_INTERESTED_ID, pIsInterested);
	}

	public boolean isInterested() {
		return this.entityData.get(DATA_INTERESTED_ID);
	}

	public Type getBlackWolfType() {
		return Type.values()[this.entityData.get(DATA_TYPE_ID)];
	}

	public void setBlackWolfType(Type type) {
		this.entityData.set(DATA_TYPE_ID, (byte) type.ordinal());
	}

	@Override
	public boolean canBeLeashed(Player pPlayer) {
		return super.canBeLeashed(pPlayer);
	}

	@Override
	public Vec3 getLeashOffset() {
		return new Vec3(0.0D, (double) (0.6F * this.getEyeHeight()), (double) (this.getBbWidth() * 0.4F));
	}

	class WolfPanicGoal extends PanicGoal {
		public WolfPanicGoal(double pSpeedModifier) {
			super(BlackWolfEntity.this, pSpeedModifier);
		}

		@Override
		protected boolean shouldPanic() {
			return this.mob.isFreezing() || this.mob.isOnFire();
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			if (this.idleAnimationTicks <= 0) {
				this.idleAnimationTicks = this.random.nextInt(60) + 80;
				this.idleAnimationState.start(this.tickCount);

			} else
				--this.idleAnimationTicks;

			if (this.isInterested())
				if (this.attackAnimationTicks <= 0) {
					this.attackAnimationTicks = 20;
					this.attackAnimationState.start(this.tickCount);

				} else
					--this.attackAnimationTicks;

			if (this.isInSittingPose())
				this.sitAnimationState.startIfStopped(this.tickCount);
			else
				this.sitAnimationState.stop();
		}

		if (this.warningSoundTicks > 0) {
			--this.warningSoundTicks;
		}
	}

	@Override
	protected void updateWalkAnimation(float pPartialTick) {

		float f;
		if (this.getPose() == Pose.STANDING) {
			f = Math.min(pPartialTick * 6.0F, 1.0F);
		} else {
			f = 0.0F;
		}

		this.walkAnimation.update(f, 0.2F);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance diff, MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {

		this.setBlackWolfType(Util.getRandom(Type.values(), this.random));
		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(this.getBlackWolfType() == Type.thunder ? 4.0D : 2.0D);

		return super.finalizeSpawn(level, diff, reason, data, tag);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand pHand) {
		ItemStack itemstack = player.getItemInHand(pHand);
		Item item = itemstack.getItem();
		if (this.level().isClientSide) {
			boolean flag = this.isOwnedBy(player) || this.isTame() || itemstack.is(Items.BONE) && !this.isTame();
			return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else if (this.isTame()) {
			if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
				this.heal((float) itemstack.getFoodProperties(this).getNutrition());
				if (!player.getAbilities().instabuild) {
					itemstack.shrink(1);
				}

				this.gameEvent(GameEvent.EAT, this);
				return InteractionResult.SUCCESS;
			} else {

				InteractionResult interactionresult = super.mobInteract(player, pHand);

				if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {

					if (!this.isOrderedToSit() && !player.isCrouching()) {
						player.startRiding(this);

						return InteractionResult.CONSUME;
					}

					this.setOrderedToSit(!this.isOrderedToSit());
					this.jumping = false;
					this.navigation.stop();
					this.setTarget((LivingEntity) null);
					return InteractionResult.SUCCESS;
				} else {
					return interactionresult;
				}
			}
		} else if (itemstack.is(Items.BONE)) {
			if (!player.getAbilities().instabuild) {
				itemstack.shrink(1);
			}

			if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
				this.tame(player);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				//this.setOrderedToSit(true);
				this.level().broadcastEntityEvent(this, (byte) 7);
			} else {
				this.level().broadcastEntityEvent(this, (byte) 6);
			}

			return InteractionResult.SUCCESS;
		} else {
			return super.mobInteract(player, pHand);
		}
	}

	Pig pig;

	@Override
	public LivingEntity getControllingPassenger() {
		return this.getFirstPassenger() instanceof Player player ? player : null;
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
		Direction direction = this.getMotionDirection();
		if (direction.getAxis() == Direction.Axis.Y) {
			return super.getDismountLocationForPassenger(entity);
		} else {
			int[][] offsets = DismountHelper.offsetsForDirection(direction);
			BlockPos blockpos = this.blockPosition();
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (Pose pose : entity.getDismountPoses()) {
				AABB aabb = entity.getLocalBoundsForPose(pose);

				for (int[] aint1 : offsets) {
					blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY(), blockpos.getZ() + aint1[1]);
					double d0 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
					if (DismountHelper.isBlockFloorValid(d0)) {
						Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
						if (DismountHelper.canDismountTo(this.level(), entity, aabb.move(vec3))) {
							entity.setPose(pose);
							return vec3;
						}
					}
				}
			}

			return super.getDismountLocationForPassenger(entity);
		}
	}

	@Override
	protected Vec3 getRiddenInput(Player pPlayer, Vec3 pTravelVector) {
		if (this.onGround() && this.isInterested())
			return Vec3.ZERO;

		else {
			float motionX = pPlayer.xxa * 0.5F;
			float motionZ = pPlayer.zza;

			if (motionZ <= 0.0F)
				motionZ *= 0.25F;

			return new Vec3(motionX, 0.0D, motionZ);
		}
	}

	@Override
	protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
		super.tickRidden(pPlayer, pTravelVector);

		this.setRot(pPlayer.getYRot(), pPlayer.getXRot() * 0.5F);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
	}

	@Override
	protected float getRiddenSpeed(Player pPlayer) {
		double multiplier = Minecraft.getInstance().options.keySprint.isDown() ? 0.5D : 0.25D;

		return (float) (this.getAttributeValue(Attributes.MOVEMENT_SPEED) * multiplier);
	}

	@Override
	protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
		super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
		ItemEntity itementity = this.spawnAtLocation(CustomItems.FANG.get());
		if (itementity != null || !this.isTame()) {
			itementity.setExtendedLifetime();
		}
	}

	public AnimationState getIdleAnimationState() {
		return idleAnimationState;
	}

	public AnimationState getAttackAnimationState() {
		return attackAnimationState;
	}

	public AnimationState getSitAnimationState() {
		return sitAnimationState;
	}
}
