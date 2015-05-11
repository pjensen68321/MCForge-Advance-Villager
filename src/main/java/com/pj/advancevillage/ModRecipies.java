package com.pj.advancevillage;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipies {

	public static void mainRegistry(){
		GameRegistry.addRecipe(new ItemStack(Items.egg, 1), "###", "#%#", "###", '#', Items.redstone, '%', Items.paper);
	}
}
