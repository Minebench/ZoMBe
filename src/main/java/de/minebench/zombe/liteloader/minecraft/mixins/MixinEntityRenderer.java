package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.core.Zombe;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.logging.Level;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow(aliases = {"applyBobbing", "func_78475_f", "e"})
    public abstract void applyBobbing(float f);

    @Inject(method = "applyBobbing(F)V", at = @At("HEAD"), cancellable = true)
    public void onApplyBobbing(CallbackInfo ci)
    {
        if (Zombe.get().ZController.flyModOn)
        {
            ci.cancel();
        }
    }
}
