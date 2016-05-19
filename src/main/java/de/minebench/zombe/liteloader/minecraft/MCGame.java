package de.minebench.zombe.liteloader.minecraft;

import de.minebench.zombe.api.minecraft.MinecraftGame;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.utils.ARGB;
import de.minebench.zombe.core.utils.LocationInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * @author dags_ <dags@dags.me> and Phoenix616 (https://github.com/Phoenix616)
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
    public boolean isSinglePlayer()
    {
        return getMinecraft().isSingleplayer();
    }

    @Override
    public boolean onSolidBlock()
    {
        BlockPos pos = new BlockPos(getPlayer().posX, getPlayer().lastTickPosY, getPlayer().posZ);
        IBlockState blockState = getMinecraft().theWorld.getBlockState(pos.down());
        return blockState.getBlock().getMaterial(blockState).isSolid();
    }

    @Override
    public ITextComponent getMessage(String s)
    {
        ITextComponent message = new TextComponentString("[ZoMBe] ").setStyle(new Style().setColor(TextFormatting.DARK_PURPLE));
        message.appendSibling(new TextComponentString(s).setStyle(new Style().setColor(TextFormatting.GRAY)));
        return message;
    }

    @Override
    public void setInvulnerable(boolean invulnerable)
    {
        EntityPlayer ep = getPlayer().getServer().getEntityWorld().getPlayerEntityByUUID(getPlayer().getUniqueID());
        ep.capabilities.disableDamage = invulnerable || ep.capabilities.isCreativeMode || ep.isSpectator();
        ep.sendPlayerAbilities();
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
            scaledResolution = new ScaledResolution(getMinecraft());
        }
        return scaledResolution;
    }

    @Override
    public boolean screenSizeChanged()
    {
        return scaledResolution == null || getMinecraft().displayWidth != lastWidth || getMinecraft().displayHeight != lastHeight || getGameSettings().guiScale != lastGuiScale;
    }

    @Override
    public void recheckOreHighlights() {
        World world = getPlayer().getEntityWorld();
        int px = getPlayer().getPosition().getX();
        int py = getPlayer().getPosition().getY();
        int pz = getPlayer().getPosition().getZ();
        int range = (int) Zombe.getConfig().oreHighlighterRange;
        for (int x = px - range; x < px + range; x++) {
            for (int z = pz - range; x < pz + range; z++) {
                for(int y = py - range; x < py + range; y++) {
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    ARGB color = Zombe.getConfig().oreColors.get(block.getMaterial(block.getDefaultState()).toString());
                    if(color != null) {
                        Zombe.get().ZController.addOreHighlight(new LocationInfo(x + 0.5f, y + 0.5f, z + 0.5f), color);
                    }
                }
            }
        }
    }
}
