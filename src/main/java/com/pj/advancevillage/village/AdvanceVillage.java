package com.pj.advancevillage.village;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

import com.pj.advancevillage.entities.EntityAdvanceVillager;

public class AdvanceVillage {
	String name = ""; // also works as an id
	Vec3 pos; //
	
	List<EntityAdvanceVillager> residents = new ArrayList<EntityAdvanceVillager>();
	
	public AdvanceVillage(String cityName, Vec3 position) {
		name = cityName;
		pos = position;
	}

	public String getVillageName() {
		return name;
	}

	public Vec3 getVillagePosition() {
		return pos;
	}

	public void addRedent(EntityAdvanceVillager entity){
		residents.add(entity);
	}
	
	
	public void readNBTdata(NBTTagCompound nbtCompound) {
		
	}

	public NBTTagCompound getNBTdata(NBTTagCompound nbtCompound) {
		NBTTagCompound cityData = new NBTTagCompound();
		cityData.setString("cityName", getVillageName());
		
		
		return nbtCompound;
	}
}
