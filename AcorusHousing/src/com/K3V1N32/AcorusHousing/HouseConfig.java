package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.management.timer.Timer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class HouseConfig {
	public Configuration houseConfig;
	public List<String> owners;
	public String configDir = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	
	public HouseConfig() {
	}
	
	public void addPlayer(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.save();
	}
	
	
	
	public boolean dirExists() {
		File mainDir = new File(configDir);
		File houseDir = new File(configDir + "houses" + File.separator);
		File playerDir = new File(configDir + "players" + File.separator);
		File signsDir = new File(configDir + "signs" + File.separator);
		if(mainDir.exists() && houseDir.exists() && playerDir.exists() && signsDir.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean makeDirs() {
		if(dirExists()) {
			File mainDir = new File(configDir);
			File houseDir = new File(configDir + "houses" + File.separator);
			File playerDir = new File(configDir + "players" + File.separator);
			File signsDir = new File(configDir + "signs" + File.separator);
			try{
				mainDir.mkdirs();
				houseDir.mkdirs();
				playerDir.mkdirs();
				signsDir.mkdirs();
				return true;
			} catch(Exception ex) {
				ex.printStackTrace(System.out);
			}
		}
		return false;
	}
	
	//returns true if it added the house, and false if the house already exists
	public boolean addHouse(String house, Block door) {
		owners = null;
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(!houseFile.exists()) {
			houseConfig.setProperty("houseName", house);
			houseConfig.setProperty("door", door);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}else {
			return false;
		}
	}
	
	public List<String> getDoorOwners(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			return houseConfig.getStringList("owners", owners);
		}
		return null;
	}
	
	public boolean addDoorOwner(String player, String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			owners = houseConfig.getStringList("owners", owners);
			owners.add(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	public boolean remDoorOwner(String player, String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			owners = houseConfig.getStringList("owners", owners);
			owners.remove(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	//false if player dosent exist!
	//THIS is the part thats not working lolwtf
	public boolean playerExists(String player) {
		File playerFile = new File(configDir + "players" + File.separator + player + ".yml");
		if(playerFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean houseExists(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		if(houseFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
