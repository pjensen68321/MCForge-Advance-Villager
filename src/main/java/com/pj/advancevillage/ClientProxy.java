package com.pj.advancevillage;

import net.minecraft.client.model.ModelBiped;

import com.pj.advancevillage.entities.EntityMale;
import com.pj.advancevillage.entities.RenderMale;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {
	@Override
	public void registerRenderThings() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMale.class, new RenderMale(new ModelBiped(), 0));
	}
}
