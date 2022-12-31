package net.army.forgehax.module;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Comparator;

public class ModuleManager {
    private final ArrayList<Module> modules;
    private final ArrayList<Module> activeModules;
    private static final Minecraft mc = Minecraft.getInstance();

    private static class ModuleComparator implements Comparator<Module> {
        @Override
        public int compare(Module a, Module b) {
            return Integer.compare(mc.font.width(b.name), mc.font.width(a.name));
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

    @SubscribeEvent
    public void onRenderWorld(RenderLevelStageEvent event) {
        if (this.getModuleByName("X-Ray").toggled) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS) {
                ClientLevel world = mc.level;
                if (mc.level == null)
                    return;

                int viewDistance = Minecraft.getInstance().options.renderDistance().get();

                for (int x = -viewDistance; x <= viewDistance; x++) {
                    for (int y = 0; y < 256; y++) {
                        for (int z = -viewDistance; z <= viewDistance; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            Block block = world.getBlockState(pos).getBlock();

                            if (this.getModuleByName("X-Ray").getWhitelist().contains(block.getName().toString())) {
                                //this.renderOutline(event, pos);
                                this.renderOutline(event, new BlockPos(0, 0, 0));
                            }
                        }
                    }
                }
            }
        }
    }

    private static VertexBuffer vertexBuffer;
    public static boolean requestedRefresh = false;

    private void renderOutline(RenderLevelStageEvent event, BlockPos pos) {
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        float r = 1.0f, g = 0.0f, b = 0.0f, a = 1.0f;

        drawLine(event, x, y, z, x + 1, y, z, r, g, b, a);
        drawLine(event, x + 1, y, z, x + 1, y + 1, z, r, g, b, a);
        drawLine(event, x + 1, y + 1, z, x, y + 1, z, r, g, b, a);
        drawLine(event, x, y + 1, z, x, y, z, r, g, b, a);

        drawLine(event, x, y, z + 1, x + 1, y, z + 1, r, g, b, a);
        drawLine(event, x + 1, y, z + 1, x + 1, y + 1, z + 1, r, g, b, a);
        drawLine(event, x + 1, y + 1, z + 1, x, y + 1, z + 1, r, g, b, a);
        drawLine(event, x, y + 1, z + 1, x, y, z + 1, r, g, b, a);

        drawLine(event, x, y, z, x, y, z + 1, r, g, b, a);
        drawLine(event, x + 1, y, z, x + 1, y, z + 1, r, g, b, a);
        drawLine(event, x + 1, y + 1, z, x + 1, y + 1, z + 1, r, g, b, a);
        drawLine(event, x, y + 1, z, x, y + 1, z + 1, r, g, b, a);
    }

    private void drawLine(RenderLevelStageEvent event, double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        vertexBuffer = new VertexBuffer();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        buffer.begin(VertexFormat.Mode.LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(x1, y1, z1).color(r, g, b, a).endVertex();
        buffer.vertex(x2, y2, z2).color(r, g, b, a).endVertex();

        vertexBuffer.bind();
        vertexBuffer.upload(buffer.end());
        VertexBuffer.unbind();

        Vec3 view = Minecraft.getInstance().getEntityRenderDispatcher().camera.getPosition();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        PoseStack matrix = event.getPoseStack();
        matrix.pushPose();
        matrix.translate(-view.x, -view.y, -view.z);

        vertexBuffer.bind();
        vertexBuffer.drawWithShader(matrix.last().pose(), event.getProjectionMatrix().copy(), RenderSystem.getShader());
        VertexBuffer.unbind();
        matrix.popPose();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }
}
