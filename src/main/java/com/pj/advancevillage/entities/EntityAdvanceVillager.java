package com.pj.advancevillage.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import java.util.Random;

import com.pj.advancevillage.entities.ai.FarmerAI;
import com.pj.advancevillage.entities.ai.PickupItemsAI;
import com.pj.functions.Functions;

public class EntityAdvanceVillager extends EntityAnimal {

	public InventoryAI inventory;
	private boolean isMale;

	public EntityAdvanceVillager(World worldIn, boolean isMale) {
		super(worldIn);
		this.isMale = isMale;
		this.setSize(0.9f, 1.9f);
		this.setCanPickUpLoot(true);
		inventory = new InventoryAI(this);

		/*
		 * this.tasks.addTask(0, new EntityAIWander(this, 0.3d));
		 * this.tasks.addTask(1, new EntityAIPanic(this, 0.1d));
		 * this.tasks.addTask(2, new EntityAILookIdle(this));
		 * this.tasks.addTask(3, new EntityAISwimming(this));
		 * this.tasks.addTask(4, new EntityAITempt(this, 1.5d, Items.coal,
		 * false));
		 */

		this.tasks.addTask(1, new FarmerAI(this));
		this.tasks.addTask(0, new PickupItemsAI(this));

		Functions.SendMessageToChat("hello, the time is: " + Functions.GetTimeOfDay());

		this.setCurrentItemOrArmor(0, new ItemStack(Items.stone_hoe));
		// this.setCurrentItemOrArmor(1, new
		// ItemStack(Items.diamond_chestplate));
		// this.setCurrentItemOrArmor(2, new ItemStack(Items.diamond_leggings));
		// this.setCurrentItemOrArmor(3, new ItemStack(Items.diamond_boots));
		// this.setCurrentItemOrArmor(4, new ItemStack(Items.diamond_helmet));
	}

	public boolean isAIEnabled() {
		return true;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0d);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3d);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		Random random = new Random(this.worldObj.getTotalWorldTime());
		if (random.nextInt(2) == 0){
			return new EntityAdvanceVillager(worldObj, true);
		}else{
			return new EntityAdvanceVillager(worldObj, false);
		}
	}

	public AxisAlignedBB getEntityBoundingBox() {
		return this.boundingBox;
	}

}
