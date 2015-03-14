package me.dags.daflightliteloader.minecraft;

import me.dags.daflightapi.minecraft.MinecraftGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.*;

/**
 * @author dags_ <dags@dags.me>
 */

public class MCGame implements MinecraftGame
{
    private static ScaledResolution scaledResolution;
    private static int lastGuiScale = 0;
    private static int lastWidth = 0;
    private static int lastHeight = 0;

    @Override
    public Minecraft getMinecraft()
    {
        return Minecraft.getMinecraft();
    }

    @Override
    public GameSettings getGameSettings()
    {
        return getMinecraft().gameSettings;
    }

    @Override
    public EntityPlayerSP getPlayer()
    {
        return getMinecraft().thePlayer;
    }

    @Override
    public ServerData getServerData()
    {
        return getMinecraft().getCurrentServerData();
    }

    @Override
    public boolean onSolidBlock()
    {
        BlockPos pos = new BlockPos(getPlayer().posX, getPlayer().lastTickPosY, getPlayer().posZ);
        return getMinecraft().theWorld.getBlockState(pos.down()).getBlock().getMaterial().isSolid();
    }

    @Override
    public IChatComponent getMessage(String s)
    {
        IChatComponent message = new ChatComponentText("[DaFlight] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE));
        message.appendSibling(new ChatComponentText(s).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
        return message;
    }

    @Override
    public void tellPlayer(String msg)
    {
        getPlayer().addChatMessage(getMessage(msg));
    }

    @Override
    public ScaledResolution getScaledResolution()
    {
        if (screenSizeChanged())
        {
            lastWidth = getMinecraft().displayWidth;
            lastHeight = getMinecraft().displayHeight;
            lastGuiScale = getGameSettings().guiScale;
            scaledResolution = new ScaledResolution(getMinecraft(), getMinecraft().displayWidth, getMinecraft().displayHeight);
        }
        return scaledResolution;
    }

    @Override
    public boolean screenSizeChanged()
    {
        return scaledResolution == null || getMinecraft().displayWidth != lastWidth || getMinecraft().displayHeight != lastHeight || getGameSettings().guiScale != lastGuiScale;
    }
}