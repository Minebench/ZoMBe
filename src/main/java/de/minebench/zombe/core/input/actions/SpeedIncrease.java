/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 *  Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 *  granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING
 *  ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL,
 *  DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 *  WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE
 *  USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package de.minebench.zombe.core.input.actions;

import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.player.Speed;
import de.minebench.zombe.core.utils.Tools;

/**
 * @author dags_ <dags@dags.me>
 */

public class SpeedIncrease extends SpeedAdjust
{
    @Override
    public boolean held(ZController ZController)
    {
        if (tick())
        {
            if (ZController.flyModOn)
                return incFlySpeed(ZController);
            else if (ZController.sprintModOn)
                return incSprintSpeed(ZController);
        }
        return false;
    }

    private boolean incFlySpeed(ZController ZController)
    {
        Speed speed = ZController.flySpeed;
        if (speed.isBoosting())
        {
            Zombe.getConfig().flySpeedMult = speed.incMultiplier();
            Zombe.getHud().renderTemp("X" + Tools.round1Dp(Zombe.getConfig().flySpeedMult));
        }
        else
        {
            Zombe.getConfig().flySpeed = speed.incBaseSpeed();
            Zombe.getHud().renderTemp("x" + Tools.round1Dp(Zombe.getConfig().flySpeed * 10F));
        }
        return true;
    }

    private boolean incSprintSpeed(ZController ZController)
    {
        Speed speed = ZController.sprintSpeed;
        if (speed.isBoosting())
        {
            Zombe.getConfig().sprintSpeedMult = speed.incMultiplier();
            Zombe.getHud().renderTemp("X" + Tools.round1Dp(Zombe.getConfig().sprintSpeedMult));
        }
        else
        {
            Zombe.getConfig().sprintSpeed = speed.incBaseSpeed();
            Zombe.getHud().renderTemp("x" + Tools.round1Dp(Zombe.getConfig().sprintSpeed * 10F));
        }
        return true;
    }
}
