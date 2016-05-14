package me.dags.daflightliteloader.minecraft.mixins;

import me.dags.daflight.DaFlight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {

    @Shadow public boolean noClip;

    @Inject(method = "onUpdate()V", at = @At("RETURN"))
    private void onOnUpdate(EntityPlayer self, CallbackInfo ci)
    {
        EntityPlayer ep = self;
        if (ep instanceof EntityPlayerMP && DaFlight.get().DFController.noClipOn && DaFlight.get().DFController.flyModOn)
        {
            this.noClip = true;
        }
    }
}
