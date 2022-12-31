package net.army.forgehax;

import com.mojang.logging.LogUtils;
import net.army.forgehax.module.ModuleManager;
import net.army.forgehax.module.modules.misc.*;
import net.army.forgehax.module.modules.movement.*;
import net.army.forgehax.module.modules.player.*;
import net.army.forgehax.module.modules.render.*;
import net.army.forgehax.ui.UI;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ForgeHax.MOD_ID)
public class ForgeHax {
    public static final String MOD_ID = "forgehax";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final ModuleManager moduleManager = new ModuleManager();

    public ForgeHax()
    {
        // ADD HACK MODULES
        //combat
        //player
        moduleManager.addModule(new NoFall());
        //movement
        moduleManager.addModule(new Fly());
        //render
        moduleManager.addModule(new XRay());
        //misc
        moduleManager.addModule(new FullBright());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(new UI());
        MinecraftForge.EVENT_BUS.register(moduleManager);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
