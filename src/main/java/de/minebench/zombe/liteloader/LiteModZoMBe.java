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

package de.minebench.zombe.liteloader;

import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.messaging.MessageHandler;
import de.minebench.zombe.api.ui.ZombeUI;
import de.minebench.zombe.liteloader.gui.UIHelper8;
import de.minebench.zombe.core.ZombeMod;
import de.minebench.zombe.core.messaging.ChannelMessaging;
import de.minebench.zombe.api.DaFlightAPI;
import de.minebench.zombe.api.IDaFlightMod;
import de.minebench.zombe.liteloader.gui.LiteloaderMenu;
import de.minebench.zombe.liteloader.messaging.MessageDispatcher;
import de.minebench.zombe.liteloader.minecraft.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketJoinGame;

import java.io.File;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModZombe implements DaFlightAPI, Tickable, HUDRenderListener, Configurable, JoinGameListener, PluginChannelListener
{
    private final IDaFlightMod ZOMBE_MOD = new ZombeMod();

    @Override
    public String getName()
    {
        return ZOMBE_MOD.getName();
    }

    @Override
    public String getVersion()
    {
        return ZOMBE_MOD.getVersion();
    }

    @Override
    public void init(File configPath)
    {
        ZOMBE_MOD.onInit(new MCGame(), new ChannelMessaging(new MessageHandler(), new MessageDispatcher()), new UIHelper8(), configPath);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {

    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return LiteloaderMenu.class;
    }

    @Override
    public void onTick(Minecraft m, float t, boolean inGame, boolean clock)
    {
        ZOMBE_MOD.onTick(clock, inGame);
    }

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight)
    {
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {
        ZOMBE_MOD.onRender();
    }

    @Override
    public List<String> getChannels()
    {
        log("Registering ZoMBe channel listener");
        return ZOMBE_MOD.getPluginChannels();
    }

    @Override
    public void onCustomPayload(String channel, PacketBuffer data)
    {
        ZOMBE_MOD.onPluginMessage(channel, data.array());
    }

    @Override
    public void onJoinGame(INetHandler netHandler, SPacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer)
    {
        ZOMBE_MOD.onJoinGame();
    }

    public static void log(String msg)
    {
        LiteLoaderLogger.info("[ZoMBe] " + msg);
    }

    @Override
    public ZombeUI getUI()
    {
        return Zombe.getHud();
    }
}
