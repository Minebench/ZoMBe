/*
 * The code of the loadVersionFromLitemodJson() method and the getLiteModJsonStream()
 * are written by totemo as part of watson (https://github.com/totemo/watson/) and
 * under the following copyright:
 *
 * Copyright (c) 2012 totemo
 *
 * DaFlight code:
 *
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

import com.google.gson.Gson;
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
import de.minebench.zombe.api.ZombeAPI;
import de.minebench.zombe.api.IZombeMod;
import de.minebench.zombe.liteloader.gui.LiteloaderMenu;
import de.minebench.zombe.liteloader.messaging.MessageDispatcher;
import de.minebench.zombe.liteloader.minecraft.MCGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketJoinGame;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dags_ <dags@dags.me>
 */

public class LiteModZombe implements ZombeAPI, Tickable, HUDRenderListener, Configurable, JoinGameListener, PluginChannelListener
{
    private final IZombeMod ZOMBE_MOD = new ZombeMod();
    private WorldClient lastWorld;

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
        loadVersionFromLitemodJson();
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
        if(clock && Zombe.getMC().getMinecraft().theWorld != lastWorld) {
            ZOMBE_MOD.onWorldChange();
            lastWorld = Zombe.getMC().getMinecraft().theWorld;
            log("Changed world!");
        }
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

    /**
     * NOTE: The two methods below is by totemo from watson (https://github.com/totemo/watson/)
     * which is licensed under this license: https://github.com/totemo/watson/blob/master/License.txt
     *
     * Copyright (c) 2012 totemo
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
     */

    /**
     * Read the mod version from the metadata.
     */
    private void loadVersionFromLitemodJson() {
        InputStream is = null;
        try
        {
            Gson gson = new Gson();
            is = getLiteModJsonStream();
            @SuppressWarnings("unchecked")
            Map<String, String> meta = gson.fromJson(new InputStreamReader(is), HashMap.class);
            String version = meta.get("version");
            if (version != null)
            {
                ZOMBE_MOD.setVersion(version);
            }
        }
        catch (Exception ignored)
        {
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (Exception ignored)
                {
                }
            }
        }
    }

    /**
     * Return an InputStream that reads "/litemod.json".
     *
     * When running under the IDE, that's easy because the file is copied to the
     * res/ directory and getResourceAsStream() can access it directly. When
     * running as an installed mod file, getResourceAsStream() may return a
     * reference to the litemod.json file for another mod, depending on the order
     * of the mods in the ClassLoader. In that circumstance, we use a specially
     * crafted URL that references litemod.json via the URL of the .litemod (JAR)
     * file.
     *
     * @return the InputStream, or null on failure.
     */
    private InputStream getLiteModJsonStream()
    {
        String classURL = getClass().getResource("/" + getClass().getName().replace('.', '/') + ".class").toString();
        if (classURL.contains("!"))
        {
            String jarURL = classURL.substring(0, classURL.indexOf('!'));
            try
            {
                URL resourceURL = new URL(jarURL + "!/litemod.json");
                return resourceURL.openStream();
            }
            catch (IOException ex)
            {
            }
            return null;
        }
        else
        {
            // No JAR. Running under the IDE.
            return getClass().getResourceAsStream("/litemod.json");
        }
    }

}
