package net.army.forgehax.module.modules.movement;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;
import net.minecraft.client.Minecraft;

import static org.lwjgl.glfw.GLFW.*;


public class Fly extends Module{
    private final Minecraft mc = Minecraft.getInstance();

    public Fly() {
        super("Fly", GLFW_KEY_F, Category.MOVEMENT);
    }

    @Override
    public void onDisable() {
        assert  mc.player != null;
        mc.player.getAbilities().mayfly = false;
        mc.player.getAbilities().flying = false;
    }

    @Override
    public void Update() {
        if (mc.player != null)
        {
            mc.player.getAbilities().mayfly = true;
            mc.player.getAbilities().flying = true;
        }
    }
}
