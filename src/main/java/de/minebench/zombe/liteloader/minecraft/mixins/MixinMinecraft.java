package de.minebench.zombe.liteloader.minecraft.mixins;

import de.minebench.zombe.liteloader.minecraft.extended.ZRenderGlobal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow(aliases = {"init", "func_71384_a", "aq"})
    public abstract void init();

    @Redirect(method = "init()V", at = @At(value = "NEW", args = "class=net/minecraft/client/renderer/RenderGlobal"))
    public RenderGlobal constructRenderGlobal(Minecraft minecraft)
    {
        return new ZRenderGlobal(minecraft);
    }
}
