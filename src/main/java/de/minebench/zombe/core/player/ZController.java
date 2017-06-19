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

import de.minebench.zombe.api.minecraft.EntityInfo;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.input.KeybindHandler;
import de.minebench.zombe.core.input.MovementHandler;
import de.minebench.zombe.api.messaging.ZData;
import de.minebench.zombe.core.input.Binds;
import de.minebench.zombe.core.player.mode.CineFlightMode;
import de.minebench.zombe.core.player.mode.FlightMode;
import de.minebench.zombe.core.player.mode.IMode;
import de.minebench.zombe.core.player.mode.SprintMode;
import de.minebench.zombe.api.render.ARGB;
import de.minebench.zombe.core.utils.Config;
import de.minebench.zombe.api.minecraft.LocationInfo;
import de.minebench.zombe.core.utils.SpeedDefaults;
import de.minebench.zombe.core.utils.Tools;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dags_ <dags@dags.me>
 */

public class ZController
{
    public static final Binds KEY_BINDS = new Binds();
    public static final ZPermissions Z_PERMISSIONS = new ZPermissions();

    public Speed sprintSpeed;
    public Speed flySpeed;

    public boolean flyModOn = false;
    public boolean sprintModOn = false;
    public boolean cineFlightOn = false;
    public boolean fullBrightOn = false;
    public boolean oreHighlighterOn = false;
    public boolean mobHighlighterOn = false;
    public boolean noClipOn = false;
    public boolean seeThroughOn = false;

    public Direction direction;
    public Vector movementVector;
    private IMode controller;
    private Map<LocationInfo, ARGB> oreHighlights = new ConcurrentHashMap<LocationInfo, ARGB>();

    private boolean customSpeeds = false;
    private boolean inMenus = true;
    private boolean wasFlying = false;
    private int softFallTicks = 0;
    private int refreshOreCounter = 0;

    public ZController()
    {
        Config config = Zombe.getConfig();
        SpeedDefaults speedDefaults = SpeedDefaults.loadDefaults();
        direction = new Direction();
        movementVector = new Vector();
        customSpeeds = speedDefaults.usingCustomSpeeds();
        flySpeed = new Speed(Speed.SpeedType.FLY, speedDefaults.getDefaultMaxBaseSpeed(), speedDefaults.getDefaultMaxMultiplier());
        flySpeed.setSpeedValues(config.flySpeed, config.flySpeedMult);
        sprintSpeed = new Speed(Speed.SpeedType.SPRINT, speedDefaults.getDefaultMaxBaseSpeed(), speedDefaults.getDefaultMaxMultiplier());
        sprintSpeed.setSpeedValues(config.sprintSpeed, config.sprintSpeedMult);
    }

    public void onGameJoin()
    {
        flySpeed.resetMaxSpeed();
        sprintSpeed.resetMaxSpeed();
        Z_PERMISSIONS.resetPermissions();
        Zombe.getChannelMessaging().dispatchMessage(ZData.getPingData());
        if (customSpeeds)
        {
            Zombe.getMC().tellPlayer("WARNING - Using extreme speeds can cause your game to lag, or even crash!");
            customSpeeds = false;
        }
        if (Zombe.getMC().getMinecraft().isSingleplayer())
            Z_PERMISSIONS.setNoClipEnabled(true);
    }

    public void tickUpdate()
    {
        if (Zombe.getMC().getMinecraft().inGameHasFocus)
        {
            if (wasFlying && Zombe.getMC().onSolidBlock())
            {
                wasFlying = false;
                softFallTicks = 5;
            }
            softFallTicks--;

            if(oreHighlighterOn) {
                if(refreshOreCounter++ >= Zombe.getConfig().oreHighlighterUpdateRate) {
                    refreshOreCounter = 0;
                    Zombe.getMC().buildOreHighlights();
                }
            }
        }
    }

    public void update()
    {

        if (Zombe.getMC().getMinecraft().inGameHasFocus)
        {
            if (inMenus)
            {
                inMenus = false;
                KEY_BINDS.updateMovementKeys();
            }
            KeybindHandler.handleInput(this);
            if (isModOn() && controller != null)
            {
                MovementHandler.handleMovementInput(this);
                controller.input(this);
            }
        }
        else
        {
            if (!inMenus)
            {
                inMenus = true;
            }
            if (isModOn() && controller != null)
            {
                controller.unFocused();
            }
        }
    }

    public void postRender(float partialTicks) {
        if(oreHighlighterOn)
            drawOreHighlights(partialTicks);
    }

    public void postRenderEntity(EntityInfo entity, float partialTicks) {
        if(mobHighlighterOn && entity.getLocation().distanceSquared(Zombe.getMC().getPlayerLocation()) < Tools.square(Zombe.getConfig().mobHighlighterRange)) {
            Zombe.getGLHelper().drawMobHighlight(entity, partialTicks);
        }
    }

    public void toggleNoClip()
    {
        noClipOn = Z_PERMISSIONS.noClipEnabled() && !noClipOn;
        Zombe.getChannelMessaging().dispatchMessage(ZData.getBooleanData(ZData.NOCLIP, noClipOn));
        Zombe.getHud().updateMsg();
        if (Zombe.getMC().isSinglePlayer())
        {
            Zombe.getMC().setInvulnerable(noClipOn);
        }
    }

    public void toggleFlight()
    {
        flyModOn = Z_PERMISSIONS.flyEnabled() && !flyModOn;
        controller = getActiveController();
        if (!flyModOn && !cineFlightOn)
        {
            Zombe.getMC().getPlayer().capabilities.isFlying = false;
            Zombe.getMC().getPlayer().sendPlayerAbilities();
        }
        if (!flyModOn && !Zombe.getConfig().speedIsToggle)
        {
            flySpeed.setBoost(false);
        }
        if (!flyModOn)
        {
            Zombe.getMC().getPlayer().capabilities.isFlying = false;
            Zombe.getMC().getPlayer().sendPlayerAbilities();
            if (cineFlightOn)
            {
                Zombe.getMC().getGameSettings().smoothCamera = false;
            }
        }
        if (flyModOn && cineFlightOn)
        {
            Zombe.getMC().getGameSettings().smoothCamera = true;
        }
        notifyServer();
    }

    public void toggleCineFlight()
    {
        if (flyModOn)
        {
            cineFlightOn = !cineFlightOn;
            Zombe.getMC().getMinecraft().gameSettings.smoothCamera = cineFlightOn;
        }
        controller = getActiveController();
        if (!flyModOn && !cineFlightOn)
        {
            Zombe.getMC().getPlayer().capabilities.isFlying = false;
            Zombe.getMC().getPlayer().sendPlayerAbilities();
        }
    }

    public void toggleSprint()
    {
        sprintModOn = Z_PERMISSIONS.sprintEnabled() && !sprintModOn;
        controller = getActiveController();
        if (!sprintModOn && !Zombe.getConfig().speedIsToggle)
        {
            sprintSpeed.setBoost(false);
        }
        notifyServer();
    }

    public void toggleSpeedModifier()
    {
        if (flyModOn)
        {
            flySpeed.toggleBoost();
        }
        else if (sprintModOn)
        {
            sprintSpeed.toggleBoost();
        }
    }

    private void notifyServer()
    {
        Zombe.getChannelMessaging().dispatchMessage(ZData.getBooleanData(ZData.FLY_MOD, flyModOn || sprintModOn));
    }

    public void toggleFullbright()
    {
        float brightness = 9999F;
        if (fullBrightOn || !Z_PERMISSIONS.fbEnabled())
        {
            fullBrightOn = false;
            brightness = Zombe.getGlobalConfig().getBrightness();
        }
        else
        {
            fullBrightOn = true;
            Zombe.getGlobalConfig().setBrightness(Zombe.getMC().getGameSettings().gammaSetting);
            Zombe.getGlobalConfig().saveConfig();
        }
        Zombe.getMC().getGameSettings().gammaSetting = brightness;
    }

    public void toggleOreHighlighter()
    {
        oreHighlighterOn = !oreHighlighterOn && Z_PERMISSIONS.oreHighlighterEnabled();
        if(oreHighlighterOn)
            Zombe.getMC().buildOreHighlights();
        else
            clearOreHighlights();
    }

    public void toggleMobHighlighter()
    {
        mobHighlighterOn = !mobHighlighterOn && Z_PERMISSIONS.mobHighlighterEnabled();
    }

    public void toggleSeeThrough()
    {
        seeThroughOn = !seeThroughOn && Z_PERMISSIONS.seeThroughEnabled();
    }

    public void disableAll()
    {
        if (Zombe.getMC().getMinecraft().inGameHasFocus)
        {
            if (flyModOn)
            {
                toggleFlight();
            }
            if (sprintModOn)
            {
                toggleSprint();
            }
            if (fullBrightOn)
            {
                toggleFullbright();
            }
            if (oreHighlighterOn) {
                toggleOreHighlighter();
            }
            if (seeThroughOn) {
                toggleSeeThrough();
            }
        }
    }

    public void disableMovementMods()
    {
        if (flyModOn)
        {
            toggleFlight();
        }
        if (sprintModOn)
        {
            toggleSprint();
        }
        flySpeed.setBoost(false);
        sprintSpeed.setBoost(false);
        Zombe.getHud().updateMsg();
    }

    public boolean softFallOn()
    {
        if (Zombe.getConfig().disabled || !Z_PERMISSIONS.noFallDamageEnabled())
        {
            return false;
        }
        if (flyModOn || sprintModOn)
        {
            return wasFlying = true;
        }
        return wasFlying || softFallTicks > 0 || (wasFlying = false);
    }

    private IMode getActiveController()
    {
        return flyModOn && cineFlightOn ? new CineFlightMode() : flyModOn ? new FlightMode() : sprintModOn ? new SprintMode() : null;
    }

    private boolean isModOn()
    {
        return flyModOn || cineFlightOn || sprintModOn;
    }

    public boolean is3DFlightOn()
    {
        return Zombe.getConfig().threeDFlight;
    }

    public double getSpeed()
    {
        if (flyModOn)
        {
            return flySpeed.getTotalSpeed();
        }
        else if (sprintModOn)
        {
            return sprintSpeed.getTotalSpeed();
        }
        return 1D;
    }

    public void clearOreHighlights() {
        oreHighlights.clear();
    }

    private void drawOreHighlights(float t) {
        Zombe.getGLHelper().drawOreHighlights(oreHighlights, t);
    }

    public void refreshOreHighlights(Map<LocationInfo, ARGB> newHighlights) {
        Iterator<LocationInfo> it = oreHighlights.keySet().iterator();
        while(it.hasNext()) {
            LocationInfo loc = it.next();
            if(!newHighlights.containsKey(loc)) {
                it.remove();
            }
        }
        oreHighlights.putAll(newHighlights);
    }
}
