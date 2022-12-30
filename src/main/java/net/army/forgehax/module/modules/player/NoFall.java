package net.army.forgehax.module.modules.player;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;
import net.minecraft.client.Minecraft;

import static org.lwjgl.glfw.GLFW.*;

public class NoFall extends Module {

    private final Minecraft mc = Minecraft.getInstance();

    public NoFall() {
        super("NoFall", GLFW_KEY_N, Category.PLAYER);
    }

    @Override
    public void Update() {
        if (mc.player != null) {
            mc.player.fallDistance = 0f;
        }
    }
}
