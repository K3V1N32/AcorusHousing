package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.config.Configuration;

public class HouseConfig {
	public Configuration houseConfig;
	public List<String> owners;
	public List<String> doors;
	public List<String> houses;
	public List<String> list;
	public String price;
	public String configDir = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	//Yes, i know this is the largest mess of crap ever, but it works XD :P
	public HouseConfig() {
	}
	
	public void createConfig () {
		houseConfig = new Configuration(new File(configDir + File.separator + "Aconfig.yml"));
		File finder = new File(configDir + File.separator + "Aconfig.yml");
		if(finder.exists()) {
			return;
		} else {
			houseConfig.setProperty("initPrice", "1000");
			houseConfig.save();
		}
	}
	
	public String loadConfig() {
		houseConfig = new Configuration(new File(configDir + File.separator + "Aconfig.yml"));
		File finder = new File(configDir + File.separator + "Aconfig.yml");
		if(finder.exists()) {
			houseConfig.load();
			String pp = houseConfig.getString("initPrice");
			return pp;
		} else {
			return "1000";
		}
	}
	
	//lol
	public String saveLocation(Location oldLocation) {
		return "x" + oldLocation.getBlockX() + "y" + oldLocation.getBlockY() + "z" + oldLocation.getBlockZ() + "".replace(".", "_").replace("-", "N");
	}
	
	//add a player file so that the plugin knows if a player is valid or not :P
	public void addPlayer(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.setProperty("isBuying", false);
		houseConfig.setProperty("isCreating", false);
		houseConfig.setProperty("isUpdating", false);
		houseConfig.save();
	}
	
	public boolean isUpdating(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		return houseConfig.getBoolean("isUpdating", false);
	}
	
	public boolean isCreating(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		return houseConfig.getBoolean("isCreating", false);
	}
	
	public boolean isBuying(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		return houseConfig.getBoolean("isBuying", false);
	}
	
	public void setBuying(String player, boolean isLolz) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		houseConfig.setProperty("isBuying", isLolz);
		houseConfig.save();
	}
	public void setCreating(String player, boolean isLolz) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		houseConfig.setProperty("isCreating", isLolz);
		houseConfig.save();
	}
	public void setUpdating(String player, boolean isLolz) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.load();
		houseConfig.setProperty("isUpdating", isLolz);
		houseConfig.save();
	}
	//returns true if it added the house, and false if the house already exists
	public boolean addHouse(String house, Block door) {
		String ppp = loadConfig();
		owners = null;
		String doorLoc = saveLocation(door.getLocation());
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(!houseFile.exists()) {
			houseConfig.setProperty("houseName", house);
			houseConfig.setProperty("price", ppp);
			houseConfig.setProperty("owners", owners);
			houseConfig.setProperty("doorLoc", doorLoc);
			houseConfig.save();
			addDoor(house, door);
			addDoorToList(door);
			return true;
		}else {
			return false;
		}
	}
	
	//
	public boolean addDoor(String house, Block door) {
		String doorLoc = saveLocation(door.getLocation());
		File doorFile = new File(configDir + "doors" + File.separator + doorLoc + ".yml");
		houseConfig = new Configuration(new File(configDir + "doors" + File.separator + doorLoc + ".yml"));
		if(!doorFile.exists()) {
			houseConfig.setProperty("house", house);
			houseConfig.save();
			return true;
		} else {
			return true;
		}
	}
	//
	public String getDoorHouse(Block door) {
		String doorLoc = saveLocation(door.getLocation());
		File houseFile = new File(configDir + "doors" + File.separator + doorLoc + ".yml");
		houseConfig = new Configuration(new File(configDir + "doors" + File.separator + doorLoc + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			String house = houseConfig.getString("house");
			return house;
		} else {
			return null;
		}
	}
	
	//get a houses door location!
	public String getDoorLoc(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			String doorLoc = houseConfig.getString("doorLoc");
			return doorLoc;
		} else {
			return null;
		}
	}
	
	//adds a door to the list of doors!
	public void addDoorToList(Block door) {
		File doorFile = new File(configDir + "lists" + File.separator + "doorList.yml");
		houseConfig = new Configuration(new File(configDir + "lists" + File.separator + "doorList.yml"));
		doors = null;
		String fileName = saveLocation(door.getLocation());
		houseConfig.load();
		doors = houseConfig.getStringList("doors", doors);
		doors.add(fileName);
		houseConfig.setProperty("doors", doors);
		houseConfig.save();
	}
	
	//true if exists
	public boolean doorExists(Block door) {
		File doorFile = new File(configDir + "lists" + File.separator + "doorList.yml");
		houseConfig = new Configuration(new File(configDir + "lists" + File.separator + "doorList.yml"));
		doors = null;
		String doorLoc = saveLocation(door.getLocation());
		if(doorFile.exists()) {
			houseConfig.load();
			doors = houseConfig.getStringList("doors", doors);
			if(doors.contains(doorLoc)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//removes a door from the list of doors, if the door isnt in there it retuns false!
	public boolean remDoorFromList(String doorLoc) {
		File doorFile = new File(configDir + "lists" + File.separator + "doorList.yml");
		houseConfig = new Configuration(new File(configDir + "lists" + File.separator + "doorList.yml"));
		doors = null;
		if(doorFile.exists()) {
			houseConfig.load();
			doors = houseConfig.getStringList("doors", doors);
			if(doors.contains(doorLoc)) {
				doors.remove(doorLoc);
			} else {
				return false;
			}
			houseConfig.setProperty("doors", doors);
			houseConfig.save();
			return true;
		} else {
			return false;
		}
	}
	
	//get a list of all the owners of a house!
	public List<String> getDoorOwners(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			owners = houseConfig.getStringList("owners", null);
			return owners;
		}
		return null;
	}
	
	//get a door price >:$
	public String getDoorPrice(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			String price = houseConfig.getString("price");
			return price;
		}
		return null;
	}
	
	//set a houses price :$
	public boolean setDoorPrice(String house, String price) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			String houseName = houseConfig.getString("houseName");
			owners = houseConfig.getStringList("owners", null);
			String doorLoc = houseConfig.getString("doorLoc");
			houseConfig.setProperty("houseName", houseName);
			houseConfig.setProperty("doorLoc", doorLoc);
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
			houseConfig.load();
			String houseName = houseConfig.getString("houseName");
			String price = houseConfig.getString("price");
			String doorLoc = houseConfig.getString("doorLoc");
			houseConfig.setProperty("houseName", houseName);
			houseConfig.setProperty("price", price);
			houseConfig.setProperty("doorLoc", doorLoc);
			owners = houseConfig.getStringList("owners", null);
			owners.add(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		} else {
			return false;
		}
	}
	
	//remove a house owner >:D
	public boolean remDoorOwner(String player, String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			String doorLoc = houseConfig.getString("doorLoc");
			String price = houseConfig.getString("price");
			houseConfig.setProperty("doorLoc", doorLoc);
			houseConfig.setProperty("price", price);
			owners = houseConfig.getStringList("owners", null);
			owners.remove(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	//checks to see if the player is the MAIN house owner
	public boolean isDoorOwner(String house, String player) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			owners = houseConfig.getStringList("owners", null);
			if(owners.get(0).contains(player)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//true if someone is an owner
	public boolean isOwner(String house, String player) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			houseConfig.load();
			owners = houseConfig.getStringList("owners", null);
			if(owners.contains(player)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	//remove a house
	public boolean remDoor(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			String doorLoc = houseConfig.getString("doorLoc");
			houseFile.delete();
			return true;
		}
		return false;
	}
	
	//
	public void remDoorFile(String door) {
		File houseFile = new File(configDir + "doors" + File.separator + door + ".yml");
		houseFile.delete();
	}
	
	//false if player dosent exist!
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
