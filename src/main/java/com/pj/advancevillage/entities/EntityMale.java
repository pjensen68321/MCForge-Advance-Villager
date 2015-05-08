package com.pj.advancevillage.entities;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import com.pj.advancevillage.entities.ai.FarmerAI;
import com.pj.functions.Functions;

public class EntityMale extends EntityAnimal{

	public EntityMale(World worldIn) {
		super(worldIn);
		this.setSize(0.9f, 1.9f);
		/*this.tasks.addTask(0, new EntityAIWander(this, 0.3d));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.1d));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAITempt(this, 1.5d, Items.coal, false));*/
		
		this.tasks.addTask(0, new FarmerAI(this));
		
		Functions.SendMessageToChat("hello, the time is: " + Functions.GetTimeOfDay());
		
		
	}
	
	public boolean isAIEnabled(){
		return true;
	}
	
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0d);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3d);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityMale(worldObj);
	}

}
