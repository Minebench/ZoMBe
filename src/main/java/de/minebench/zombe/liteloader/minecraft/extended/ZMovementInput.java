package de.minebench.zombe.liteloader.minecraft.extended;

import de.minebench.zombe.core.Zombe;
import net.minecraft.util.MovementInputFromOptions;

/**
 * @author dags_ <dags@dags.me>
 */

public class ZMovementInput extends MovementInputFromOptions
{
    public boolean block = false;
    public boolean wasSneaking = false;

    public ZMovementInput()
    {
        super(Zombe.getMC().getGameSettings());
    }

    @Override
    public void updatePlayerMoveState()
    {
        super.updatePlayerMoveState();
        if (block)
        {
            wasSneaking = sneak;

            super.sneak = false;
            super.jump = false;

            block = false;
        }
    }
}
