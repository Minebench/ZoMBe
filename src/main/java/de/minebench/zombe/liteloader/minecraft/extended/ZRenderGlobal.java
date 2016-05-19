package de.minebench.zombe.liteloader.minecraft.extended;

import de.minebench.zombe.core.Zombe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;

/**
 * @author dags_ <dags@dags.me>
 */

public class ZRenderGlobal extends RenderGlobal
{
    public ZRenderGlobal(Minecraft mcIn)
    {
        super(mcIn);
    }

    @Override
    public void setupTerrain(Entity viewEntity, double ticks, ICamera camera, int frameCount, boolean spectator)
    {
        if (Zombe.get().ZController.noClipOn)
        {
            spectator = true;
        }
        super.setupTerrain(viewEntity, ticks, camera, frameCount, spectator);
    }
}
