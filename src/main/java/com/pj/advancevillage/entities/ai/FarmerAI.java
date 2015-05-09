package com.pj.advancevillage.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import com.pj.advancevillage.entities.EntityAdvanceVillager;
import com.pj.functions.Functions;

public class FarmerAI extends EntityAIBase {

	private EntityAdvanceVillager entity;
	
	Vec3 farmBase;
	
	private double lookX;
	private double lookZ;
	private double xPosition;
	private double yPosition;
	private double zPosition;

	private Block grassBlock;

	public FarmerAI(EntityAdvanceVillager entity) {
		this.entity = entity;
		farmBase = entity.getPosition(1.0f);
		farmBase.xCoord = -124;
		farmBase.yCoord = 71;
		farmBase.zCoord = 277;
		//Functions.SendMessageToChat("Farm is at: " + farmBase.toString());
		
		
		
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
	 * North = -z
	 * East = +x
	 */
	
	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	private boolean goingToMatureWheat = false;
	Vec3 wheatPosition;
	private boolean atBase = false;
	boolean startup = true;
	public boolean shouldExecute() {
		if (startup){
			startup = false;
			
		}
//		Vec3 grassPos;
//		Vec3 myPos = grassPos = this.entity.getPosition(1.0f);
//		Block grassBlock;
//		boolean foundGrass = false;
//		double minD = 100000;
		//Functions.SendMessageToChat("finding wheat");
		boolean foundMatureWheat = false;
		
		if (goingToMatureWheat)
		{
			Functions.SendMessageToChat("breaking block " + wheatPosition.toString());
			Functions.breakBlock(entity.worldObj, wheatPosition.xCoord, wheatPosition.yCoord, wheatPosition.zCoord);
			goingToMatureWheat = false;
		}
		
		wheatPosition = farmBase;
		int y = (int) farmBase.yCoord;
		// find fully grown wheat
		for (int x = (int) (farmBase.xCoord - 4); x < farmBase.xCoord + 5; x++) {
			for (int z = (int) (farmBase.zCoord - 10); z < farmBase.zCoord - 1; z++) {
				if (!((x == ((int) farmBase.xCoord)) && (z == ((int) farmBase.zCoord) -6))){
					Block b = this.entity.worldObj.getBlock(x, y, z);
					if (b == Blocks.wheat) {
						int meta = this.entity.worldObj.getBlockMetadata(x, y, z);
						if (meta == 7){
							//Functions.SendMessageToChat(b.getClass().getSimpleName() + " : " + meta);
							foundMatureWheat = true;
							wheatPosition = Vec3.createVectorHelper(x, y, z);
							goingToMatureWheat = true;
						}
						
					}
				}
			}
		}
		
		// find empty farmland
		for (int x = (int) (farmBase.xCoord - 4); x < farmBase.xCoord + 5; x++) {
			for (int z = (int) (farmBase.zCoord - 10); z < farmBase.zCoord - 1; z++) {
				if (!((x == ((int) farmBase.xCoord)) && (z == ((int) farmBase.zCoord) -6))){
					Block b = this.entity.worldObj.getBlock(x, y, z);
					if (b != Blocks.wheat) {
						Block b2 = this.entity.worldObj.getBlock(x, y-1, z);
						if (b2 == Blocks.farmland){
							// seed
							ItemStack[] stacks = this.entity.inventory.getAllItems();
	                    	for (int slotNumber = 0 ; slotNumber < stacks.length ; slotNumber++)
	                    	{
	                    		if (stacks[slotNumber].getItem() == Items.wheat_seeds){
	                    			Functions.SendMessageToChat("Seeds are in slot number " + slotNumber);
	                    		}
	                    	}
						}else if (b2 == Blocks.grass || b2 == Blocks.dirt){
							// use hoe
						}else {
							// remove block, set dirt
						}
					}
				}
			}
		}

		xPosition = wheatPosition.xCoord;
		yPosition = wheatPosition.yCoord;
		zPosition = wheatPosition.zCoord;
		
		atBase = !foundMatureWheat && !goingToMatureWheat;
		if (atBase){
			if (!printetAtHome){
				Functions.SendMessageToChat("home");
				printetAtHome = true;
			}
		}else{
			printetAtHome = false;
		}
		return true;
	}
	private boolean printetAtHome = false;

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
		this.entity.getNavigator().tryMoveToXYZ(this.farmBase.xCoord - 1, this.farmBase.yCoord, this.farmBase.zCoord, 1.0f);
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
