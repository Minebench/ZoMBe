package de.minebench.zombe.liteloader.minecraft;

import com.mumfrey.liteloader.gl.GL;
import de.minebench.zombe.api.render.ARGB;
import de.minebench.zombe.api.render.IGLHelper;
import de.minebench.zombe.api.render.LocationInfo;
import de.minebench.zombe.core.Zombe;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
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

    public void drawOreHighlights(Map<LocationInfo, ARGB> oreHighlights, float t) {
        if(oreHighlights == null || oreHighlights.size() == 0)
            return;

        beginRender(t);

        for(Map.Entry<LocationInfo, ARGB> entry : oreHighlights.entrySet()) {

            Tessellator tess = Tessellator.getInstance();
            VertexBuffer vb = tess.getBuffer();

            EntityPlayerSP player = Zombe.getMC().getPlayer();
            double px = player.posX + (player.posX - player.prevPosX) * t;
            double py = player.posY + (player.posY - player.prevPosY) * t;
            double pz = player.posZ + (player.posZ - player.prevPosZ) * t;

            LocationInfo location = entry.getKey();
            ARGB color = entry.getValue();

            vb.begin(GL.GL_LINES, GL.VF_POSITION);
            GL.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha());
            GL.glLineWidth(2f);

            //TESTING
            /*
            double x = location.getX() - px;
            double y = location.getY() - py;
            double z = location.getZ() - pz;

            vb.pos(x, y, z).endVertex();
            vb.pos(x + 1, y, z).endVertex();
            vb.pos(x, y, z).endVertex();
            vb.pos(x, y + 1, z).endVertex();
            vb.pos(x, y + 1, z).endVertex();
            vb.pos(x + 1, y + 1, z).endVertex();
            vb.pos(x + 1, y + 1, z).endVertex();
            vb.pos(x + 1, y, z).endVertex();
            */

            double x = location.getX() + 0.5d - px;
            double y = location.getY() + 0.5d - py;
            double z = location.getZ() + 0.5d - pz;

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

    public void beginRender(float t) {
        RenderHelper.disableStandardItemLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        GL.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        GL.glEnableBlend();
        GL.glDisableTexture2D();
        GL.glDisableLighting();
        GL.glDepthMask(false);
        GL.glDisableDepthTest();
        GL.glPushMatrix();

        fogEnabled = GL11.glIsEnabled(GL11.GL_FOG);
        GL.glDisableFog();

        /*
        EntityPlayerSP player = Zombe.getMC().getPlayer();
        double x = player.posX + (player.posX - player.prevPosX) * t;
        double y = player.posY + (player.posY - player.prevPosY) * t;
        double z = player.posZ + (player.posZ - player.prevPosZ) * t;
        GL.glTranslated(-x, -y, -z);
        */
    }

    public void endRender() {
        GL.glPopMatrix();

        if(fogEnabled) {
            GL.glEnableFog();
        }

        GL.glEnableDepthTest();
        GL.glDepthMask(true);
        GL.glEnableLighting();
        GL.glEnableTexture2D();
        GL.glDisableBlend();

        RenderHelper.enableStandardItemLighting();
    }
}
