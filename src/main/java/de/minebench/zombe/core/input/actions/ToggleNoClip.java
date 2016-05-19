package de.minebench.zombe.core.input.actions;

import de.minebench.zombe.core.player.ZController;

/**
 * @author dags_ <dags@dags.me>
 */

public class ToggleNoClip implements Action
{
    @Override
    public boolean pressed(ZController ZController)
    {
        ZController.toggleNoClip();
        return true;
    }

    @Override
    public boolean held(ZController ZController)
    {
        if (!ZController.noClipOn)
        {
            ZController.toggleNoClip();
            return true;
        }
        return false;
    }

    @Override
    public boolean released(ZController ZController)
    {
        if (ZController.noClipOn)
        {
            ZController.toggleNoClip();
            return true;
        }
        return false;
    }
}
