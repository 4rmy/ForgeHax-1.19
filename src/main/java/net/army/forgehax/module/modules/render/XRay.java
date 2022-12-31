package net.army.forgehax.module.modules.render;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class XRay extends Module {

    public ArrayList<String> whitelist = new ArrayList<>();
    public XRay() {
        // Super mod
        super("X-Ray", GLFW_KEY_X, Category.RENDER);

        /* Add ores to list */
        whitelist.add(Blocks.COAL_ORE.getName().toString());
        whitelist.add(Blocks.DIAMOND_ORE.getName().toString());
        whitelist.add(Blocks.COPPER_ORE.getName().toString());
        whitelist.add(Blocks.EMERALD_ORE.getName().toString());
        whitelist.add(Blocks.GOLD_ORE.getName().toString());
        whitelist.add(Blocks.IRON_ORE.getName().toString());
        whitelist.add(Blocks.LAPIS_ORE.getName().toString());
        whitelist.add(Blocks.REDSTONE_ORE.getName().toString());

        whitelist.add(Blocks.DEEPSLATE_COAL_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_DIAMOND_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_COPPER_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_EMERALD_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_GOLD_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_IRON_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_LAPIS_ORE.getName().toString());
        whitelist.add(Blocks.DEEPSLATE_REDSTONE_ORE.getName().toString());

        whitelist.add(Blocks.ANCIENT_DEBRIS.getName().toString());
        whitelist.add(Blocks.NETHER_GOLD_ORE.getName().toString());
        whitelist.add(Blocks.NETHER_QUARTZ_ORE.getName().toString());
    }

    @Override
    public ArrayList<String> getWhitelist() {
        return whitelist;
    }
}
