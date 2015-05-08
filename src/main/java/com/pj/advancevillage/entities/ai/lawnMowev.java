package com.pj.advancevillage.entities.ai;

import com.pj.functions.Functions;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;

public class lawnMowev extends EntityAIBase {

	private EntityCreature entity;

	private double lookX;
	private double lookZ;
	private double xPosition;
	private double yPosition;
	private double zPosition;

	private Block grassBlock;

	public lawnMowev(EntityCreature entity) {
		this.entity = entity;
		this.setMutexBits(1);
	}

	/**
	 * shouldExecute() runs while it returns false when shouldExecute() returns
	 * true, startExecuting() is running once startExecuting() is where you
	 * create a plan. updateTask() is then running every time before
	 * continueExecuting() when continueExecuting() is returning false the loop
	 * start from shouldExecute()
	 */

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		Vec3 grassPos;
		Vec3 myPos = grassPos = this.entity.getPosition(1.0f);
		Block grassBlock;
		boolean foundGrass = false;
		double minD = 100000;
		// Functions.SendMessageToChat("finding block");

		for (int y = (int) (myPos.yCoord - 2); y < myPos.yCoord + 2; y++) {
			for (int x = (int) (myPos.xCoord - 2); x < myPos.xCoord + 2; x++) {
				for (int z = (int) (myPos.zCoord - 2); z < myPos.zCoord + 2; z++) {
					Block b = this.entity.worldObj.getBlock(x, y, z);
					if (b == Blocks.tallgrass) {
						Vec3 thisPos = Vec3.createVectorHelper(x, y, z);
						if (minD > Functions.calcDistance(myPos, thisPos)) {
							minD = Functions.calcDistance(myPos, thisPos);
							grassPos = thisPos;
							grassBlock = b;
							foundGrass = true;
						}
					}
				}
			}
		}

		if (foundGrass) {
			Functions.SendMessageToChat("breaking block " + grassPos.toString());
			Functions.breakBlock(entity.worldObj, grassPos.xCoord, grassPos.yCoord, grassPos.zCoord);
		}

		xPosition = grassPos.xCoord;
		yPosition = grassPos.yCoord;
		zPosition = grassPos.zCoord;

		return true;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1.0f);

	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		// this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX
		// + this.lookX, this.idleEntity.posY +
		// (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ +
		// this.lookZ, 10.0F, (float)this.idleEntity.getVerticalFaceSpeed());
	}

}
