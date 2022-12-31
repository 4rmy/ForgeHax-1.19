package net.army.forgehax.module.modules.player;

import net.army.forgehax.module.Category;
import net.army.forgehax.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

import static org.lwjgl.glfw.GLFW.*;

public class NoFall extends Module {

    private final Minecraft mc = Minecraft.getInstance();

    public NoFall() {
        super("NoFall", GLFW_KEY_N, Category.PLAYER);
    }

    @Override
    public void Update() {
        if (mc.player != null) {
            if (mc.player.fallDistance > 2) {
                mc.player.connection.send(new ServerboundMovePlayerPacket(
                        mc.player.getX(),
                        mc.player.getY(),
                        mc.player.getZ(),
                        mc.player.getYRot(),
                        mc.player.getXRot(),
                        true,
                        true,
                        true
                ) {
                    @Override
                    public void write(FriendlyByteBuf p_131343_) {

                    }

                    @Override
                    public boolean isSkippable() {
                        return super.isSkippable();
                    }
                });
            }
        }
    }
}
