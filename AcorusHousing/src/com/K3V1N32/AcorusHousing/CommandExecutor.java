package com.K3V1N32.AcorusHousing;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import com.K3V1N32.AcorusHousing.AcorusHousing;
import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;
import com.K3V1N32.AcorusHousing.HouseConfig;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousing plugin;
	public AcorusHousingPlayerListener playerListener;
	public HouseConfig hConfig;
	
	public String houseName;
	public String player;
	public int price;
	
	public CommandExecutor(AcorusHousingPlayerListener pListener) {
		playerListener = pListener;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		//these will be the staff house commands
		if(commandLabel.equalsIgnoreCase("house")) {
			hConfig = new HouseConfig();
			if(sender.isOp()) {
			if(args.length == 2 && args[0].equals("info")) {
				if(hConfig.houseExists(args[1])) {
					int price = hConfig.getDoorPrice(args[1]);
					sender.sendMessage("The price of that house is: " + price);
					return true;
				} else {
					sender.sendMessage("No House Exists! :O");
				}
			} else
			if(args.length == 2 && args[0].equals("reg")) {
				houseName = args[1];
				if(!hConfig.houseExists(houseName)) {
					playerListener.isCreatingHouse = true;
					playerListener.houseName = houseName;
					sender.sendMessage("Creating Apartment at: " + args[0] + ". Left click door to register.");
					return true;
				} else {
					sender.sendMessage("That House Exists Already! :U");
				}
			} else
			if(args.length == 3 && args[0].equals("forsale")) {
				if(hConfig.houseExists(args[1])) {
					int price = Integer.parseInt(args[2]);
					hConfig.setDoorPrice(args[1], price);
					sender.sendMessage("Set price for " + args[1] + " to " + price);
					return true;
				} else {
					sender.sendMessage("No House Exists! :O");
				}
			}
			}
		}
		else
			//RAGE!!!!!!!!!
			if(commandLabel.equalsIgnoreCase("ragequit")) {
				sender.getServer().broadcastMessage("§5" + ((Player)sender).getName() + " ragequit");
				((Player)sender).kickPlayer("Have a nice day");
				return true;
			}
		return false;
	}

}