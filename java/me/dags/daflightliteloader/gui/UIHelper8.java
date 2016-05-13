package me.dags.daflightliteloader.gui;

import me.dags.daflightapi.ui.UIHelper;
import me.dags.daflightapi.ui.element.IEntryBox;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static com.mumfrey.liteloader.gl.GL.*;

public class UIHelper8 implements UIHelper
{
    @Override
    public IEntryBox getEntryBox(int x, int y, int width, int height, String label, String defaultValue, boolean colour)
    {
        return new EntryBox8(x, y, width, height, label, defaultValue, colour);
    }

    public void glDrawTexturedRect(int x, int y, int width, int height, int u, int v, int u2, int v2)
    {
        glDisableLighting();
        glEnableBlend();
        glAlphaFunc(GL_GREATER, 0F);
        glEnableTexture2D();
        glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        float texMapScale = 0.001953125F;

        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer worldRenderer = tessellator.getBuffer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(x, y + height, 0).tex(u * texMapScale, v2 * texMapScale).endVertex();
        worldRenderer.pos(x + width, y + height, 0).tex(u2 * texMapScale, v2 * texMapScale).endVertex();
        worldRenderer.pos(x + width, y, 0).tex(u2 * texMapScale, v * texMapScale).endVertex();
        worldRenderer.pos(x, y, 0).tex(u * texMapScale, v * texMapScale).endVertex();
        tessellator.draw();
        glDisableBlend();
    }
}
