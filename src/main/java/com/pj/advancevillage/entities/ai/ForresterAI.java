package com.pj.advancevillage.entities.ai;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import com.pj.advancevillage.entities.EntityAdvanceVillager;
import com.pj.advancevillage.pathfinders.ConnectedBlockSearcher;
import com.pj.functions.Functions;
import com.pj.functions.Functions.blockInfo;

public class ForresterAI extends EntityAIBase {

	private EntityAdvanceVillager entity;

	Vec3 farmBase;
	Vec3 farmCenter;

	private Block grassBlock;

	public ForresterAI(EntityAdvanceVillager entity) {
		this.entity = entity;
		farmBase = entity.getPosition(1.0f);

		farmCenter = Vec3.createVectorHelper(-137, 71, 271);
		farmBase = Vec3.createVectorHelper(farmCenter.xCoord, farmCenter.yCoord, farmCenter.zCoord + 6);

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

	Vec3 blockPosition;
	List<Vec3> treeToRemove;
	List<Vec3> leavesToRemove;
	private boolean atBase = false;
	private String goingTo = "";
	private boolean goingFromBase = false;

	private boolean isAxe(Item item) {
		if (item == Items.wooden_axe || item == Items.stone_axe || item == Items.iron_axe || item == Items.golden_axe || item == Items.diamond_axe) {
			return true;
		} else {
			return false;
		}
	}

	public boolean shouldExecute() {
		if (entity.IsMale()) return false;
		
		if (atBase && !goingFromBase) {
			// drop items in chest
			Block b = entity.worldObj.getBlock((int) farmBase.xCoord - 2, (int) farmBase.yCoord, (int) farmBase.zCoord - 1);
			if (b == Blocks.chest) {
				IInventory inv = Blocks.chest.func_149951_m(entity.worldObj, (int) farmBase.xCoord - 2, (int) farmBase.yCoord, (int) farmBase.zCoord - 1);

				ItemStack[] stacks = entity.getAllItems();
				int numberOfSeedStacks = 0;
				for (ItemStack stack : stacks) {
//					if (stack.getItem() != Blocks.sapling. && !isAxe(stack.getItem())) {
//						Functions.transfereItemStack(entity, inv, stack, 64);
//					} else {
//						if (stack.getItem() == Items.apple) {
//							if (stack.stackSize > 10 || numberOfSeedStacks > 1) {
//								Functions.transfereItemStack(entity, inv, stack, (stack.stackSize - 5) + numberOfSeedStacks * 10);
//							}
//							numberOfSeedStacks++;
//						} else {
//							// its a hoe, don't drop it
//						}
//					}
				}
			}
		}

		goingFromBase = goingTo.equals("base");
		// go back
		blockPosition = farmBase;
		goingTo = "base";

		// find fully grown wheat
		blockInfo[] treeButtoms = Functions.getBlocksOfType(entity.worldObj, farmCenter, 4, 4, false, Blocks.log);
		for (blockInfo wb : treeButtoms) {
			blockPosition = wb.position;
			treeToRemove = ConnectedBlockSearcher.findConnectedBlocks(entity.worldObj, blockPosition, Blocks.log);
			leavesToRemove = ConnectedBlockSearcher.findConnectedBlocks(entity.worldObj, treeToRemove.get(treeToRemove.size()-1), Blocks.leaves);
			goingTo = "harvest";
			break;
		}

		if (!goingTo.equals("base"))
			atBase = false;

		return !atBase;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting() {

		boolean endOfRoute = this.entity.getNavigator().noPath();

//		if (endOfRoute && goingToCheckPoint) {
//			goingToCheckPoint = false;
//			endOfRoute = false;
//			this.entity.getNavigator().tryMoveToXYZ(blockPosition.xCoord + 1.5, blockPosition.yCoord-1, blockPosition.zCoord + 1.5, 1.0f);
//		}

		if (endOfRoute) {
			if (goingTo.equals("base")) {
				atBase = true;

			} else if (goingTo.equals("harvest")) {
				goingTo = "harvesting";
			} else if (goingTo.equals("harvesting")) {
				// waiting to finish
			} else if (goingTo.equals("farmland")) {
				this.entity.worldObj.setBlock((int) blockPosition.xCoord, (int) blockPosition.yCoord, (int) blockPosition.zCoord, Blocks.sapling);
			}
		}

		return !endOfRoute || goingTo.equals("harvesting");
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	boolean goingToCheckPoint = true;

	public void startExecuting() {
		
		
		this.entity.getNavigator().tryMoveToXYZ(blockPosition.xCoord + 1.5, blockPosition.yCoord-1, blockPosition.zCoord + 1.5, 1.0f);
		
//		if (Functions.logicalXOR(goingTo.equals("base"), goingFromBase)) {
//			goingToCheckPoint = true;
//			this.entity.getNavigator().tryMoveToXYZ(this.farmCenter.xCoord + 0.5, this.farmCenter.yCoord, this.farmCenter.zCoord + 0.5 + 3, 1.0f);
//		} else {
//			this.entity.getNavigator().tryMoveToXYZ(blockPosition.xCoord + 0.5, blockPosition.yCoord, blockPosition.zCoord + 0.5, 1.0f);
//		}
	}

	/**
	 * Updates the task
	 */
	public void updateTask() {
		// this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX
		// + this.lookX, this.idleEntity.posY +
		// (double)this.idleEntity.getEyeHeight(), this.idleEntity.posZ +
		// this.lookZ, 10.0F, (float)this.idleEntity.getVerticalFaceSpeed());
		
		if (goingTo.equals("harvesting")){
			//Items.iron_axe.getDigSpeed(itemstack, block, metadata)
			if (leavesToRemove.size() > 0){
				Vec3 p = leavesToRemove.get(0);
				leavesToRemove.remove(0);
				Functions.breakBlock(entity.worldObj, p.xCoord, p.yCoord, p.zCoord);
			}else if (treeToRemove.size() > 0){
				Vec3 p = treeToRemove.get(0);
				treeToRemove.remove(0);
				Functions.breakBlock(entity.worldObj, p.xCoord, p.yCoord, p.zCoord);
			}else{
				goingTo = "doneharvesting";
			}
//			for (Vec3 p : leavesToRemove){
//				Functions.breakBlock(entity.worldObj, p.xCoord, p.yCoord, p.zCoord);
//			}
//			for (Vec3 p : treeToRemove){
//				Functions.breakBlock(entity.worldObj, p.xCoord, p.yCoord, p.zCoord);
//			}
			
			
		}
		
	}

}
