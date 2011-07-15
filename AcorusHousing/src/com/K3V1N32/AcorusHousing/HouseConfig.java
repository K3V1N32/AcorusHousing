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
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.save();
	}
	
	public boolean makeDirs() {
		//gonna do this eventually... damn this procrastination XD
		return false;
	}
	
	//returns true if it added the house, and false if it didnt 
	public boolean addHouse(String house) {
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseConfig.equals(null)) {
			houseConfig.setProperty("houseName", house);
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
	
	//false if player dosent exist!
	//THIS is the part thats not working lolwtf
	public boolean playerExists(String player) {
		File file = new File(configDir + File.separator + "players" + File.separator + player + ".yml");
		boolean exists = false;
		try{
			exists = file.exists();
		} catch(Exception ex) {
			ex.printStackTrace(System.out);
		}
		return exists;
	}
}
