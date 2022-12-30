package net.army.forgehax.module;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class ModuleManager {
    private final ArrayList<Module> modules;
    private final ArrayList<Module> activeModules;

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
        if (event.getAction() == InputConstants.PRESS) {
            for (Module m : this.modules) {
                if (m.key == event.getKey()) {
                    m.toggle();

                    if (m.toggled) {
                        this.activeModules.add(m);
                    } else {
                        this.activeModules.remove(m);
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

    @SubscribeEvent
    public void LivingFall(LivingFallEvent event) {
        if (this.getModuleByName("NoFall").toggled) {
            event.setDistance(0);
        }
    }
}
