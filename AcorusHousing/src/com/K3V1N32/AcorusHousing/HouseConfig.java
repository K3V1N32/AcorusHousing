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
	public Configuration houseLocations;
	public String configDirectory = "plugins" + File.separator + "AcorusHousing" + File.separator;
	
	
	public HouseConfig() {
		houseLocations = new Configuration(new File(configDirectory + "house.yml"));
		houseLocations.load();
	}
	
	public void addHouse(Player player, Block woodenDoor) {
		
	}
}
