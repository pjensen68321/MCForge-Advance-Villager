package com.pj.advancevillage.entities.ai;

import com.pj.functions.Functions;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class FarmerAI  extends EntityAIBase {

    private EntityLiving entity;
    
    private double lookX;
    private double lookZ;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    
    private Block grassBlock;
    
    public FarmerAI(EntityLiving entity)
    {
        this.entity = entity;
        this.setMutexBits(3);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	//this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0D);
    	Vec3 myPosition = this.entity.getPosition(1.0f);
    	myPosition.yCoord -= 2;
    	grassBlock = Functions.getBlock(this.entity.worldObj, myPosition);
    	
    	//Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
    	Functions.SendMessageToChat(grassBlock.toString());
    	
        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 0.2f);
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        //this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0F, (float)this.idleEntity.getVerticalFaceSpeed());
    }

}
