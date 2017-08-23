package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.core.Zombe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer extends Entity {

    public MixinEntityPlayer(World worldIn) {
        super(worldIn);
    }

    @Shadow(aliases = {"onUpdate", "func_70071_h_", "B_"})
    public void onUpdate() {}

    @Inject(method = "onUpdate()V", at = @At(value = "HEAD"))
    public void onOnUpdate(CallbackInfo ci)
    {
        if (Zombe.get().ZController.noClipOn && Zombe.get().ZController.flyModOn)
        {
            noClip = true;
        }
    }
}
