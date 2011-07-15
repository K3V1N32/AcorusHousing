package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.util.config.Configuration;

public class HouseConfig {
	public Configuration houseConfig;
	public List<String> owners;
	public String configDir = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	//Yes, i know this is the largest mess of crap ever, but it works XD :P
	public HouseConfig() {
	}
	
	//add a player file so that the plugin knows if a player is valid or not :P
	public void addPlayer(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.save();
	}
	
	//returns true if it added the house, and false if the house already exists
	//adds a house!! YAY
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
	
	//get a list of all the owners of a house!
	public List<String> getDoorOwners(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			return houseConfig.getStringList("owners", owners);
		}
		return null;
	}
	
	//get a door price >:$
	public int getDoorPrice(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			int price = houseConfig.getInt("price", 0);
			return price;
		}
		return 31337;
	}
	
	//set a houses price :$
	public boolean setDoorPrice(String house, int price) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			String houseName = houseConfig.getString("houseName");
			Block door = (Block)houseConfig.getProperty("door");
			owners = houseConfig.getStringList("owners", owners);
			houseConfig.setProperty("houseName", houseName);
			houseConfig.setProperty("door", door);
			houseConfig.setProperty("price", price);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	//add a door owner :D
	public boolean addDoorOwner(String player, String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			String houseName = houseConfig.getString("houseName");
			Block door = (Block)houseConfig.getProperty("door");
			int price = houseConfig.getInt("price", 0);
			houseConfig.setProperty("houseName", houseName);
			houseConfig.setProperty("door", door);
			houseConfig.setProperty("price", price);
			owners = houseConfig.getStringList("owners", owners);
			owners.add(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	//remove a house owner >:D
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
	//remove a house (yea i know i'm using 'door' XD
	public boolean remDoor(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseFile.delete();
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
