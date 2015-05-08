package com.pj.functions;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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

	public static Block getBlock(World worldObj, Vec3 pos) {
		return worldObj.getBlock((int) Math.abs(pos.xCoord), (int) Math.abs(pos.yCoord), (int) Math.abs(pos.zCoord));
	}

}
