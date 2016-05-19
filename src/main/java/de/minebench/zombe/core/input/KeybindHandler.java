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
import de.minebench.zombe.core.input.bind.AbstractBind;
import de.minebench.zombe.core.player.ZController;

/**
 * @author dags_ <dags@dags.me>
 */

public class KeybindHandler
{

    public static void handleInput(ZController ZController)
    {
        boolean result = false;
        for (AbstractBind kb : ZController.KEY_BINDS.binds)
        {
            if (kb.bindPressed())
                result = kb.getAction().pressed(ZController) || result;
            else if (kb.bindHeld())
                result = kb.getAction().held(ZController) || result;
            else if (kb.bindReleased())
                result = kb.getAction().released(ZController) || result;
        }
        if (result)
            Zombe.getHud().updateMsg();
    }
}