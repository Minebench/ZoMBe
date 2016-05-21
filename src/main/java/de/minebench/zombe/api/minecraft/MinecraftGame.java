package de.minebench.zombe.api.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.text.ITextComponent;

/**
 * @author dags_ <dags@dags.me>
 */

public interface MinecraftGame
{
    public Minecraft getMinecraft();

    public GameSettings getGameSettings();

    public EntityPlayerSP getPlayer();

    public LocationInfo getPlayerLocation();

    public ServerData getServerData();

    public boolean isSinglePlayer();

    public boolean onSolidBlock();

    public ITextComponent getMessage(String s);

    public void setInvulnerable(boolean invulnerable);

    public void tellPlayer(String msg);

    public ScaledResolution getScaledResolution();

    public boolean screenSizeChanged();

    public void buildOreHighlights();
}
