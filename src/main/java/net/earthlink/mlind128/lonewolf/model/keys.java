package net.earthlink.mlind128.lonewolf.model;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public final class keys {

	public static final String CATEGORY = "key.category.lonewolf.main";

	public static final KeyMapping TEST = new KeyMapping("key.lonewolf.test",
			KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY);
}
