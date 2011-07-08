package com.K3V1N32.AcorusHousing;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousingPlayerListener playerListener;

	public CommandExecutor(AcorusHousingPlayerListener pListener) {
		playerListener = pListener;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {

		if(commandLabel.equalsIgnoreCase("houseregA")) {
			if(sender.isOp()) {
				sender.sendMessage("Apartment: " + args[0] + ". Left click door to register.");
			}
		}
		else 
			if(commandLabel.equalsIgnoreCase("houseregB")) {
				if(sender.isOp()) {
					sender.sendMessage("House: " + args[0] + ". Left click door to register.");
				}
			}
		return false;
	}

}