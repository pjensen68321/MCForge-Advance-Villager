package com.pj.advancevillage.pathfinders;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ConnectedBlockSearcher {

	public static List<Vec3> findConnectedBlocks(World world, Vec3 start, Block type) {
		List<Vec3> cells = new ArrayList<Vec3>();
		cells.add(start);

		for (int i = 0; i < cells.size(); i++) {
			List<Vec3> neighbors = getNeighborCells(world, cells.get(i), type);
			for (Vec3 cell : neighbors) {
				if (!listContains(cells, cell)) {
					cells.add(cell);
				}
			}
		}

		return cells;
	}

	private static boolean listContains(List<Vec3> list, Vec3 item) {
		String itemString = item.toString();
		for (Vec3 p : list) {
			if (itemString.equals(p.toString()))
				return true;
		}
		return false;

	}

	private static List<Vec3> getNeighborCells(World world, Vec3 center, Block type) {
		List<Vec3> cells = new ArrayList<Vec3>();

		for (int x = (int) center.xCoord - 1; x <= center.xCoord + 1; x++) {
			for (int y = (int) center.yCoord - 1; y <= center.yCoord + 1; y++) {
				for (int z = (int) center.zCoord - 1; z <= center.zCoord + 1; z++) {
					if (world.getBlock(x, y, z) == type) {
						cells.add(Vec3.createVectorHelper(x, y, z));
					}
				}
			}
		}

		return cells;
	}

}
