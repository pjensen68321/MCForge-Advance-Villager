package com.pj.advancevillage.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class EntityMale extends EntityAnimal{

	public EntityMale(World worldIn) {
		super(worldIn);
		this.setSize(1.0f, 1.0f);
		this.tasks.addTask(0, new EntityAIWander(this, 0.3d));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0d));
		this.tasks.addTask(2, new EntityAILookIdle(this));
		this.tasks.addTask(3, new EntityAISwimming(this));
		this.tasks.addTask(4, new EntityAITempt(this, 1.5d, Items.coal, false));
		
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(
				"hello, the time is: " + (Minecraft.getMinecraft().theWorld.getWorldTime() % 24000)
				));
		
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
