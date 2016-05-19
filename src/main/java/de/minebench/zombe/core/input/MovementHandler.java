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

package de.minebench.zombe.core.input;

import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.player.Direction;
import de.minebench.zombe.core.input.bind.AbstractBind;
import de.minebench.zombe.core.input.bind.BindType;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.player.Vector;

/**
 * @author dags_ <dags@dags.me>
 */

public class MovementHandler
{

    private static final double sr = 1 / Math.sqrt(2);

    public static void handleMovementInput(ZController ZController)
    {
        double yaw = Zombe.getMC().getPlayer().rotationYaw;
        double pitch = Zombe.getMC().getPlayer().rotationPitch;
        double lrMod = Zombe.getConfig().lrModifier;

        Vector movementVector = new Vector();
        Direction direction = ZController.direction.update(yaw);

        boolean b1 = false;
        boolean b2 = false;

        movementVector.setSpeed(ZController.getSpeed());

        for (AbstractBind kb : ZController.KEY_BINDS.movementBinds)
        {
            if (kb.bindHeld())
            {
                movementVector.setHasInput(true);

                double x;
                double y;
                double z;

                if (kb.getType().equals(BindType.STRAFE))
                {
                    b1 = true;
                    x = direction.getZ() * kb.getModX() * movementVector.getSpeed() * lrMod;
                    y = 1.15 * kb.getModY() * movementVector.getSpeed();
                    z = direction.getX() * kb.getModZ() * movementVector.getSpeed() * lrMod;
                    movementVector.setHasLateralInput(true);
                }
                else
                {
                    b2 = true;
                    x = direction.getX() * kb.getModX() * movementVector.getSpeed();
                    y = 1.15 * kb.getModY() * movementVector.getSpeed();
                    z = direction.getZ() * kb.getModZ() * movementVector.getSpeed();

                    if (kb.getType().equals(BindType.MOVE))
                    {
                        movementVector.setHasLateralInput(true);
                    }
                    if (ZController.cineFlightOn || ZController.is3DFlightOn())
                    {
                        y += (0 - kb.getModX()) * movementVector.getSpeed() * (pitch / 90);
                    }
                }
                movementVector.add(x, y, z);
            }
        }
        if (b1 && b2)
        {
            movementVector.multiply(sr);
        }
        ZController.direction = direction;
        ZController.movementVector = movementVector;
    }
}
