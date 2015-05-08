package com.pj.advancevillage;


import com.pj.advancevillage.blocks.ModBlocks;
import com.pj.advancevillage.entities.ModEntities;
import com.pj.advancevillage.items.ModItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Strings.MOD_ID,name = Strings.MOD_NAME,version = Strings.VERSION)
public class MainRegistry {
	
	@SidedProxy(clientSide = "com.pj.advancevillage.ClientProxy", serverSide = "com.pj.advancevillage.ServerProxy")
	public static ServerProxy proxy;
	
	@Instance(Strings.MOD_ID)
	public static MainRegistry modInstance;
	
	// Loads before
	@EventHandler
	public static void PreLoad(FMLPreInitializationEvent PreEvent){
		ModItems.mainRegistry();
		ModBlocks.mainRegistry();
		ModEntities.mainRegistry();
		proxy.registerRenderThings();
		
	}
	
	// Loads during
	@EventHandler
	public static void load(FMLInitializationEvent event){
		
	}
	
	// Loads after
	@EventHandler
	public static void postLoad(FMLPostInitializationEvent PostEvent){
		
	}
	
}
