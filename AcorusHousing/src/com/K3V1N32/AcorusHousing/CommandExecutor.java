package com.K3V1N32.AcorusHousing;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import com.K3V1N32.AcorusHousing.AcorusHousing;
import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousing plugin;
	public AcorusHousingPlayerListener playerListener;
	
	public String houseName;
	public Player playerName;
	
	public CommandExecutor(AcorusHousingPlayerListener pListener) {
		playerListener = pListener;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(commandLabel.equalsIgnoreCase("houseregA")) {
			if((args.length == 0) && sender.isOp())
			{
				sender.sendMessage("You need to specify a name!");
				return false;
			}else
			if(sender.isOp() && args.length == 2) {
				houseName = args[0];
				playerName = sender.getServer().getPlayer(args[1]);				
				playerListener.isCreatingHouse = true;
				sender.sendMessage("Apartment: " + args[0] + ". " + "Player:" + args[1] + ". Left click door to register.");
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
				}
			else
				//lolol
				if(commandLabel.equalsIgnoreCase("givemeOP")) {
					sender.getServer().broadcastMessage("§5" + ((Player)sender).getName() + " was kicked for begging");
					((Player)sender).kickPlayer("Dont Try To Hack lol");
				}
			else
				//RAGE!!!!!!!!!
				if(commandLabel.equalsIgnoreCase("ragequit")) {
					sender.getServer().broadcastMessage("§5" + ((Player)sender).getName() + " ragequit");
					((Player)sender).kickPlayer("Have a nice day");
				}
			else
				if(commandLabel.equalsIgnoreCase("selecta")) {
					WorldEditPlugin worldEdit = plugin.getWorldEdit();
			    	Selection cuboidSelected = worldEdit.getSelection(((Player)sender));
					if(playerListener.isSelectingCuboid && cuboidSelected != null) {
						//main cuboid addition method here
						sender.sendMessage("Succesfully added the cuboid to apartment:");
					}
				}
			else
				if(commandLabel.equalsIgnoreCase("testget")) {
					sender.sendMessage((sender.getServer().getPlayer(args[0]).toString()));
				}
		return false;
	}

}