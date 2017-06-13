/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
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

package de.minebench.zombe.liteloader.minecraft;

import com.mumfrey.liteloader.transformers.event.EventInfo;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.liteloader.minecraft.extended.EntityDaFlyer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dags_ <dags@dags.me>
 */

@SuppressWarnings("unused")
public class EventListener
{
    public static void onSetupViewBobbing(EventInfo<EntityRenderer> e, float f)
    {
        if (Zombe.get().ZController.flyModOn)
        {
            e.cancel();
        }
    }

    public static void onEntityUpdate(EventInfo<EntityPlayer> e)
    {
        EntityPlayer ep = e.getSource();
        if (ep instanceof EntityPlayerMP && Zombe.get().ZController.noClipOn && Zombe.get().ZController.flyModOn)
        {
            ep.noClip = true;
        }
    }

    public static void onUpdateWalkingPlayer(EventInfo<EntityPlayerSP> e)
    {
        if (e.getSource() instanceof EntityDaFlyer && Zombe.get().ZController.softFallOn())
        {
            e.cancel();
            ((EntityDaFlyer) e.getSource()).softFall();
        }
    }
}
