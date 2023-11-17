package net.earthlink.mlind128.lonewolf.blockentity;

import net.earthlink.mlind128.lonewolf.block.CustomBlocks;
import net.earthlink.mlind128.lonewolf.item.CustomItems;
import net.earthlink.mlind128.lonewolf.menu.CustomMenu;
import net.earthlink.mlind128.lonewolf.util.FluidTankRenderer;
import net.earthlink.mlind128.lonewolf.util.SimpleBlockEntity;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FireFangMachineBlockEntity extends SimpleBlockEntity {

	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	private static final int ENERGY_SLOT = 2;
	private static final int FLUID_SLOT = 3;

	private FluidTankRenderer fluidRenderer;
	private int progressTicks = 0;
	private int maxProgressTicks = 20;

	public FireFangMachineBlockEntity(BlockPos pos, BlockState blockState) {
		super(CustomBlockEntities.FIRE_FANG_MACHINE, pos, blockState);

		this.fluidRenderer = new FluidTankRenderer(this.getFluidCapacity());
	}

	@Override
	protected String getInventoryTitle() {
		return "Fire Fang Machine";
	}

	@Override
	protected String getTextureName() {
		return "fire_fang_machine";
	}

	@Override
	protected Block getBlock() {
		return CustomBlocks.FIRE_FANG_MACHINE.get();
	}

	@Override
	protected MenuType getMenu() {
		return CustomMenu.FIRE_FANG_MACHINE.get();
	}

	@Override
	protected boolean isItemValid(int slot, ItemStack stack) {

		if (slot == OUTPUT_SLOT)
			return false;

		if (slot == INPUT_SLOT)
			return stack.getItem() == CustomItems.FANG.get();

		if (slot == ENERGY_SLOT)
			return stack.getItem() == Items.NETHER_STAR;

		if (slot == FLUID_SLOT)
			return stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET;

		return true;
	}

	@Override
	protected void onLoad(CompoundTag tag) {
		progressTicks = tag.getInt("progressTicks");
	}

	@Override
	protected void onSave(CompoundTag tag) {
		tag.putInt("progressTicks", progressTicks);
	}

	@Override
	protected int getDataCount() {
		return 2;
	}

	@Override
	protected int getData(int index) {

		if (index == 0)
			return progressTicks;

		else if (index == 1)
			return maxProgressTicks;

		return 0;
	}

	@Override
	protected void setData(int index, int value) {
		if (index == 0)
			progressTicks = value;

		else if (index == 1)
			maxProgressTicks = value;
	}

	@Override
	protected List<DirectionalHandler> getDirections() {
		return Arrays.asList(
				new DirectionalHandler(Direction.UP, INPUT_SLOT, true),
				new DirectionalHandler(Direction.NORTH, INPUT_SLOT, true),
				new DirectionalHandler(Direction.WEST, INPUT_SLOT, true),
				new DirectionalHandler(Direction.SOUTH, INPUT_SLOT, true),
				new DirectionalHandler(Direction.EAST, INPUT_SLOT, true),

				new DirectionalHandler(Direction.DOWN, OUTPUT_SLOT, false)
		);
	}

	@Override
	protected int getFluidCapacity() {
		return 2000;
	}

	@Override
	protected Tuple<Integer, Integer> getEnergy() {
		return new Tuple<>(100, 100);
	}

	@Override
	protected List<Tuple<Integer, Integer>> getSlotPositions() {
		return Arrays.asList(
				new Tuple<>(56, 35),
				new Tuple<>(116, 35),
				new Tuple<>(20, 54),
				new Tuple<>(152, 60)
		);
	}

	@Override
	protected void onRender(ContainerData data, GuiGraphics graphics, ResourceLocation texture, int x, int y) {
		if (data.get(0) > 0) {
			int arrowSize = 24;
			int current = data.get(0);
			int max = data.get(1);

			int progress = current * arrowSize / max;

			graphics.blit(texture, x + 80, y + 35, 176, 14, progress, 17);
		}

		{
			this.fluidRenderer.render(this.getFluidStack(), graphics, x + 152, y + 10, 16, 45);
		}

		{
			int width = 8;
			int height = 50;
			int stored = (int) ((this.getEnergyStorage().getEnergyStored() / (float) this.getEnergyStorage().getMaxEnergyStored()) * height);

			x = x + 8;
			y = y + 20;

			graphics.fillGradient(
					x,
					y + height - stored,
					x + width,
					y + height,
					Color.ORANGE.getRGB(),
					Color.RED.getRGB());
		}

	}

	@Override
	protected void onRenderLabels(GuiGraphics graphics, Font font, int x, int y, int width, int height, int mouseX, int mouseY) {

		int offsetX = x + 8;
		int offsetY = y + 20;

		if (mouseX >= offsetX && mouseX <= offsetX + 8 && mouseY >= offsetY && mouseY <= offsetY + 50) {
			Component tooltip = Component.literal(this.getEnergyStorage().getEnergyStored()
					+ "/" + this.getEnergyStorage().getMaxEnergyStored() + "Energy");

			graphics.renderTooltip(font, List.of(tooltip), Optional.empty(), mouseY - x, mouseY - y);
		}

		offsetX = x + 152;
		offsetY = y + 10;

		if (mouseX >= offsetX && mouseX <= offsetX + 16 && mouseY >= offsetY && mouseY <= offsetY + 45) {
			graphics.renderTooltip(font, this.fluidRenderer.getTooltip(this.getFluidStack()), Optional.empty(), mouseY - x, mouseY - y);
		}
	}

	@Override
	public void onTick(ServerLevel level, BlockPos pos, BlockState state) {
		ItemStack input = this.getHandler().getStackInSlot(INPUT_SLOT);
		ItemStack output = this.getHandler().getStackInSlot(OUTPUT_SLOT);
		ItemStack energy = this.getHandler().getStackInSlot(ENERGY_SLOT);
		ItemStack fluid = this.getHandler().getStackInSlot(FLUID_SLOT);

		if (input.getItem() == CustomItems.FANG.get()
				&& (output.isEmpty() || (output.is(CustomItems.FIRE_FANG.get())) && output.getCount() < output.getMaxStackSize())) {

			if (this.getEnergyStorage().getEnergyStored() > 0 && this.getFluidTank().getFluidAmount() > 0) {
				this.getEnergyStorage().extractEnergy(1, false);

				if (this.progressTicks++ >= this.maxProgressTicks) {
					this.getHandler().extractItem(INPUT_SLOT, 1, false);
					this.getHandler().setStackInSlot(OUTPUT_SLOT, new ItemStack(CustomItems.FIRE_FANG.get(), output.getCount() + 1));
					this.progressTicks = 0;
					this.getFluidTank().drain(100, IFluidHandler.FluidAction.EXECUTE);

					setChanged(level, pos, state);
				}

			} //else
			//this.progressTicks = 0;

		} else
			this.progressTicks = 0;

		if (!energy.isEmpty() && energy.getItem() == Items.NETHER_STAR && getEnergyStorage().getEnergyStored() == 0) {
			getEnergyStorage().receiveEnergy(100, false);

			this.getHandler().extractItem(ENERGY_SLOT, 1, false);
		}

		if (!fluid.isEmpty() && fluid.getItem() == Items.WATER_BUCKET && this.getFluidTank().getFluidAmount() < this.getFluidCapacity()) {
			this.getFluidTank().fill(new FluidStack(Fluids.WATER, 500), IFluidHandler.FluidAction.EXECUTE);

			this.getHandler().extractItem(FLUID_SLOT, 1, false);
			this.getHandler().setStackInSlot(FLUID_SLOT, new ItemStack(Items.BUCKET));
		}
	}

	public ItemStack getRenderedItem() {
		ItemStack item = this.getHandler().getStackInSlot(OUTPUT_SLOT);

		return item.isEmpty() ? this.getHandler().getStackInSlot(INPUT_SLOT) : item;
	}
}
