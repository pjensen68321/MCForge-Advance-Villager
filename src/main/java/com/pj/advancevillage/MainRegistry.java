package com.pj.advancevillage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

import com.pj.advancevillage.blocks.ModBlocks;
import com.pj.advancevillage.entities.GuiHandler;
import com.pj.advancevillage.entities.ModEntities;
import com.pj.advancevillage.items.ModItems;
import com.pj.advancevillage.village.AdvanceVillage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION)
public class MainRegistry {

	@SidedProxy(clientSide = "com.pj.advancevillage.ClientProxy", serverSide = "com.pj.advancevillage.ServerProxy")
	public static ServerProxy proxy;

	@Instance(Strings.MOD_ID)
	public static MainRegistry modInstance;

	static List<AdvanceVillage> villages = new ArrayList<AdvanceVillage>();

	// Loads before
	@EventHandler
	public static void PreLoad(FMLPreInitializationEvent PreEvent) {

		//readNBTFromFile();

		ModItems.mainRegistry();
		ModBlocks.mainRegistry();
		ModEntities.mainRegistry();
		ModRecipies.mainRegistry();
		
		NetworkRegistry.INSTANCE.registerGuiHandler(modInstance, new GuiHandler());
		
		proxy.registerRenderThings();

	}

	// Loads during
	@EventHandler
	public static void load(FMLInitializationEvent event) {

	}

	// Loads after
	@EventHandler
	public static void postLoad(FMLPostInitializationEvent PostEvent) {

	}
	
	public NBTTagCompound getNBTData(){
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		
		nbttagcompound.setInteger("numberOfVillages", this.villages.size());
		for (int i = 0 ; i < villages.size() ; i++){
			//nbttagcompound.setTag("village" + i, villages.get(i).getNBTdata());
		}
		
		
		return nbttagcompound;
	}
	
	// save mod data to file
	// static NBTTagCompound nbttagcompound = new NBTTagCompound();
	private void writeNBTToFile() {
		try {

			File file = new File(cpw.mods.fml.common.Loader.instance().getConfigDir().getParent() + "/saves/" + MinecraftServer.getServer().worldServers[0].getWorldInfo().getWorldName() + "", "AdvanceVillageMod.dat");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileoutputstream = new FileOutputStream(file);
			NBTTagCompound nbttagcompound = new NBTTagCompound();

			

			CompressedStreamTools.writeCompressed(nbttagcompound, fileoutputstream);
			fileoutputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private static void readNBTFromFile() {
		try {
			MinecraftServer.getServer().worldServers[0].checkSessionLock();

			File file = new File(cpw.mods.fml.common.Loader.instance().getConfigDir().getParent() + "/saves/" + MinecraftServer.getServer().worldServers[0].getWorldInfo().getWorldName() + "", "AdvanceVillageMod.dat");
			if (!file.exists()) {
				return;
			}
			FileInputStream fileinputstream = new FileInputStream(file);
			NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
			// if (nbttagcompound.hasKey("my_value_a")) {
			// this.my_value_a = nbttagcompound.getBoolean("my_value_a");
			// }
			// if (nbttagcompound.hasKey("my_value_b")) {
			// this.my_value_b = nbttagcompound.getInteger("my_value_b");
			// }
			fileinputstream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
