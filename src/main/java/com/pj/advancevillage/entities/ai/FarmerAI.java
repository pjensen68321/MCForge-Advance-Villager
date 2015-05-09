package com.pj.advancevillage.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import com.pj.advancevillage.entities.EntityAdvanceVillager;
import com.pj.advancevillage.entities.InventoryAI;
import com.pj.functions.Functions;
import com.pj.functions.Functions.blockInfo;

public class FarmerAI extends EntityAIBase {

	private EntityAdvanceVillager entity;

	Vec3 farmBase;
	Vec3 farmCenter;

	private Block grassBlock;

	public FarmerAI(EntityAdvanceVillager entity) {
		this.entity = entity;
		farmBase = entity.getPosition(1.0f);

		farmBase = Vec3.createVectorHelper(-124, 71, 277);
		farmCenter = Vec3.createVectorHelper(farmBase.xCoord, farmBase.yCoord, farmBase.zCoord - 6);

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
	private boolean atBase = false;
	private String goingTo = "";
	private boolean goingFromBase = false;

	public boolean shouldExecute() {

		if (atBase && !goingFromBase) {
			// drop items in chest
			Block b = entity.worldObj.getBlock((int) farmBase.xCoord - 2, (int) farmBase.yCoord, (int) farmBase.zCoord - 1);
			if (b == Blocks.chest) {
				// BlockChest chest = (BlockChest) b;
				IInventory inv = Blocks.chest.func_149951_m(entity.worldObj, (int) farmBase.xCoord - 2, (int) farmBase.yCoord, (int) farmBase.zCoord - 1);

				ItemStack[] stacks = entity.getAllItems();
				int numberOfSeedStacks = 0;
				for (ItemStack stack : stacks) {
					if (stack.getItem() != Items.wheat_seeds) {
						Functions.transfereItemStack(entity, inv, stack, 64);
					} else {
						if (stack.stackSize > 10 || numberOfSeedStacks > 1) {
							Functions.transfereItemStack(entity, inv, stack, (stack.stackSize - 5) + numberOfSeedStacks * 10);
						}
						numberOfSeedStacks++;
					}
				}
			}
		}

		goingFromBase = goingTo.equals("base");
		// go back
		blockPosition = farmBase;
		goingTo = "base";

		// find fully grown wheat
		blockInfo[] wheatBlocks = Functions.getBlocksOfType(entity.worldObj, farmCenter, 4, 4, false, Blocks.wheat);
		for (blockInfo wb : wheatBlocks) {
			if (wb.meta == 7) {
				blockPosition = wb.position;
				goingTo = "wheat";
			}
		}

		// find empty farmland
		blockInfo[] airBlocks = Functions.getBlocksOfType(entity.worldObj, farmCenter, 4, 4, false, Blocks.air);
		for (blockInfo wb : airBlocks) {
			Block b = entity.worldObj.getBlock((int) wb.position.xCoord, (int) wb.position.yCoord - 1, (int) wb.position.zCoord);
			if (b == Blocks.farmland) {
				// going to place seeds if seeds in inventory
				ItemStack[] stacks = this.entity.getAllItems();
				for (int slotNumber = 0; slotNumber < stacks.length; slotNumber++) {
					if (stacks[slotNumber].getItem() == Items.wheat_seeds) {
						// found seeds in inventory

						blockPosition = wb.position;
						goingTo = "farmland";
					}
				}
			} else if (b == Blocks.grass || b == Blocks.dirt) {
				// use hoe
			} else {
				// remove block, set dirt
			}
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

		if (endOfRoute) {
			if (goingTo.equals("base")) {
				atBase = true;
				//this.entity.getLookHelper().setLookPosition(entity.posX + 0, entity.posY + (double) entity.getEyeHeight(), entity.posZ + 1, 10.0F, (float) entity.getVerticalFaceSpeed());
			} else if (goingTo.equals("wheat")) {
				Functions.breakBlock(entity.worldObj, blockPosition.xCoord, blockPosition.yCoord, blockPosition.zCoord);
			} else if (goingTo.equals("farmland")) {
				this.entity.worldObj.setBlock((int) blockPosition.xCoord, (int) blockPosition.yCoord, (int) blockPosition.zCoord, Blocks.wheat);
				this.entity.consumeInventoryItem(Items.wheat_seeds);
			}
		}

		return !endOfRoute;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting() {
		if (Functions.logicalXOR(goingTo.equals("base"), goingFromBase)) {
			this.entity.getNavigator().tryMoveToXYZ(this.farmBase.xCoord - 2, this.farmBase.yCoord, this.farmBase.zCoord, 1.0f);
		}
		this.entity.getNavigator().tryMoveToXYZ(blockPosition.xCoord, blockPosition.yCoord, blockPosition.zCoord, 1.0f);
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
