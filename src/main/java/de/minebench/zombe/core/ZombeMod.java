package de.minebench.zombe.core;

import de.minebench.zombe.api.minecraft.EntityInfo;
import de.minebench.zombe.core.messaging.ChannelMessaging;
import de.minebench.zombe.api.IZombeMod;
import de.minebench.zombe.api.minecraft.MinecraftGame;
import de.minebench.zombe.api.ui.UIHelper;
import de.minebench.zombe.api.render.IGLHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class ZombeMod implements IZombeMod
{
    private boolean wasInGame;

    @Override
    public String getName()
    {
        return "ZoMBe";
    }

    @Override
    public String getVersion()
    {
        return Zombe.VERSION;
    }

    @Override
    public void onInit(MinecraftGame mcGame, ChannelMessaging channelMessaging, UIHelper uiHelper, IGLHelper glHelper, File configFolder)
    {
        Zombe.init(mcGame, channelMessaging, uiHelper, glHelper, configFolder);
        Zombe.getConfig().applySettings();
        Zombe.getGlobalConfig().applyDefaults();
    }

    @Override
    public void onTick(boolean clock, boolean inGame)
    {
        if (clock)
        {
            if (!inGame && wasInGame)
            {
                wasInGame = false;
                Zombe.reloadConfig();
                Zombe.getConfig().applySettings();
            }
            if (Zombe.getConfig().disabled)
                Zombe.get().ZController.disableAll();
        }
        if (inGame && !Zombe.getConfig().disabled)
        {
            Zombe.get().ZController.update();
            wasInGame = true;
            if (clock)
                Zombe.get().ZController.tickUpdate();
        }
    }

    @Override
    public void postRender(float partialTicks) {
        if(!Zombe.getConfig().disabled) {
            Zombe.get().ZController.postRender(partialTicks);
        }
    }

    @Override
    public void onRender()
    {
        Zombe.getHud().draw();
    }

    @Override
    public void postRenderEntity(EntityInfo entity, float partialTicks) {
        Zombe.get().ZController.postRenderEntity(entity, partialTicks);
    }

    @Override
    public void onWorldChange() {
        Zombe.get().ZController.clearOreHighlights();
    }

    @Override
    public void onJoinGame()
    {
        Zombe.get().ZController.onGameJoin();
        if (Zombe.getGlobalConfig().perServerConfig() && !Zombe.getMC().getMinecraft().isSingleplayer())
        {
            Zombe.getServerConfig();
            Zombe.getConfig().applySettings();
            Zombe.getMC().tellPlayer("Server config loaded for: " + Zombe.getMC().getServerData().serverIP);
        }
    }

    @Override
    public void onPluginMessage(String channel, byte[] data)
    {
        Zombe.getChannelMessaging().onPacketReceived(channel, data);
    }

    @Override
    public List<String> getPluginChannels()
    {
        List<String> channel = new ArrayList<String>();
        channel.add("DaFlight");
        channel.add("ZoMBe");
        return channel;
    }
}
