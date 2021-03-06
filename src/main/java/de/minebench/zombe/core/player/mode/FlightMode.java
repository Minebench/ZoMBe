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

package de.minebench.zombe.core.player.mode;

import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.player.Vector;
import de.minebench.zombe.api.minecraft.MinecraftGame;

public class FlightMode implements IMode
{
    @Override
    public void input(ZController ZController)
    {
        MinecraftGame mc = Zombe.getMC();
        Vector v = ZController.movementVector;
        if (v.hasInput())
        {

            mc.getPlayer().setVelocity(v.getX(), v.getY(), v.getZ());
        }
        else
        {
            double smoothing = Zombe.getConfig().flySmoothing;
            mc.getPlayer().setVelocity(mc.getPlayer().motionX * smoothing, 0, mc.getPlayer().motionZ * smoothing);
        }
    }

    @Override
    public void unFocused()
    {
        MinecraftGame mc = Zombe.getMC();
        double smoothing = Zombe.getConfig().flySmoothing;
        double xMotion = mc.getPlayer().motionX;
        double yMotion = mc.getPlayer().motionY;
        double zMotion = mc.getPlayer().motionZ;
        mc.getPlayer().setVelocity(xMotion * smoothing, yMotion * smoothing, zMotion * smoothing);
    }
}
