package net.earthlink.mlind128.lonewolf.util;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleBlockEntity extends BlockEntity implements MenuProvider {

	private final ContainerData data;
	private final ItemStackHandler handler;
	private final Map<Direction, LazyOptional<DirectionalHandler>> directions = new HashMap<>();
	private final EnergyStorage energyStorage;
	private final FluidTank fluidTank;

	private LazyOptional<IItemHandler> optionalItem = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> optionalEnergy = LazyOptional.empty();
	private LazyOptional<IFluidHandler> optionalFluid = LazyOptional.empty();

	public SimpleBlockEntity(RegistryObject<BlockEntityType> blockEntity, BlockPos pos, BlockState blockState) {
		super(blockEntity.get(), pos, blockState);
		this.data = new ContainerData() {
			@Override
			public int get(int dataIndex) {
				return getData(dataIndex);
			}

			@Override
			public void set(int dataIndex, int value) {
				setData(dataIndex, value);
			}

			@Override
			public int getCount() {
				return getDataCount();
			}
		};
		this.handler = new ItemStackHandler(getSlotCount()) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
				if (!level.isClientSide()) {
					level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
				}
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return SimpleBlockEntity.this.isItemValid(slot, stack);
			}
		};

		for (DirectionalHandler handler : this.getDirections())
			directions.put(handler.direction, LazyOptional.of(() -> handler));

		Tuple<Integer, Integer> energy = getEnergy();
		this.energyStorage = new EnergyStorage(energy.getA(), energy.getB()) {
			@Override
			public int extractEnergy(int maxExtract, boolean simulate) {
				int energy = super.extractEnergy(maxExtract, simulate);

				if (energy != 0)
					update();

				return energy;
			}

			@Override
			public int receiveEnergy(int maxReceive, boolean simulate) {
				int energy = super.receiveEnergy(maxReceive, simulate);

				if (energy != 0)
					update();

				return energy;
			}

			private void update() {
				setChanged();
				getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		};

		this.fluidTank = new FluidTank(getFluidCapacity()) {
			@Override
			protected void onContentsChanged() {
				setChanged();
				getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
			}
		};
	}

	protected abstract int getFluidCapacity();

	protected abstract Tuple<Integer, Integer> getEnergy();

	protected abstract List<DirectionalHandler> getDirections();

	/**
	 * Get the title of the inventory
	 *
	 * @return
	 */
	protected abstract String getInventoryTitle();

	/**
	 * Return the X as texture name inside textures/gui/X.png
	 *
	 * @return
	 */
	protected abstract String getTextureName();

	/**
	 * Return the block this entity represents here
	 *
	 * @return
	 */
	protected abstract Block getBlock();

	/**
	 * Get the menu type for this block entity
	 *
	 * @return
	 */
	protected abstract MenuType getMenu();

	/**
	 * Return true if the given slot can accept the given itemstack
	 *
	 * @param slot
	 * @param stack
	 * @return
	 */
	protected abstract boolean isItemValid(int slot, ItemStack stack);

	/**
	 * Load your custom data here
	 *
	 * @param tag
	 */
	protected abstract void onLoad(CompoundTag tag);

	/**
	 * Save your custom data here
	 *
	 * @param tag
	 */
	protected abstract void onSave(CompoundTag tag);

	/**
	 * Get the cutom container data count
	 *
	 * @return
	 */
	protected abstract int getDataCount();

	/**
	 * Retrieve custom data
	 *
	 * @param index
	 * @return
	 */
	protected abstract int getData(int index);

	/**
	 * Set your custom data for the given container data
	 *
	 * @param index
	 * @param value
	 */
	protected abstract void setData(int index, int value);

	/**
	 * Add your custom slots here with x-y positions
	 */
	protected abstract List<Tuple<Integer, Integer>> getSlotPositions();

	/**
	 * Render your custom GUI here
	 *
	 * @param data
	 * @param graphics
	 * @param texture
	 * @param x
	 * @param y
	 */
	protected abstract void onRender(ContainerData data, GuiGraphics graphics, ResourceLocation texture, int x, int y);

	protected abstract void onRenderLabels(GuiGraphics graphics, Font font, int x, int y, int width, int height, int mouseX, int mouseY);

	/**
	 * Called every tick, override to add your custom logic
	 *
	 * @param level
	 * @param pos
	 * @param state
	 */
	public abstract void onTick(ServerLevel level, BlockPos pos, BlockState state);

	/**
	 * Return the amount of working slots in this GUI entity
	 *
	 * @return
	 */
	private int getSlotCount() {
		return this.getSlotPositions().size();
	}

	@Override
	public final Component getDisplayName() {
		return Component.literal(this.getInventoryTitle());
	}

	@Override
	public final @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
		if (capability == ForgeCapabilities.ITEM_HANDLER) {
			if (side == null)
				return optionalItem.cast();

			if (directions.containsKey(side)) {
				if (side == Direction.DOWN || side == Direction.UP)
					return directions.get(side).cast();

				return switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
					default -> directions.get(side.getOpposite()).cast();
					case EAST -> directions.get(side.getClockWise()).cast();
					case SOUTH -> directions.get(side).cast();
					case WEST -> directions.get(side.getCounterClockWise()).cast();
				};
			}
		}

		if (capability == ForgeCapabilities.ENERGY)
			return optionalEnergy.cast();

		if (capability == ForgeCapabilities.FLUID_HANDLER)
			return optionalFluid.cast();

		return super.getCapability(capability, side);
	}

	@Nullable
	@Override
	public final AbstractContainerMenu createMenu(int id, Inventory inventory, Player pPlayer) {
		return new SimpleMenu(this.getMenu(), id, inventory, this, this.data);
	}

	@Override
	public final void onLoad() {
		super.onLoad();
		optionalItem = LazyOptional.of(() -> handler);
		optionalEnergy = LazyOptional.of(() -> energyStorage);
		optionalFluid = LazyOptional.of(() -> fluidTank);
	}

	@Override
	public final void load(@NotNull CompoundTag tag) {
		super.load(tag);

		handler.deserializeNBT(tag.getCompound("inventory"));
		energyStorage.deserializeNBT(tag.get("energy"));
		fluidTank.readFromNBT(tag.getCompound("fluid"));

		this.onLoad(tag);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();

		optionalItem.invalidate();
		optionalEnergy.invalidate();
		optionalFluid.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("inventory", handler.serializeNBT());
		tag.put("energy", energyStorage.serializeNBT());
		tag.put("fluid", fluidTank.writeToNBT(new CompoundTag()));

		onSave(tag);
		super.saveAdditional(tag);
	}

	/**
	 * Opens the menu for the player, make sure it is registered
	 *
	 * @param player
	 * @param pos
	 */
	public final void showTo(ServerPlayer player, BlockPos pos) {
		NetworkHooks.openScreen(player, this, pos);
	}

	/**
	 * Drops all items from this block entity on the floor, call this manually when the block is broken
	 */
	public final void dropContent() {
		SimpleContainer inventory = new SimpleContainer(handler.getSlots());
		for (int i = 0; i < handler.getSlots(); i++)
			inventory.setItem(i, handler.getStackInSlot(i));
		Containers.dropContents(this.getLevel(), this.getBlockPos(), inventory);
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}

	public final ItemStackHandler getHandler() {
		return handler;
	}

	public final ContainerData getData() {
		return data;
	}

	public EnergyStorage getEnergyStorage() {
		return energyStorage;
	}

	public FluidTank getFluidTank() {
		return fluidTank;
	}

	public FluidStack getFluidStack() {
		return fluidTank.getFluid();
	}

	public class DirectionalHandler implements IItemHandlerModifiable {
		private final Direction direction;
		private final int slot;
		private final boolean canInsert;

		public DirectionalHandler(Direction direction, int slot, boolean canInsert) {
			this.direction = direction;
			this.slot = slot;
			this.canInsert = canInsert;
		}

		@Override
		public int getSlots() {
			return handler.getSlots();
		}

		@Override
		public int getSlotLimit(int slot) {
			return handler.getSlotLimit(slot);
		}

		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			return this.canInsert && handler.isItemValid(slot, stack);
		}

		@Nonnull
		@Override
		public ItemStack getStackInSlot(int slot) {
			return handler.getStackInSlot(slot);
		}

		@Override
		public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
			handler.setStackInSlot(slot, stack);
		}

		@Nonnull
		@Override
		public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
			return this.canInsert ? handler.insertItem(slot, stack, simulate) : stack;
		}

		@Nonnull
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return this.slot == slot ? handler.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
		}
	}
}
