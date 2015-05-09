package com.pj.functions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Functions {

	public static void SendMessageToChat(String messeage) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(messeage));
	}

	private static int ticksPrHour = 1000;
	private static long ticksPrDay = 24000;

	public static int GetHourOfDay() {
		long hour = (Math.abs((Minecraft.getMinecraft().theWorld.getWorldTime() % ticksPrDay) / ticksPrHour) + 6) % 24;

		return (int) hour;
	}

	public static int GetMinutesOfDay() {
		int dayTime = (int) ((Minecraft.getMinecraft().theWorld.getWorldTime() % ticksPrDay) % ticksPrHour);
		dayTime = (dayTime * 60) / ticksPrHour;
		return dayTime;
	}

	public static String GetTimeOfDay() {
		return "" + GetHourOfDay() + ":" + GetMinutesOfDay();
	}

	public static double calcDistance(Vec3 p1, Vec3 p2) {
		return Math.sqrt(Math.pow(p1.xCoord + p2.xCoord, 2) + Math.pow(p1.yCoord + p2.yCoord, 2) + Math.pow(p1.zCoord + p2.zCoord, 2));
	}

	public static void breakBlock(World world, double x, double y, double z) {
		breakBlock(world, (int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
	}

	public static void breakBlock(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z) != Blocks.bedrock) {
			world.getBlock(x, y, z).dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	public static blockInfo[] getBlocksOfType(World world, Vec3 center, int northLength, int eastLength, boolean includeCenter, Block type) {
		List<blockInfo> blocksPositionsList = new ArrayList<blockInfo>();
		int y = (int) center.yCoord;
		for (int x = (int) (center.xCoord - eastLength); x <= center.xCoord + eastLength; x++) {
			for (int z = (int) (center.zCoord - northLength); z <= center.zCoord + northLength; z++) {
				if (includeCenter || !((x == ((int) center.xCoord)) && (z == ((int) center.zCoord)))) {
					Block b = world.getBlock(x, y, z);
					if (b == type) {
						blocksPositionsList.add(new blockInfo(Vec3.createVectorHelper(x, y, z), b, world.getBlockMetadata(x, y, z)));
					}
				}
			}
		}

		blockInfo[] blocksPositions = new blockInfo[blocksPositionsList.size()];
		return blocksPositionsList.toArray(blocksPositions);
	}

	public static blockInfo[] getBlocksNotOfType(World world, Vec3 center, int northLength, int eastLength, boolean includeCenter, Block type) {
		List<blockInfo> blocksPositionsList = new ArrayList<blockInfo>();
		int y = (int) center.yCoord;
		for (int x = (int) (center.xCoord - eastLength); x <= center.xCoord + eastLength; x++) {
			for (int z = (int) (center.zCoord - northLength); z <= center.zCoord + northLength; z++) {
				if (includeCenter || !((x == ((int) center.xCoord)) && (z == ((int) center.zCoord)))) {
					Block b = world.getBlock(x, y, z);
					if (b != type) {
						blocksPositionsList.add(new blockInfo(Vec3.createVectorHelper(x, y, z), b, world.getBlockMetadata(x, y, z)));
					}
				}
			}
		}

		blockInfo[] blocksPositions = new blockInfo[blocksPositionsList.size()];
		return blocksPositionsList.toArray(blocksPositions);
	}

	public static boolean logicalXOR(boolean x, boolean y) {
		return ((x || y) && !(x && y));
	}

	public static class blockInfo {
		public Vec3 position;
		public Block block;
		public int meta;

		public blockInfo(Vec3 pos, Block b, int meta) {
			this.position = pos;
			this.block = b;
			this.meta = meta;
		}

	}

	public static int getFirstFreeSlot(IInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack s = inv.getStackInSlot(i);
			if (s == null) {
				return i;
			}
		}
		return -1;
	}

	public static int addToInventory(IInventory inv, ItemStack stack, int maxNumber) {

		int left = stack.stackSize;
		if (left > maxNumber)
			left = maxNumber;
		int startingWith = left;
		// first look for items of same type, and add to them.
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).getItem() == stack.getItem()) {
				int roomLeftInStack = inv.getStackInSlot(i).getMaxStackSize() - inv.getStackInSlot(i).stackSize;
				if (roomLeftInStack >= left) {
					inv.getStackInSlot(i).stackSize += left;
					left = 0;
				} else {
					left -= roomLeftInStack;
					inv.getStackInSlot(i).stackSize = inv.getStackInSlot(i).getMaxStackSize();
				}
			}
		}
		// stack.stackSize = left;

		// then look for empty and place rest there
		if (left > 0) {
			int freeSlot = getFirstFreeSlot(inv);
			if (freeSlot >= 0) {
				inv.setInventorySlotContents(freeSlot, stack.copy());
				inv.getStackInSlot(freeSlot).stackSize = left;
				left = 0;
			}
		}

		return startingWith - left;
	}
	
	/** 
	 * @param inv
	 * @param stack
	 * @param maxNumber number between 0 and 64, both included
	 */
	public static void removeFromInventory(IInventory inv, ItemStack stack, int maxNumber) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) == stack) {
				if (stack.stackSize > maxNumber) {
					stack.stackSize -= maxNumber;
				} else {
					inv.setInventorySlotContents(i, null);
				}
				return;
			}
		}
	}

	public static void transfereItemStack(IInventory from, IInventory to, ItemStack fromStack, int maxNumber) {
		int added = addToInventory(to, fromStack, maxNumber);
		removeFromInventory(from, fromStack, added);
	}

	public static void cleanInventory(IInventory inv) {
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null && inv.getStackInSlot(i).stackSize == 0) {
				inv.setInventorySlotContents(i, null);
			}
		}
	}
}
