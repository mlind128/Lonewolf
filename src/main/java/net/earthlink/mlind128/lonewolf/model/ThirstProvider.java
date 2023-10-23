package net.earthlink.mlind128.lonewolf.model;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ThirstProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	public static Capability<Thirst> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	private final LazyOptional<Thirst> optional = LazyOptional.of(this::getThirst);
	private Thirst thirst = null;

	private Thirst getThirst() {
		if (this.thirst == null)
			this.thirst = new Thirst();

		return this.thirst;
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CAPABILITY)
			return optional.cast();

		return LazyOptional.empty();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		getThirst().saveData(nbt);

		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		getThirst().loadData(nbt);
	}
}
