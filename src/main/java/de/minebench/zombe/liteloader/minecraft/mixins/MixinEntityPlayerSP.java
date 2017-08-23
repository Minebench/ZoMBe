package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.liteloader.minecraft.extended.EntityZFlyer;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

    @Shadow(aliases = {"onUpdateWalkingPlayer", "N", "func_175161_p"})
    private void onUpdateWalkingPlayer() {}

    @Inject(method = "onUpdateWalkingPlayer()V", at = @At("HEAD"), cancellable = true)
    public void onOnUpdateWalkingPlayer(CallbackInfo ci)
    {
        if (Zombe.get().ZController.softFallOn())
        {
            ci.cancel();
            ((EntityZFlyer) Zombe.getMC().getPlayer()).softFall();
        }
    }
}
