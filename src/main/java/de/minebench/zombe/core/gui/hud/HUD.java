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

package de.minebench.zombe.core.gui.hud;

import de.minebench.zombe.api.ui.ZombeUI;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.minecraft.Colour;
import de.minebench.zombe.core.player.ZController;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders text to screen when certain components of the mod are enabled
 *
 * @author dags_
 */
public class HUD implements ZombeUI
{
    private static final int FLIGHT = 0;
    private static final int SPRINT = 1;
    private static final int NO_CLIP = 2;
    private static final int FULLBRIGHT = 3;
    private static final int ORE_HIGHLIGHT = 4;
    private static final int MOB_HIGHLIGHT = 5;
    private static final int SEE_THROUGH = 6;
    private static final int TEMP = 7;

    private List<ZEntry> mods;
    private int counter = 50;

    private String flight = "f";
    private String cine = "c";
    private String run = "r";
    private String clip = "n";
    private String modifier = "*";
    private String fb = "fb";
    private String oreHighlight = "o";
    private String mobHighlight = "m";
    private String seeThrough = "s";

    public HUD()
    {
        mods = new ArrayList<ZEntry>();
        mods.add(FLIGHT, new ZEntry("", false));
        mods.add(SPRINT, new ZEntry("", false));
        mods.add(NO_CLIP, new ZEntry("", false));
        mods.add(FULLBRIGHT, new ZEntry("", false));
        mods.add(ORE_HIGHLIGHT, new ZEntry("", false));
        mods.add(MOB_HIGHLIGHT, new ZEntry("", false));
        mods.add(SEE_THROUGH, new ZEntry("", false));
        mods.add(TEMP, new ZEntry("", false));
    }

    public void updateMsg()
    {
        ZController dp = Zombe.get().ZController;
        boolean flyModifier = false;
        // Flight
        if (dp.flyModOn || ZController.KEY_BINDS.enableFly.bindHeld())
        {
            String s = "";
            flyModifier = ZController.KEY_BINDS.speedModifier.bindHeld();
            if (dp.flyModOn)
            {
                s = flight;
                if (dp.cineFlightOn)
                {
                    s = cine;
                }
            }
            if (ZController.Z_PERMISSIONS.flyEnabled() && (dp.flySpeed.isBoosting() || flyModifier))
            {
                s = s + modifier;
            }
            mods.get(FLIGHT).setTitle(s);
            mods.get(FLIGHT).setShow(true);
        }
        else
        {
            mods.get(FLIGHT).setShow(false);
        }
        // Sprint
        if (ZController.Z_PERMISSIONS.sprintEnabled() && (dp.sprintModOn || ZController.KEY_BINDS.enableSprint.bindHeld()))
        {
            String s = run;
            if (dp.sprintSpeed.isBoosting() || (ZController.KEY_BINDS.speedModifier.bindHeld() && !flyModifier))
            {
                s = s + modifier;
            }
            mods.get(SPRINT).setTitle(s);
            mods.get(SPRINT).setShow(true);
        }
        else
        {
            mods.get(SPRINT).setShow(false);
        }
        // noClip
        mods.get(NO_CLIP).setTitle(clip);
        mods.get(NO_CLIP).setShow(dp.noClipOn);
        // FullBright
        mods.get(FULLBRIGHT).setTitle(fb);
        mods.get(FULLBRIGHT).setShow(dp.fullBrightOn);
        // Xray
        mods.get(ORE_HIGHLIGHT).setTitle(oreHighlight);
        mods.get(ORE_HIGHLIGHT).setShow(dp.oreHighlighterOn);
        // Mob Highlighter
        mods.get(MOB_HIGHLIGHT).setTitle(mobHighlight);
        mods.get(MOB_HIGHLIGHT).setShow(dp.mobHighlighterOn);
        // See Through
        mods.get(SEE_THROUGH).setTitle(seeThrough);
        mods.get(SEE_THROUGH).setShow(dp.seeThroughOn);
    }

    public void refreshStatuses()
    {
        flight = Colour.getColouredString(Zombe.getConfig().flightStatus);
        cine = Colour.getColouredString(Zombe.getConfig().cineFlightStatus);
        run = Colour.getColouredString(Zombe.getConfig().runStatus);
        modifier = Colour.getColouredString(Zombe.getConfig().speedStatus);
        clip = Colour.getColouredString(Zombe.getConfig().noClipStatus);
        fb = Colour.getColouredString(Zombe.getConfig().fullBrightStatus);
        oreHighlight = Colour.getColouredString(Zombe.getConfig().oreHighlighterStatus);
        mobHighlight = Colour.getColouredString(Zombe.getConfig().mobHighlighterStatus);
        seeThrough = Colour.getColouredString(Zombe.getConfig().seeThroughStatus);
    }

    public void renderTemp(String s)
    {
        counter = 50;
        mods.get(TEMP).setTitle(s);
        mods.get(TEMP).setShow(true);
    }

    public void setTemp(boolean b)
    {
        mods.get(TEMP).setShow(b);
    }

    @Override
    public void draw()
    {
        if (Zombe.getConfig().disabled)
        {
            return;
        }
        counter--;
        if (Zombe.getConfig().showHud && Zombe.getMC().getMinecraft().inGameHasFocus && !Zombe.getMC().getGameSettings().showDebugInfo)
        {
            int slot = 5;
            for (ZEntry d : mods)
            {
                if (d.isShown())
                {
                    if (Zombe.getConfig().textShadow)
                    {
                        Zombe.getMC().getMinecraft().fontRenderer.drawStringWithShadow(d.getTitle(), 5, slot, 0xFFFFFF);
                    }
                    else
                    {
                        Zombe.getMC().getMinecraft().fontRenderer.drawString(d.getTitle(), 5, slot, 0xFFFFFF);
                    }
                    slot += 10;
                }
            }
        }
        if (counter <= 0)
        {
            setTemp(false);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public int addModStatus(String s)
    {
        int i = mods.size();
        mods.add(i, new ZEntry(s, false));
        return i;
    }

    @SuppressWarnings("unused")
    @Override
    public void removeModStatus(int id)
    {
        if (id < mods.size() && id > 3)
        {
            mods.remove(id);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void setStatusVisibility(int id, boolean b)
    {
        if (id < mods.size() && id > 3)
        {
            mods.get(id).setShow(b);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void setStatus(int id, String s)
    {
        if (id < mods.size() && id > 3)
        {
            mods.get(id).setTitle(s);
        }
    }

}
