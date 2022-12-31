package net.army.forgehax.module.modules.render;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;

import static org.lwjgl.glfw.GLFW.*;

public class XRay extends Module {
    public XRay() {
        super("X-Ray", GLFW_KEY_X, Category.RENDER);
    }
}
