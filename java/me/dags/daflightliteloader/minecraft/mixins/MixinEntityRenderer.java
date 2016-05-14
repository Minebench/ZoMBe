package me.dags.daflightliteloader.minecraft.mixins;

import me.dags.daflight.DaFlight;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

    @Inject(method = "setupViewBobbing(F)V", at = @At("HEAD"))
    private void onSetupViewBobbing(float f, CallbackInfo ci)
    {
        if (DaFlight.get().DFController.flyModOn)
        {
            ci.cancel();
        }
    }
}
