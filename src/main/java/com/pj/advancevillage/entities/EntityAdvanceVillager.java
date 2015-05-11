package com.pj.advancevillage.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import com.pj.advancevillage.MainRegistry;
import com.pj.advancevillage.entities.ai.FarmerAI;
import com.pj.advancevillage.entities.ai.ForresterAI;
import com.pj.advancevillage.entities.ai.PickupItemsAI;
import com.pj.advancevillage.entities.ai.lookIdleAtArea;
import com.pj.functions.nameGenerator;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EntityAdvanceVillager extends EntityAnimal implements IInventory {

	// public InventoryAI inventory;
	private boolean isMale;
	private ItemStack[] inv;

	private String firstname = "";
	private String surname = "";
	private String occupation = "";

	public EntityAdvanceVillager(World worldIn, boolean isMale) {
		super(worldIn);
		this.isMale = isMale;
		this.setSize(0.9f, 1.9f);
		this.setCanPickUpLoot(true);

		// inventory = new InventoryAI(this);
		inv = new ItemStack[36];
		System.out.println("creating mob on client " + worldIn.isRemote);
		// (this.worldObj.getWorldInfo().getWorldName()

		/*
		 * this.tasks.addTask(priority++, new EntityAIWander(this, 0.3d));
		 * this.tasks.addTask(priority++, new EntityAIPanic(this, 0.1d));
		 * this.tasks.addTask(priority++, new EntityAILookIdle(this));
		 * this.tasks.addTask(priority++, new EntityAISwimming(this));
		 * this.tasks.addTask(priority++, new EntityAITempt(this, 1.5d,
		 * Items.coal, false));
		 */
		if (!worldIn.isRemote) {

			int priority = 0; // makes sure that the order they are in here is
								// the
								// priority. where the first is the most
								// important
			this.tasks.addTask(priority++, new EntityAIPanic(this, 1.5f));
			this.tasks.addTask(priority++, new PickupItemsAI(this));

			// jobs
			// this.tasks.addTask(priority++, new FarmerAI(this,
			// Items.wheat_seeds, Blocks.wheat));
			this.tasks.addTask(priority++, new FarmerAI(this, Items.potato));
			this.tasks.addTask(priority++, new ForresterAI(this));

			// idle work
			this.tasks.addTask(priority++, new lookIdleAtArea(this, 0));

		}

		getFirstame();
		getSurname();
	}

	public Boolean IsMale() {
		return isMale;
	}

	public String getFirstame() {
		if (firstname.equals(""))
			setFirstname(generateFirstname());
		return this.firstname;
	}

	public String getSurname() {
		if (surname.equals(""))
			setSurname(generateSurname());
		return this.surname;
	}

	public String getOccupation() {
		return this.occupation;
	}

	private void setFirstname(String newFirstname) {
		this.firstname = newFirstname;
		this.setCustomNameTag(this.occupation + " " + this.firstname + " " + this.surname);
	}

	public void setSurname(String newSurname) {
		this.surname = newSurname;
		this.setCustomNameTag(this.occupation + " " + this.firstname + " " + this.surname);
	}

	public void setOccupation(String newOccupation) {
		this.occupation = newOccupation;
		this.setCustomNameTag(this.occupation + " " + this.firstname + " " + this.surname);
	}

	private String generateFirstname() {
		return nameGenerator.getRandomFirstname(this.isMale);
	}

	private String generateSurname() {
		return nameGenerator.getRandomSurname(this.isMale);
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
		if (random.nextInt(2) == 0) {
			return new EntityAdvanceVillager(worldObj, true);
		} else {
			return new EntityAdvanceVillager(worldObj, false);
		}
	}

	public AxisAlignedBB getEntityBoundingBox() {
		return this.boundingBox;
	}

	// inventory stuff

	@Override
	public void onDeath(DamageSource source) {
		ItemStack[] stacks = getAllItems();
		for (ItemStack stack : stacks) {
			this.dropItem(stack.getItem(), stack.stackSize);
		}
		super.onDeath(source);
	};

	@Override
	public boolean canMateWith(EntityAnimal otherEntity) {
		// if (otherEntity.getClass() == this.getClass()) {
		// EntityAdvanceVillager AdvVillager = (EntityAdvanceVillager)
		// otherEntity;
		// return AdvVillager.isMale == !this.isMale;
		// }

		return false;
	};

	@Override
	public boolean interact(EntityPlayer player) {
		
		System.out.println("Right clicked 2");
		player.openGui(MainRegistry.modInstance, 0, this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
		return super.interact(player);
	};

	@SubscribeEvent
	public void onEntityRightClicked(EntityInteractEvent event) {
		System.out.println("Right clicked");
		// ItemStack itemstack = event.entityPlayer.inventory.getCurrentItem();
		event.entityPlayer.openGui(MainRegistry.modInstance, 0, this.worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		// make villager stand still
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		// let village move again
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return true;
	}

	public ItemStack[] getAllItems() {
		List<ItemStack> stackList = new ArrayList<ItemStack>();
		for (int i = 0; i < getSizeInventory(); i++) {
			ItemStack s = getStackInSlot(i);
			if (s != null) {
				stackList.add(s);
			}
		}
		ItemStack[] stacks = new ItemStack[stackList.size()];
		for (int i = 0; i < stacks.length; i++) {
			stacks[i] = stackList.get(i);
		}
		return stacks;
	}

	private int getSlotNumber(Item item) {
		for (int i = 0; i < this.inv.length; ++i) {
			if (this.inv[i] != null && this.inv[i].getItem() == item) {
				return i;
			}
		}

		return -1;
	}

	public boolean consumeInventoryItem(Item p_146026_1_) {
		int i = this.getSlotNumber(p_146026_1_);

		if (i < 0) {
			return false;
		} else {
			if (--this.inv[i].stackSize <= 0) {
				this.inv[i] = null;
			}

			return true;
		}
	}

	/**
	 * Reads from the given tag list and fills the slots in the inventory with
	 * the correct items.
	 */
	public void readInventoryFromNBT(NBTTagCompound tagCompound) {

		NBTTagList tagList = tagCompound.getTagList("Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inv.length) {
				inv[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		setFirstname(tagCompound.getString("firstname"));
		this.surname = tagCompound.getString("surname");

	}

	/**
	 * Writes the inventory out as a list of compound tags. This is where the
	 * slot indices are used (+100 for armor, +80 for crafting).
	 */
	public void writeInventoryToNBT(NBTTagCompound tagCompound) {

		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < inv.length; i++) {
			ItemStack stack = inv[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
		tagCompound.setString("firstname", this.firstname);
		tagCompound.setString("surname", this.surname);

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
		this.writeInventoryToNBT(p_70014_1_);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		readInventoryFromNBT(p_70037_1_);
	}

}
