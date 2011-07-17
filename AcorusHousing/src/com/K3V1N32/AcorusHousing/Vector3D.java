package com.K3V1N32.AcorusHousing;

import org.bukkit.block.Block;

public class Vector3D {
	public int x;
	public int y;
	public int z;
	//lol at me making my own vector class and prolly failing hard at it XD
	public Vector3D() {
		x = 0;
		y = 0;
		z = 0;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public void setX(int x1) {
		x = x1;
	}
	public void setY(int y1) {
		y = y1;
	}
	public void setZ(int z1) {
		z = z1;
	}
}
