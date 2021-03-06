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

package de.minebench.zombe.core.player;

/**
 * @author dags_ <dags@dags.me>
 */

public class ZPermissions
{

    private boolean canFly = true;
    private boolean canSprint = true;
    private boolean canFullbright = true;
    private boolean canHighlightOres = true;
    private boolean canHighlightMobs = true;
    private boolean noFallDamage = true;
    private boolean canNoClip = false;
    private boolean canSeeThrough = true;

    public boolean flyEnabled()
    {
        return canFly;
    }

    public boolean sprintEnabled()
    {
        return canSprint;
    }

    public boolean fbEnabled()
    {
        return canFullbright;
    }

    public boolean oreHighlighterEnabled() {
        return canHighlightOres;
    }

    public boolean mobHighlighterEnabled() {
        return canHighlightMobs;
    }

    public boolean noFallDamageEnabled()
    {
        return noFallDamage;
    }

    public boolean noClipEnabled()
    {
        return canNoClip;
    }

    public boolean seeThroughEnabled()
    {
        return canSeeThrough;
    }

    public void resetPermissions()
    {
        canFly = true;
        canSprint = true;
        canFullbright = true;
        canHighlightOres = true;
        noFallDamage = true;
        canNoClip = false;
        canSeeThrough = true;
    }

    public void setMovementModsEnabled(boolean b)
    {
        canFly = b;
        canSprint = b;
    }

    public void setFullbrightEnabled(boolean b)
    {
        canFullbright = b;
    }

    public void setNoFallDamage(boolean b)
    {
        noFallDamage = b;
    }

    public void setNoClipEnabled(boolean b)
    {
        canNoClip = b;
    }

    public void setOreHighlight(boolean b) {
        this.canHighlightOres = b;
    }

    public void setMobHighlight(boolean b) {
        this.canHighlightMobs = b;
    }

    public void setSeeThroughEnabled(boolean b)
    {
        this.canSeeThrough = b;
    }
}
