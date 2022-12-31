package net.army.forgehax.module;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleManager {
    private final ArrayList<Module> modules;
    private final ArrayList<Module> activeModules;
    private static final Minecraft mc = Minecraft.getInstance();

    private static class ModuleComparator implements Comparator<Module> {

        @Override
        public int compare(Module a, Module b) {
            if (mc.font.width(a.name) > mc.font.width(b.name)) {
                return -1;
            }
            if (mc.font.width(a.name) < mc.font.width(b.name)) {
                return 1;
            }
            return 0;
        }
    }

    public ModuleManager() {
        this.modules = new ArrayList<>();
        this.activeModules = new ArrayList<>();
    }

    public ArrayList<Module> getModules() {
        return this.modules;
    }

    public Module getModuleByName(String name) {
        for (Module m : this.modules) {
            if (m.name.equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Module> getModulesByCategory(Category c) {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : this.modules) {
            if (m.category == c) {
                mods.add(m);
            }
        }
        return mods;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public ArrayList<Module> getActiveModules() {
        return activeModules;
    }

    @SubscribeEvent
    public void onKeyDown(InputEvent.Key event) {
        if (true) {
            if (event.getAction() == InputConstants.PRESS) {
                for (Module m : this.modules) {
                    if (m.key == event.getKey()) {
                        m.toggle();

                        if (m.toggled) {
                            this.activeModules.add(m);
                            this.activeModules.sort(new ModuleComparator());
                        } else {
                            this.activeModules.remove(m);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void PlayerUpdate(TickEvent.PlayerTickEvent event) {
        for (Module m : this.activeModules) {
            m.Update();
        }
    }

    /*@SubscribeEvent
    public void LivingFall(LivingFallEvent event) {
        if (this.getModuleByName("NoFall").toggled) {
            event.setDistance(0);
        }
    }*/

    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
        if (this.getModuleByName("X-Ray").toggled) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
                ClientLevel world = mc.level;
                if (mc.level == null)
                    return;

                int viewDistance = Minecraft.getInstance().options.renderDistance().get();

                for (int x = -viewDistance; x <= viewDistance; x++) {
                    for (int y = 0; y < 256; y++) {
                        for (int z = -viewDistance; z <= viewDistance; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            BlockState blockState = world.getBlockState(pos);
                            Block block = blockState.getBlock();

                            // Check if the block's translation key does not contain "ore"
                            if (!block.getName().toString().toLowerCase().contains("ore")) {
                                // Set the block to be invisible

                            }
                        }
                    }
                }
            }
        }
    }
}
