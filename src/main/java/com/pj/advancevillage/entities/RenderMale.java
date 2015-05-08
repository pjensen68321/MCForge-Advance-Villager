package com.pj.advancevillage.entities;

import com.pj.advancevillage.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

//import net.minecraft.client.renderer.entity.Render;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderMale extends RenderLiving {
	
	private static final ResourceLocation maleFarmerTexture = new ResourceLocation(Strings.MOD_ID +  "/textures/entities/male_farmer_texture.png");
	private static final String __OFFID = "CL_00000984";
	
	public RenderMale(ModelBase par1, float par2)
	{
		super(par1,par2);
	}
	
	protected ResourceLocation getEntityTexture(EntityMale entity)
	{
		return maleFarmerTexture;
	}
	
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMale) entity);
	}
	
}
