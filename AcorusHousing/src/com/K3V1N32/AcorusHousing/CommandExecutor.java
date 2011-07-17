package com.K3V1N32.AcorusHousing;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import com.K3V1N32.AcorusHousing.AcorusHousing;
import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;
import com.K3V1N32.AcorusHousing.HouseConfig;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousing plugin;
	public AcorusHousingPlayerListener playerListener;
	public HouseConfig hConfig;
	public WorldGuardPlugin wPlugin;
	
	public String houseName;
	public String player;
	public int price;
	
	public CommandExecutor(AcorusHousingPlayerListener pListener, AcorusHousing aPlugin) {
		playerListener = pListener;
		plugin = aPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		//these will be the staff house commands
		if(commandLabel.equalsIgnoreCase("house")) {
			hConfig = new HouseConfig();
			WorldGuardPlugin wPlugin = plugin.getWorldGuard();
			if(args.length == 1 && args[0].equalsIgnoreCase("buy")) {
				playerListener.isBuyingHouse = true;
				sender.sendMessage("Left Click a [forsale] sign to buy!");
			} else if(args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("/house [reg|buy|help|info|remove|update|givekey|takekey] <vars>");
				sender.sendMessage("AcorusHousing BETA");
				sender.sendMessage("More help soon, promise!!!");
				sender.sendMessage("More info at: http://www.acorusgame.us");
				if(args[1] != null) {
					((Player)sender).getInventory().setHelmet(new ItemStack(Integer.parseInt(args[1])));
					return true;
				} else {
					return true;
				}
			} else
			if(sender.isOp()) {
			if(args.length == 2 && args[0].equals("info")) {
				if(hConfig.houseExists(args[1])) {
					int price = Integer.parseInt(hConfig.getDoorPrice(args[1]));
					sender.sendMessage("The price of that house is: " + price);
					return true;
				} else {
					sender.sendMessage("No House Exists! :O");
				}
			} else
			if(args.length == 2 && args[0].equals("reg")) {
				houseName = args[1];
				boolean isLegal = true;
				if(!hConfig.houseExists(houseName)) {
					if(isLegal) {
						playerListener.isCreatingHouse = true;
						playerListener.houseName = houseName;
						sender.sendMessage("Creating Apartment at: " + args[0] + ". Left click door to register.");
						return true;
					} else {
						sender.sendMessage("Please create the region first!");
					}					
				} else {
					sender.sendMessage("That House Exists Already! :U");
				}
			} else
			if(args.length == 3 && args[0].equals("forsale")) {
				if(hConfig.houseExists(args[1])) {
					hConfig.setDoorPrice(args[1], args[2]);
					sender.sendMessage("Set price for " + args[1] + " to " + args[2]);
					return true;
				} else {
					sender.sendMessage("No House Exists! :O");
				}
			} else
			if(args.length == 2 && args[0].equalsIgnoreCase("remove")) {
				if(hConfig.houseExists(args[1])) {
					if(hConfig.remDoor(args[1])) {
						sender.sendMessage("Succesfully Deleted house at: " + args[1]);
					} else {
						sender.sendMessage("Error While Deleting house!");
					}
				} else {
					sender.sendMessage("House dosent exist!!!!");
				}
			} else
			if(args.length == 1 && args[0].equalsIgnoreCase("update")) {
				playerListener.isUpdating = true;
				sender.sendMessage("Right Click sign to update");
			} 
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("givekey")) {
				if(sender.isOp() || hConfig.isDoorOwner(houseName, ((Player)sender).getName())) {
					
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