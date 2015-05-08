package com.pj.advancevillage.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.registry.GameRegistry;


public class ModBlocks {
	
	public static void mainRegistry(){
		initializeBlocks();
		registerBlocks();
	}
	
	public static Block testBlock;
	
	public static void initializeBlocks(){
		testBlock = new TestBlock(Material.ground).setBlockName("TestBlock").setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public static void registerBlocks(){
		GameRegistry.registerBlock(testBlock, testBlock.getUnlocalizedName());
	}
}
