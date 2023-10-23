package net.earthlink.mlind128.lonewolf.sound;

import net.earthlink.mlind128.lonewolf.Lonewolf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CustomSounds {

	public static final DeferredRegister<SoundEvent> SOUND_REGISTER =
			DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Lonewolf.MOD_ID);

	public static final RegistryObject<SoundEvent> DOOR_OPEN = registerSound("door_open");

	public static final RegistryObject<SoundEvent> DOOR_CLOSE = registerSound("door_close");

	public static final RegistryObject<SoundEvent> MUSIC_TEST = registerSound("music_test");

	private static RegistryObject<SoundEvent> registerSound(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(Lonewolf.MOD_ID, name);

		return SOUND_REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(resourceLocation));
	}
}
