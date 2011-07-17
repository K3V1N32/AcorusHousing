package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.util.config.Configuration;

public class HouseConfig {
	public Configuration houseConfig;
	public List<String> owners;
	public List<String> houses;
	public List<String> list;
	public String price;
	public String configDir = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	//Yes, i know this is the largest mess of crap ever, but it works XD :P
	public HouseConfig() {
	}
	
	//add a player file so that the plugin knows if a player is valid or not :P
	public void addPlayer(String player) {
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		houseConfig.save();
	}
	
	public boolean addHouseToPlayer(String player, String house) {
		File playerFile = new File(configDir + "players" + File.separator + player + ".yml");
		houseConfig = new Configuration(new File(configDir + "players" + File.separator + player + ".yml"));
		if(playerFile.exists()) {
			houseConfig.load();
			houses = houseConfig.getStringList("owns", houses);
			houses.add(house);
			houseConfig.setProperty("owns", houses);
			houseConfig.save();
			return true;
		} else {
			return false;
		}
	}
	
	//returns true if it added the house, and false if the house already exists
	//adds a house!! YAY
	public boolean addHouse(String house) {
		owners = null;
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(!houseFile.exists()) {
			houseConfig.setProperty("houseName", house);
			houseConfig.setProperty("price", "1000");
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}else {
			return false;
		}
	}
	
	//add a door
	//if a door already exists, then it returns false ;P
	public boolean addDoor(String house, Block door) {
		File houseFile = new File(configDir + "doors" + File.separator + house + "door.yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(!houseFile.exists()) {
			houseConfig.setProperty("name", house);
			houseConfig.setProperty("x", door.getX());
			houseConfig.setProperty("y", door.getY());
			houseConfig.setProperty("z", door.getZ());
			houseConfig.save();
			return true;
		} else {
			return false;
		}
	}
	
	//does a door exist? you get the picture...
	public boolean doorExists(String house) {
		File houseFile = new File(configDir + "doors" + File.separator + house + "door.yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	//get a door Vector! gets the x,y,and z of the door according to which house it belongs to
	public Vector3D getDoorVec(String house) {
		File houseFile = new File(configDir + "doors" + File.separator + house + "door.yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		Vector3D doorVec = new Vector3D();
		if(houseFile.exists()) {
			int x = 0;
			int y = 0;
			int z = 0;
			houseConfig.load();
			houseConfig.getInt("x", x);
			houseConfig.getInt("y", y);
			houseConfig.getInt("z", z);
			doorVec.setX(x);
			doorVec.setY(y);
			doorVec.setZ(z);
			return doorVec;
		} else {
			doorVec = null;
			return doorVec;
		}
	}
	
	//get a list of all the owners of a house!
	public List<String> getDoorOwners(String house) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			return houseConfig.getStringList("owners", owners);
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
			String houseName = houseConfig.getString("houseName");
			owners = houseConfig.getStringList("owners", owners);
			houseConfig.setProperty("houseName", houseName);
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
			houseConfig.setProperty("houseName", houseName);
			houseConfig.setProperty("price", price);
			owners = houseConfig.getStringList("owners", owners);
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
			owners = houseConfig.getStringList("owners", owners);
			owners.remove(player);
			houseConfig.setProperty("owners", owners);
			houseConfig.save();
			return true;
		}
		return false;
	}
	
	//checks to see if the player is the houses owner
	public boolean isDoorOwner(String house, String player) {
		File houseFile = new File(configDir + "houses" + File.separator + house + ".yml");
		houseConfig = new Configuration(new File(configDir + "houses" + File.separator + house + ".yml"));
		if(houseFile.exists()) {
			owners = houseConfig.getStringList("owners", owners);
			if(owners.get(0).contains(player)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
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
