package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.core.utils.FieldAccess;
import de.minebench.zombe.liteloader.minecraft.ObfTable;
import de.minebench.zombe.liteloader.minecraft.extended.EntityZFlyer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    /**
     * @author Phoenix616
     * @reason Create EntityZFlyer object
     */
    @Overwrite(aliases = {"createPlayer", "func_192830_a", "a"})
    public EntityPlayerSP createPlayer(World worldIn, StatisticsManager statisticsManager, RecipeBook recipeBook)
    {
        String[] fieldObf = ObfTable.PlayerControllerMP_connection.names;
        FieldAccess<NetHandlerPlayClient> netHandlerAccessor = new FieldAccess<NetHandlerPlayClient>(PlayerControllerMP.class, fieldObf);
        NetHandlerPlayClient netHandlerPlayClient = netHandlerAccessor.get(Minecraft.getMinecraft().playerController);
        return new EntityZFlyer(Minecraft.getMinecraft(), worldIn, netHandlerPlayClient, statisticsManager, recipeBook);
    }
}
