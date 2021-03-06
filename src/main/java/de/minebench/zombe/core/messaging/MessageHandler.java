package de.minebench.zombe.core.messaging;

import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.player.ZPermissions;
import de.minebench.zombe.api.messaging.ZData;
import de.minebench.zombe.api.messaging.PluginMessageHandler;

/**
 * @author dags_ <dags@dags.me>
 */

public class MessageHandler implements PluginMessageHandler
{
    private ZPermissions getPerms()
    {
        return ZController.Z_PERMISSIONS;
    }

    @Override
    public void fullBright(boolean enable)
    {
        String message = enable ? "Fullbright allowed!" : "Fullbright not allowed!";
        if (getPerms().fbEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setFullbrightEnabled(enable);
        if (!enable && Zombe.get().ZController.fullBrightOn)
            Zombe.get().ZController.toggleFullbright();
    }

    @Override
    public void oreHighlighter(boolean enable)
    {
        String message = enable ? "Ore Highlighter allowed!" : "Ore Highlighter not allowed!";
        if (getPerms().oreHighlighterEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setOreHighlight(enable);
        if (!enable && Zombe.get().ZController.oreHighlighterOn)
            Zombe.get().ZController.toggleOreHighlighter();
    }

    @Override
    public void mobHighlighter(boolean enable) {
        String message = enable ? "Mob Highlighter allowed!" : "Mob Highlighter not allowed!";
        if(getPerms().mobHighlighterEnabled() != enable) {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setMobHighlight(enable);
        if(!enable && Zombe.get().ZController.mobHighlighterOn)
            Zombe.get().ZController.toggleMobHighlighter();
    }

    @Override
    public void flyMod(boolean enable)
    {
        String message = enable ? "Fly/Sprint mod allowed!" : "Fly/Sprint mod not allowed!";
        if (getPerms().flyEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setMovementModsEnabled(enable);
        if (!enable)
            Zombe.get().ZController.disableMovementMods();
    }

    @Override
    public void softFall(boolean enable)
    {
        String message = enable ? "Survival SoftFall allowed!" : "Survival SoftFall not allowed!";
        if (getPerms().noFallDamageEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setNoFallDamage(enable);
    }

    @Override
    public void noClip(boolean enable)
    {
        String message = enable ? "NoClip allowed!" : "NoClip not allowed!";
        if (getPerms().noClipEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setNoClipEnabled(enable);
        if (!enable && Zombe.get().ZController.noClipOn)
            Zombe.get().ZController.toggleNoClip();
    }

    @Override
    public void seeThrough(boolean enable)
    {
        String message = enable ? "SeeThrough allowed!" : "SeeThrough not allowed!";
        if (getPerms().seeThroughEnabled() != enable)
        {
            Zombe.getMC().tellPlayer(message);
        }
        ZController.Z_PERMISSIONS.setSeeThroughEnabled(enable);
        if (!enable && Zombe.get().ZController.seeThroughOn)
            Zombe.get().ZController.toggleSeeThrough();
    }

    @Override
    public void refresh(byte value)
    {
        if (Zombe.get().ZController.flyModOn || Zombe.get().ZController.sprintModOn)
        {
            Zombe.getChannelMessaging().dispatchMessage(ZData.getBooleanData(ZData.FLY_MOD, true));
        }
        if (Zombe.get().ZController.noClipOn)
        {
            Zombe.getChannelMessaging().dispatchMessage(ZData.getBooleanData(ZData.NOCLIP, true));
        }
    }

    @Override
    public void setSpeed(int value)
    {
        Zombe.getMC().tellPlayer("Max speed set by server! " + value);
        Zombe.get().ZController.flySpeed.setMaxSpeed(value);
        Zombe.get().ZController.sprintSpeed.setMaxSpeed(value);
        Zombe.getHud().updateMsg();
    }
}
