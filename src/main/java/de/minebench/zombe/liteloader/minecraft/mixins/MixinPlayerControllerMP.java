package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.core.utils.FieldAccess;
import de.minebench.zombe.liteloader.minecraft.ObfTable;
import de.minebench.zombe.liteloader.minecraft.extended.EntityDaFlyer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Overwrite(aliases = {"createClientPlayer", "func_178892_a", "a"})
    public EntityPlayerSP createClientPlayer(World worldIn, StatisticsManager statisticsManager)
    {
        String[] fieldObf = ObfTable.PlayerControllerMP_connection.names;
        FieldAccess<NetHandlerPlayClient> netHandlerAccessor = new FieldAccess<NetHandlerPlayClient>(PlayerControllerMP.class, fieldObf);
        NetHandlerPlayClient netHandlerPlayClient = netHandlerAccessor.get(Minecraft.getMinecraft().playerController);
        return new EntityDaFlyer(Minecraft.getMinecraft(), worldIn, netHandlerPlayClient, statisticsManager);
    }
}
