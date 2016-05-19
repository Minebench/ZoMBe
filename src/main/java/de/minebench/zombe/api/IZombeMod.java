package de.minebench.zombe.api;

import de.minebench.zombe.core.messaging.ChannelMessaging;
import de.minebench.zombe.api.minecraft.MinecraftGame;
import de.minebench.zombe.api.ui.UIHelper;

import java.io.File;
import java.util.List;

/**
 * @author dags_ <dags@dags.me>
 */

public interface IZombeMod
{
    /**
     * @return Name of the mod
     */
    public String getName();

    /**
     * @return Version of the mod
     */
    public String getVersion();

    /**
     * Set the version of the mod
     * @param version Version of the mod
     */
    String setVersion(String version);

    /**
     * Initialize ZoMBe and configs
     * @param mcGame Instance of the MinecraftGame interface implementation
     * @param channelMessaging The inbound and outbound custom packet handler
     * @param uiHelper The UI helper
     * @param configFolder The File config folder
     */
    public void onInit(MinecraftGame mcGame, ChannelMessaging channelMessaging, UIHelper uiHelper, File configFolder);

    /**
     * Called every tick, update the mod here
     * @param clock Is on the server tick (every 50ms)
     * @param inGame User is in-game
     */
    public void onTick(boolean clock, boolean inGame);

    /**
     * Draw HUD elements to the screen
     */
    public void onRender();

    /**
     * Reset any parameters and send a join query to the server if multiplayer
     */
    public void onJoinGame();

    /**
     * Handle in-bound packets
     * @param channel Channel name ("ZoMBe")
     * @param data Byte array of inbound data
     */
    public void onPluginMessage(String channel, byte[] data);

    /**
     * Get a list of plugin channels to register for the mod
     * @return ArrayList of channel names
     */
    public List<String> getPluginChannels();
}
