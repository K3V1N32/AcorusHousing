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
		
		if(commandLabel.equalsIgnoreCase("housereg")) {
			if((args.length <= 0) && sender.isOp())
			{
				sender.sendMessage("Correct usage is: /housereg [RegionName]");
				return false;
			}else
			if(sender.isOp() && args.length == 1) {
				houseName = args[0];
				playerListener.isCreatingHouse = true;
				sender.sendMessage("Creating Apartment at: " + args[0] + ". Left click door to register.");
				return true;
			}
		}
		else
			if(commandLabel.equalsIgnoreCase("houseforsale")) {
				if(args.length >= 1) {
					sender.sendMessage("");
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