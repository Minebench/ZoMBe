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

import de.minebench.zombe.core.player.ZController;

/**
 * @author dags_ <dags@dags.me>
 */

public class ToggleSpeed implements Action
{
    @Override
    public boolean pressed(ZController ZController)
    {
        ZController.toggleSpeedModifier();
        return true;
    }

    @Override
    public boolean held(ZController ZController)
    {
        if (ZController.flyModOn)
        {
            if (!ZController.flySpeed.isBoosting())
            {
                ZController.toggleSpeedModifier();
                return true;
            }
        }
        else if (ZController.sprintModOn && !ZController.sprintSpeed.isBoosting())
        {
            ZController.toggleSpeedModifier();
            return true;
        }
        return false;
    }

    @Override
    public boolean released(ZController ZController)
    {
        if (ZController.flyModOn && ZController.flySpeed.isBoosting() || ZController.sprintModOn && ZController.sprintSpeed.isBoosting())
        {
            ZController.toggleSpeedModifier();
            return true;
        }
        return false;
    }
}
