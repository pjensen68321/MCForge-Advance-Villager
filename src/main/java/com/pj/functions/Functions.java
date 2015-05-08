package com.pj.functions;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
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
	
	public static void breakBlock(World world, double x, double y, double z){
		breakBlock(world, (int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
	}
	public static void breakBlock(World world, int x, int y, int z){
		if(world.getBlock(x, y, z) != Blocks.bedrock){
			world.getBlock(x, y, z).dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
