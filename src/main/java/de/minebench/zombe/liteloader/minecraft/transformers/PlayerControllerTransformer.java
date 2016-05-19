package de.minebench.zombe.liteloader.minecraft.transformers;

import com.mumfrey.liteloader.transformers.ClassOverlayTransformer;

/**
 * @author dags_ <dags@dags.me>
 */

public class PlayerControllerTransformer extends ClassOverlayTransformer
{
    public PlayerControllerTransformer()
    {
        super("de/minebench/zombe/liteloader/minecraft/extended/ControllerMPOverlay");
    }
}
