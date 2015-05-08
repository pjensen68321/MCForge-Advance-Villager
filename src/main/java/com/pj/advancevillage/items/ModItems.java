package com.pj.advancevillage.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
	
	public static void mainRegistry(){
		initializeItems();
		registerItems();
	}
	
	public static Item testItem;
	
	public static void initializeItems(){
		testItem = new Item().setUnlocalizedName("TestItem");
	}
	
	public static void registerItems(){
		registerItem(testItem);
	}
	
	private static void registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName());
	}
	
}
