package net.army.forgehax.ui;

import net.army.forgehax.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static net.army.forgehax.ForgeHax.moduleManager;

public class UI {
    private final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public void Render(RenderGuiEvent event)
    {
        int Y = 2;
        // Render Active Modules
        for (Module m : moduleManager.getActiveModules()){
            mc.font.drawShadow(event.getPoseStack(), Component.literal(m.name), 2, Y, 0xFFFFFF);
            Y += 10;
        }
    }
}
