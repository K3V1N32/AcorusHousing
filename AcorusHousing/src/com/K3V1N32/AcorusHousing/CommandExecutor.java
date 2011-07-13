package com.K3V1N32.AcorusHousing;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousingPlayerListener playerListener;
	public CommandExecutor(AcorusHousingPlayerListener pListener) {
		playerListener = pListener;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(commandLabel.equalsIgnoreCase("houseregA")) {
			if((args.length == 0) && sender.isOp())
			{
				sender.sendMessage("You need to specify a name!");
				return true;
			}else
			if(sender.isOp()) {
				playerListener.isCreatingHouse = true;
				sender.sendMessage("Apartment: " + args[0] + ". Left click door to register.");
				return true;
			}
		}
		else 
			if(commandLabel.equalsIgnoreCase("houseregB")) {
				if((args.length == 0) && sender.isOp())
				{
					sender.sendMessage("You need to specify a name!");
					return true;
				}else
				if(sender.isOp()) {
					sender.sendMessage("House: " + args[0] + ". Left click door to register.");
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
					((Player)sender).kickPlayer("Your account at " + ((Player)sender).getAddress() + "has been logged and sent to Mojang! NOTCH NO-LIKEY HACKS");
				}
			else
				//lolol
				if(commandLabel.equalsIgnoreCase("givemeOP")) {
					sender.getServer().broadcastMessage("�5" + ((Player)sender).getName() + "was kicked for begging");
					((Player)sender).kickPlayer("Dont Try To Hack lol");
				}
			else
				//RAGE!!!!!!!!!
				if(commandLabel.equalsIgnoreCase("ragequit")) {
					sender.getServer().broadcastMessage("�5" + ((Player)sender).getName() + "ragequit");
					((Player)sender).kickPlayer("Have a nice day");
				}
		return false;
	}

}