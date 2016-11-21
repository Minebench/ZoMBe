package de.minebench.zombe.liteloader.minecraft;

import static com.mumfrey.liteloader.gl.GL.*;

import de.minebench.zombe.api.minecraft.EntityInfo;
import de.minebench.zombe.api.render.ARGB;
import de.minebench.zombe.api.render.IGLHelper;
import de.minebench.zombe.api.minecraft.LocationInfo;
import de.minebench.zombe.core.Zombe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import org.lwjgl.opengl.GL11;

import java.util.Map;

/**
 * Copyright 2016 Max Lee (https://github.com/Phoenix616/)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
public class GLHelper implements IGLHelper {

    private boolean fogEnabled = GL11.glIsEnabled(GL11.GL_FOG);

    @Override
    public void drawOreHighlights(Map<LocationInfo, ARGB> oreHighlights, float t) {
        if(oreHighlights == null || oreHighlights.size() == 0)
            return;

        beginRender(t);

        for(Map.Entry<LocationInfo, ARGB> entry : oreHighlights.entrySet()) {

            Tessellator tess = Tessellator.getInstance();
            VertexBuffer vb = tess.getBuffer();

            LocationInfo location = entry.getKey();
            ARGB color = entry.getValue();

            vb.begin(GL_LINES, VF_POSITION);
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha());
            glLineWidth(1f);

            double x = location.getX() + 0.5d;
            double y = location.getY() + 0.5d;
            double z = location.getZ() + 0.5d;

            vb.pos(x + 0.25d, y + 0.25d, z + 0.25d).endVertex();
            vb.pos(x - 0.25d, y - 0.25d, z - 0.25d).endVertex();
            vb.pos(x + 0.25d, y + 0.25d, z - 0.25d).endVertex();
            vb.pos(x - 0.25d, y - 0.25d, z + 0.25d).endVertex();
            vb.pos(x + 0.25d, y - 0.25d, z + 0.25d).endVertex();
            vb.pos(x - 0.25d, y + 0.25d, z - 0.25d).endVertex();
            vb.pos(x + 0.25d, y - 0.25d, z - 0.25d).endVertex();
            vb.pos(x - 0.25d, y + 0.25d, z + 0.25d).endVertex();
            tess.draw();
        }
        endRender();
    }

    @Override
    public void drawMobHighlight(EntityInfo entity, float partialTicks) {
        beginRender(partialTicks);
        glDisableDepthTest();
        glDepthMask(false);

        ARGB color = Zombe.getConfig().mobColors.get(entity.getName());
        if(color == null)
            color = Zombe.getConfig().mobColors.get("unknown");

        if(color == null)
            color = new ARGB();

        Tessellator tess = Tessellator.getInstance();
        VertexBuffer vb = tess.getBuffer();

        vb.begin(GL_LINES, VF_POSITION);
        glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha());
        glLineWidth(1f);

        double x = entity.getLocation().getX();
        double y = entity.getLocation().getY();
        double z = entity.getLocation().getZ();

        vb.pos(x, y, z).endVertex();
        vb.pos(x, y + entity.getEyeHeight(), z).endVertex();
        tess.draw();

        glEnableDepthTest();
        glDepthMask(true);
        endRender();
    }

    @Override
    public void beginRender(float t) {

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnableBlend();
        glDisableTexture2D();

        fogEnabled = GL11.glIsEnabled(GL_FOG);
        glDisableFog();

        glPushMatrix();

        EntityPlayerSP player = Minecraft.getMinecraft().player;
        double x = player.prevPosX + (player.posX - player.prevPosX) * t;
        double y = player.prevPosY + (player.posY - player.prevPosY) * t;
        double z = player.prevPosZ + (player.posZ - player.prevPosZ) * t;
        glTranslated(-x, -y, -z);

    }

    @Override
    public void endRender() {
        glPopMatrix();

        if(fogEnabled) {
            glEnableFog();
        }

        glEnableTexture2D();
        glDisableBlend();
    }
}
