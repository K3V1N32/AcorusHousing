package com.K3V1N32.AcorusHousing;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.WorldEditOperation;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import com.K3V1N32.AcorusHousing.AcorusHousing;
import com.K3V1N32.AcorusHousing.AcorusHousingPlayerListener;
import com.K3V1N32.AcorusHousing.HouseConfig;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {

	public AcorusHousing plugin;
	public AcorusHousingPlayerListener playerListener;
	public HouseConfig hConfig;
	public WorldGuardPlugin wPlugin;
	public WorldEditPlugin wePlugin;
	public WorldEditOperation op1;

	public String houseName;
	public String player;
	public int price;

	public CommandExecutor(AcorusHousingPlayerListener pListener, AcorusHousing aPlugin) {
		playerListener = pListener;
		plugin = aPlugin;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		// these will be the staff house commands
		if (commandLabel.equalsIgnoreCase("house")) {
			hConfig = new HouseConfig();
			Player player = (Player)sender;
			WorldGuardPlugin wPlugin = plugin.getWorldGuard();
			if (args.length >= 1 && args[0].equalsIgnoreCase("buy")) {
				if(plugin.permissionHandler.has(player, "acorus.housing.buy")) {
					playerListener.isBuyingHouse = true;
					sender.sendMessage("Left Click a [forsale] sign to buy or click another block to cancel!");
					return true;
				} else {
					sender.sendMessage("You dont have Permission!");
					return false;
				}				
			} else if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage("/house [reg|buy|help|info|remove|update|givekey|takekey] <vars>");
				sender.sendMessage("AcorusHousing BETA");
				sender.sendMessage("More help soon, promise!!!");
				sender.sendMessage("More info at: http://www.acorusgame.us");
				return true;				
			} else if (args.length == 2 && args[0].equals("info")) {//info
				if(plugin.permissionHandler.has(player, "acorus.housing.info")) {
					if (hConfig.houseExists(args[1])) {
						int price = Integer.parseInt(hConfig.getDoorPrice(args[1]));
						sender.sendMessage("The price of " + args[1] + " is: " + price);
						sender.sendMessage("Owners:");
						sender.sendMessage("" + hConfig.getDoorOwners(args[1]).toString());
						return true;
					} else {
						sender.sendMessage("No House Exists! :O");
						return false;
					}
				} else {
					sender.sendMessage("You dont have permission!");
					return false;
				}				
			} else if (args.length == 2 && args[0].equals("reg")) {//only acorus.housing.admin will access this
				if(plugin.permissionHandler.has(player, "acorus.housing.admin")) {
					houseName = args[1];
					boolean isLegal = wPlugin.getRegionManager((player).getWorld()).hasRegion(houseName);
					if (!hConfig.houseExists(houseName)) {
						if (isLegal) {
							playerListener.isCreatingHouse = true;
							playerListener.houseName = houseName;
							sender.sendMessage("Creating Apartment at: " + args[1] + ". Left click door to register.");
							return true;
						} else {
							sender.sendMessage("POUR INSTANT PUBERTY SALSA ON THE REGION! :V");
							return false;
						}
					} else {
						sender.sendMessage("That house already has its desktop wallpaper boosted! >:U");
						return false;
					}
				} else {
					sender.sendMessage("You dont have Permissions");
					return false;
				}
			} else if (args.length == 3 && args[0].equals("forsale")) {//admin
				if(plugin.permissionHandler.has(player, "acorus.housing.admin")) {
					if (hConfig.houseExists(args[1])) {
						hConfig.setDoorPrice(args[1], args[2]);
						sender.sendMessage("Set price for " + args[1] + " to $" + args[2]);
						return true;
					} else {
						sender.sendMessage("That house already has its desktop wallpaper boosted! >:U");
						return false;
					}
				} else {
					sender.sendMessage("You dont have Permissions!");
					return false;
				}	
			} else if (args.length == 2	&& args[0].equalsIgnoreCase("remove")) {//admin
				if(plugin.permissionHandler.has(player, "acorus.housing.admin")) {
					if (hConfig.houseExists(args[1])) {
						if (hConfig.remDoor(args[1])) {
							sender.sendMessage("Succesfully Deleted house at: "	+ args[1]);
							return true;
						} else {
							sender.sendMessage("Error While Boosting Wallpaper!");
							return false;
						}
					} else {
						sender.sendMessage("House dosent exist!!!!");
						return false;
					}
				} else {
					sender.sendMessage("Access Denied");
					return false;
				}				
			} else if (args.length == 1	&& args[0].equalsIgnoreCase("update")) {//admin
				if(plugin.permissionHandler.has(player, "acorus.housing.admin")) {
					playerListener.isUpdating = true;
					sender.sendMessage("Right Click sign to update");
					return true;
				} else {
					sender.sendMessage("Access Denied");
					return false;
				}
			} else if(args.length == 3 && args[0].equalsIgnoreCase("givekey")) {//admin
				if((plugin.permissionHandler.has(player, "acorus.housing.admin") || hConfig.isDoorOwner(args[0], (player).getName())) && hConfig.playerExists(args[1])) {
					if(hConfig.addDoorOwner(args[1], args[0])) {
						sender.sendMessage("Succesfully gave " + args[1] + " a key!");
						if(plugin.getServer().getPlayer(args[1]).isOnline()) {
							plugin.getServer().getPlayer(args[1]).sendMessage("you have recived a key from " + (player).getName());
							plugin.getServer().getPlayer(args[1]).sendMessage("to the house: " + args[0]);
						} return false;
					} else {
						sender.sendMessage("Error Adding Player!");
						return false;
					}
				} else {
					sender.sendMessage("Access Denied");
					return false;
				}
			} else if(args.length == 3 && args[0].equalsIgnoreCase("takekey")) {//admin
				if((plugin.permissionHandler.has(player, "acorus.housing.admin") || hConfig.isDoorOwner(args[0], (player).getName())) && hConfig.playerExists(args[1]) && hConfig.isOwner(args[0], args[1])) {
					if(hConfig.remDoorOwner(args[1], args[0])) {
						sender.sendMessage("");
						if(plugin.getServer().getPlayer(args[1]).isOnline()) {
							plugin.getServer().getPlayer(args[1]).sendMessage("" + (player).getName() + " has taken your key");
							plugin.getServer().getPlayer(args[1]).sendMessage("to the house: " + args[0]);
						} return true;
					} else {
						sender.sendMessage("Error Removing Player!");
					}
				} else {
					sender.sendMessage("Access Denied");
					return false;
				}
			}
		} else {
			return false;
		}
		return false;
	}

}