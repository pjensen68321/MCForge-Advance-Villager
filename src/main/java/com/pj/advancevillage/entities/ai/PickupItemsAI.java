package com.pj.advancevillage.entities.ai;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import com.pj.advancevillage.entities.EntityAdvanceVillager;
import com.pj.functions.Functions;

public class PickupItemsAI extends EntityAIBase {

	private EntityAdvanceVillager entity;

	private double xPosition;
	private double yPosition;
	private double zPosition;

	private Block grassBlock;

	public PickupItemsAI(EntityAdvanceVillager entity) {
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
	 * North = -z East = +x
	 */

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute() {
		List list = this.entity.worldObj.getEntitiesWithinAABB(EntityItem.class, this.entity.getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityItem entityitem = (EntityItem) iterator.next();
				if (!entityitem.isDead && entityitem.getEntityItem() != null) {
					ItemStack itemstack = entityitem.getEntityItem();
					if (this.entity.inventory.addItemStackToInventory(itemstack) != false) {
						this.entity.onItemPickup(entityitem, 1);
						entityitem.setDead();
					}
				}
			}
		}
		// never does anything else but collecting items, so no path
		return false;
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
	}

}
