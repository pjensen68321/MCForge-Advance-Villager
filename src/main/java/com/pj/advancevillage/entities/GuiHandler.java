package com.pj.advancevillage.entities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	// returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		List entities = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1));
		while (entities.iterator().hasNext()) {
			Object entityInstance = entities.iterator().next();
			if (entityInstance instanceof EntityAdvanceVillager) {
				return new ContainerAdvVillager(player.inventory, (EntityAdvanceVillager) entityInstance);
			}
		}
		return null;

	}

	// returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

		List entities = world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1));
		while (entities.iterator().hasNext()) {
			Object entityInstance = entities.iterator().next();
			if (entityInstance instanceof EntityAdvanceVillager) {
				return new GuiAdvVillager(player.inventory, (EntityAdvanceVillager) entityInstance);
			}
		}
		return null;

	}
}
