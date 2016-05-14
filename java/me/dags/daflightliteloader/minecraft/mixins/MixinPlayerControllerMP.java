package me.dags.daflightliteloader.minecraft.mixins;

import me.dags.daflightliteloader.minecraft.extended.EntityDaFlyer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {

    @Shadow @Final private NetHandlerPlayClient netClientHandler;

    //@Inject(method = "createClientPlayer(Lnet/minecraft/world/World;Lnet/minecraft/stats/StatFileWriter;)Lnet.minecraft.client.entity.EntityPlayerSP;", at = @At("RETURN"))
    @Overwrite
    private EntityPlayerSP createClientPlayer(World worldIn, StatFileWriter fileWriter, CallbackInfo ci)
    {
        return new EntityDaFlyer(Minecraft.getMinecraft(), worldIn, netClientHandler, fileWriter);
    }
}
