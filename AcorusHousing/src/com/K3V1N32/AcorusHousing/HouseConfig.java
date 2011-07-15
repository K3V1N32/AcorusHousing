package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.management.timer.Timer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class HouseConfig {
	public Configuration houseConfig;
	public Vector3D doorVec;
	public String configDir = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	
	public HouseConfig() {
	}
	
	public void addPlayer(String player) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + player + ".yml"));
		houseConfig.save();
	}
	
	public boolean makeDirs() {
		//gonna do this eventually... damn this procrastination XD
		return false;
	}
	
	//returns true if it added the house, and false if it didnt 
	public boolean addHouse(String house, String owner, int x, int y, int z, int price) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseConfig.equals(null)) {
			houseConfig.setProperty("houseName", house);
			houseConfig.setProperty("owner", owner);
			houseConfig.setProperty("price", price);
			houseConfig.setProperty("x", x);
			houseConfig.setProperty("y", y);
			houseConfig.setProperty("z", z);
			houseConfig.save();
			return true;
		}else {
			return false;
		}
	}
	
	
	public String getHouseOwner(String name) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + name + ".yml"));
		String out = "null";
		if(houseConfig.equals(null)) {
			return out;
		} else {
			return houseConfig.getString("owner");
		}
	}
	
	public Vector3D getDoorVector(String house) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		int x = houseConfig.getInt("x", 0);
		int y = houseConfig.getInt("y", 0);
		int z = houseConfig.getInt("z", 0);
		doorVec.setX(x);
		doorVec.setY(y);
		doorVec.setZ(z);
		return doorVec;
		
	}
	
	public boolean playerExists(String player) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + player + ".yml"));
		if(houseConfig.equals(null)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void addHouse(Player player, Block woodenDoor) {
		
	}
}
