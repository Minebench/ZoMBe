package me.dags.daflightliteloader.minecraft.mixins;

import me.dags.daflight.DaFlight;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

    @Inject(method = "setupTerrain(Lnet/minecraft/entity/Entity;DLnet/minecraft/client.renderer.culling.ICamera;IB)V", at = @At("HEAD"))
    private void onSetupTerrain(Entity viewEntity, double ticks, ICamera camera, int frameCount, boolean spectator)
    {
        if (DaFlight.get().DFController.noClipOn)
        {
            spectator = true;
        }
    }
}
