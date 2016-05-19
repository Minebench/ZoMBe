package de.minebench.zombe.core;

import de.minebench.zombe.core.gui.hud.HUD;
import de.minebench.zombe.core.messaging.ChannelMessaging;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.utils.GlobalConfig;
import de.minebench.zombe.api.ui.ZombeUI;
import de.minebench.zombe.core.utils.Config;
import de.minebench.zombe.api.ZombeAPI;
import de.minebench.zombe.api.minecraft.MinecraftGame;
import de.minebench.zombe.api.ui.UIHelper;

import java.io.File;

/**
 * @author dags_ <dags@dags.me>
 */

public class Zombe implements ZombeAPI
{
    public static String VERSION = "1.0";
    private static Zombe instance;

    public de.minebench.zombe.core.player.ZController ZController;
    private File configFolder;
    private MinecraftGame minecraftGame;
    private UIHelper uiHelper;
    private ChannelMessaging channelMessaging;
    private HUD hud;

    private Config config;
    private GlobalConfig globalConfig;

    private Zombe()
    {}

    public static Zombe init(MinecraftGame minecraftGame, ChannelMessaging messenging, UIHelper uiHelper, File folder)
    {
        get().minecraftGame = minecraftGame;
        get().channelMessaging = messenging;
        get().uiHelper = uiHelper;
        get().configFolder = folder;
        get().ZController = new ZController();
        get().hud = new HUD();
        return get();
    }

    public static Zombe get()
    {
        if (instance == null)
        {
            instance = new Zombe();
        }
        return instance;
    }

    public static MinecraftGame getMC()
    {
        return get().minecraftGame;
    }

    public static ChannelMessaging getChannelMessaging()
    {
        return get().channelMessaging;
    }

    public static HUD getHud()
    {
        return get().hud;
    }

    public static UIHelper getUIHelper()
    {
        return get().uiHelper;
    }

    public static File getConfigFolder()
    {
        File folder = get().configFolder;
        if (!folder.exists())
        {
            if (folder.mkdirs())
            {
                System.out.println("Creating new config folder...");
            }
        }
        return folder;
    }

    @Override
    public ZombeUI getUI()
    {
        return hud;
    }

    public static Config getConfig()
    {
        return get().config();
    }

    public static Config getServerConfig()
    {
        return get().serverConfig();
    }

    public static Config reloadConfig()
    {
        return get().reload();
    }

    public static GlobalConfig getGlobalConfig()
    {
        return get().globalConfig();
    }

    private Config config()
    {
        if (config == null)
        {
            config = Config.getDefaultConfig();
        }
        return config;
    }

    private Config serverConfig()
    {
        return config = Config.getServerConfig();
    }

    private Config reload()
    {
        config = null;
        return config();
    }

    private GlobalConfig globalConfig()
    {
        if (globalConfig == null)
            globalConfig = GlobalConfig.loadGlobalConfig();
        return globalConfig;
    }
}
