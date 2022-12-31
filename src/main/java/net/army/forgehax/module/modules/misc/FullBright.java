package net.army.forgehax.module.modules.misc;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;
import net.minecraft.client.Minecraft;

import static org.lwjgl.glfw.GLFW.*;

public class FullBright extends Module {
    private double currentSetting;

    public FullBright() {
        super("FullBright", GLFW_KEY_O, Category.MISC);
        this.currentSetting = Minecraft.getInstance().options.gamma().get();
    }

    @Override
    public void onEnable() {
        Minecraft.getInstance().options.gamma().set(1.0);
    }

    @Override
    public void onDisable() {
        Minecraft.getInstance().options.gamma().set(this.currentSetting);
    }
}
