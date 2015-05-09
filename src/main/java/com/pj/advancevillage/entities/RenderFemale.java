package com.pj.advancevillage.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.pj.advancevillage.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//import net.minecraft.client.renderer.entity.Render;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFemale extends RenderLiving {
	
	private static final ResourceLocation femaleFarmerTexture = new ResourceLocation(Strings.MOD_ID +  ":textures/entities/female_farmer_texture.png");
	private static final String __OFFID = "CL_00000985";
	
	public RenderFemale(ModelBase par1, float par2)
	{
		super(par1,par2);
	}
	
	protected ResourceLocation getEntityTexture(EntityFemale entity)
	{
		return femaleFarmerTexture;
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityFemale) entity);
	}
	
}
