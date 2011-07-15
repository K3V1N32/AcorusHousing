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
			
			if((args.length <= 2) && sender.isOp())
			{
				sender.sendMessage("Correct usage is: /housereg [AprtName] [PlayerName] [price]");
				return false;
			}else
			if(sender.isOp() && args.length == 3) {
				houseName = args[0];
				player = args[1];
				price = Integer.parseInt(args[2]);
				playerListener.isCreatingHouse = true;
				if(!hConfig.playerExists(player)) {
					sender.sendMessage("That player does not exist!");
				} else {
					sender.sendMessage("Apartment: " + args[0] + ". " + "Player:" + args[1] + ". Left click door to register.");
				}
				return true;
			}
		}
		else 
			//Heheheh :P P.S. These were SJ's ideas
				if(commandLabel.equalsIgnoreCase("facepalm")) {
					if((args.length == 0) && sender.isOp()) {
						sender.getServer().broadcastMessage("***" + ((Player)sender).getName() + " facepalms");
						return true;
					}else if(sender.isOp())
					{
						sender.getServer().broadcastMessage("***" + ((Player)sender).getName() + " facepalms at " + args[0]);
						return true;
					}
				}
			else
				//teehee
				if(commandLabel.equalsIgnoreCase("hacks")) {
					sender.getServer().broadcastMessage(((Player)sender).getName() + "Has Been Promoted for using /hacks!");
					((Player)sender).kickPlayer("Your account at " + ((Player)sender).getAddress() + " has been logged and sent to Mojang! NOTCH NO-LIKEY HACKS");
					return true;
				}
			else
				//lolol
				if(commandLabel.equalsIgnoreCase("givemeOP")) {
					sender.getServer().broadcastMessage("§5" + ((Player)sender).getName() + " was kicked for begging");
					((Player)sender).kickPlayer("Dont Try To Hack lol");
					return true;
				}
			else
				//RAGE!!!!!!!!!
				if(commandLabel.equalsIgnoreCase("ragequit")) {
					sender.getServer().broadcastMessage("§5" + ((Player)sender).getName() + " ragequit");
					((Player)sender).kickPlayer("Have a nice day");
					return true;
				}
			else
				if(commandLabel.equalsIgnoreCase("select")) {
					WorldEditPlugin worldEdit = plugin.getWorldEdit();
			    	Selection cuboidSelected = worldEdit.getSelection(((Player)sender));
					if(playerListener.isSelectingCuboid && cuboidSelected != null) {
						//main cuboid addition method here
						
						sender.sendMessage("Succesfully added the cuboid to apartment:");
					}
					return true;
				}
		return false;
	}

}