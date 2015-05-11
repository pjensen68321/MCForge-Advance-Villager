package com.pj.advancevillage.entities;

import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;

import com.pj.advancevillage.MainRegistry;


public class ModEntities {
	
	public static void mainRegistry(){
		registerEntity();
	}
	
	public static void registerEntity(){
		createEntity(EntityMale.class,   "Male",   0xF2FF00, 0x001EFF);
		createEntity(EntityFemale.class, "Female", 0xF2FF00, 0xFF00B3);
	}
	
	public static void createEntity(Class entityClass, String entityName, int solidColor, int spotColor){
		int randomId = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
		EntityRegistry.registerModEntity(entityClass, entityName, randomId, MainRegistry.modInstance, 64, 1, true);
		
		createEgg(randomId, solidColor, spotColor);
	}

	private static void createEgg(int randomId, int solidColor, int spotColor) {
		EntityList.entityEggs.put(Integer.valueOf(randomId),new EntityList.EntityEggInfo(randomId, solidColor, spotColor));
		
	}
	
	
}
