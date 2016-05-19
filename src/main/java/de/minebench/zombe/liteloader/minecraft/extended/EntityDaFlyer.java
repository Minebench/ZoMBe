package de.minebench.zombe.liteloader.minecraft.extended;

import de.minebench.zombe.core.Zombe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.world.World;

/**
 * @author dags_ <dags@dags.me>
 */

public class EntityDaFlyer extends EntityPlayerSP
{
    private ZMovementInput movementInput;
    private int ticksSinceMovePacket = 0;
    private boolean wasSneaking = false;

    private double oldPosX;
    private double oldMinY;
    private double oldPosZ;
    private double oldRotationYaw;
    private double oldRotationPitch;

    public EntityDaFlyer(Minecraft mc, World world, NetHandlerPlayClient netHandlerPlayClient, StatisticsManager statisticsManager)
    {
        super(mc, world, netHandlerPlayClient, statisticsManager);
        this.movementInput = new ZMovementInput();
    }

    @Override
    public void onLivingUpdate()
    {
        if (Zombe.get().ZController.flyModOn && !this.capabilities.isFlying)
        {
            this.capabilities.isFlying = true;
            this.sendPlayerAbilities();
        }

        if (super.movementInput != this.movementInput)
            super.movementInput = this.movementInput;

        // step assist
        if (Zombe.getConfig().enableStep)
            this.stepHeight = 1.1f;
        else
            this.stepHeight = 0.6f;

        // disable vanilla flight
        if (Zombe.getConfig().disableCreativeFlight)
            this.flyToggleTimer = 0;

        this.movementInput.block = Zombe.get().ZController.flyModOn;
        super.onLivingUpdate();
    }

    @Override
    public void onUpdateWalkingPlayer()
    {
        if (Zombe.get().ZController.softFallOn())
        {
            boolean sneaking = this.isSneaking();
            if (sneaking != wasSneaking)
            {
                if (sneaking)
                {
                    connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.START_SNEAKING));
                }
                else
                {
                    connection.sendPacket(new CPacketEntityAction(this, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                wasSneaking = sneaking;
            }
            double xChange = this.posX - this.oldPosX;
            double yChange = getEntityBoundingBox().minY - oldMinY;
            double zChange = posZ - oldPosZ;
            double rotationChange = rotationYaw - oldRotationYaw;
            double pitchChange = rotationPitch - oldRotationPitch;
            boolean sendMovementUpdate = xChange * xChange + yChange * yChange + zChange * zChange > 9.0E-4D || ticksSinceMovePacket >= 20;
            boolean sendLookUpdate = rotationChange != 0.0D || pitchChange != 0.0D;
            boolean ground = true;
            if (Zombe.get().ZController.flyModOn)
            {
                ground = !this.capabilities.allowFlying;
            }
            if (sendMovementUpdate && sendLookUpdate)
            {
                connection.sendPacket(new CPacketPlayer.PositionRotation(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.rotationYaw, this.rotationPitch, ground));
            }
            else if (sendMovementUpdate)
            {
                connection.sendPacket(new CPacketPlayer.Position(this.posX, this.getEntityBoundingBox().minY, this.posZ, ground));
            }
            else if (sendLookUpdate)
            {
                connection.sendPacket(new CPacketPlayer.Rotation(this.rotationYaw, this.rotationPitch, ground));
            }
            else
            {
                connection.sendPacket(new CPacketPlayer(ground));
            }
            ++ticksSinceMovePacket;
            if (sendMovementUpdate)
            {
                oldPosX = posX;
                oldMinY = getEntityBoundingBox().minY;
                oldPosZ = posZ;
                ticksSinceMovePacket = 0;
            }
            if (sendLookUpdate)
            {
                oldRotationPitch = rotationPitch;
                oldRotationYaw = rotationYaw;
            }
        }
        else
        {
            super.onUpdateWalkingPlayer();
        }
    }

    @Override
    public void moveEntityWithHeading(float f1, float f2)
    {
        super.moveEntityWithHeading(f1, f2);
        if (this.isOnLadder() && !Zombe.get().ZController.flyModOn && Zombe.get().ZController.sprintModOn)
        {
            if (this.isCollidedHorizontally)
            {
                if (this.rotationPitch < -30F)
                {
                    double speed = Zombe.get().ZController.getSpeed();
                    this.moveEntity(0D, speed, 0D);
                }
            }
            else if (!isSneaking() && this.rotationPitch > 40F)
            {
                double speed = Zombe.get().ZController.getSpeed();
                this.moveEntity(0D, -speed, 0D);
            }
        }
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        if (Zombe.get().ZController.softFallOn())
            return;
        super.fall(distance, damageMultiplier);
    }

    @Override
    public float getFovModifier()
    {
        if (!Zombe.getConfig().disabled && Zombe.get().ZController.flyModOn)
        {
            if (Zombe.getConfig().disableFov)
                return 1.0F;
        }
        return super.getFovModifier();
    }

    @Override
    public boolean isOnLadder()
    {
        return !Zombe.get().ZController.flyModOn && super.isOnLadder();
    }

    @Override
    public void jump()
    {
        if (Zombe.get().ZController.sprintModOn && !this.capabilities.isFlying)
            this.motionY = 0.42F + 1.25 * Zombe.getConfig().jumpModifier * Zombe.get().ZController.getSpeed();
        else
            super.jump();
    }
    /* No longer part of EntityPlayerSP?
    @Override
    public float getToolDigEfficiency(Block b)
    {
        float f = super.getToolDigEfficiency(b);
        if (ZoMBe.get().ZController.flyModOn && (!this.onGround || (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))))
            f *= 5.0F;
        return f;
    }
    */
    @Override
    public boolean isSneaking()
    {
        return (Zombe.get().ZController.flyModOn && movementInput.wasSneaking) || super.isSneaking();
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        if (Zombe.get().ZController.noClipOn && Zombe.get().ZController.flyModOn)
            this.noClip = true;
        return super.pushOutOfBlocks(x, y, z);
    }

    @Override
    public void moveEntity(double x, double y, double z)
    {
        if (Zombe.get().ZController.noClipOn && Zombe.get().ZController.flyModOn)
            this.noClip = true;
        super.moveEntity(x, y, z);
    }

    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return !Zombe.get().ZController.noClipOn && super.isEntityInsideOpaqueBlock();
    }
}
