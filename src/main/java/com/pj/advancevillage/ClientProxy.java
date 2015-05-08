package com.pj.advancevillage;

import net.minecraft.client.model.ModelBiped;

import com.pj.advancevillage.entities.EntityMale;
import com.pj.advancevillage.entities.RenderMale;
import com.pj.advancevillage.entities.models.Modelmale;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
	@Override
	public void registerRenderThings() {
		//RenderingRegistry.registerEntityRenderingHandler(EntityMale.class, new RenderMale(new Modelmale(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityMale.class, new RenderMale(new ModelBiped(), 0));
	}
}
