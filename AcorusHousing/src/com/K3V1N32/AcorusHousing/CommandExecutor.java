package com.K3V1N32.AcorusHousing;

import java.io.File;
import java.util.List;

import org.bukkit.Achievement;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.getspout.spoutapi.gui.Button;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.sk89q.worldedit.WorldEditOperation;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
	public SpoutPlayer spoutplayer;

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
		if (commandLabel.equalsIgnoreCase("house")) {
			
			hConfig = new HouseConfig();
			Player player = (Player)sender;
			WorldGuardPlugin wPlugin = plugin.getWorldGuard();
			WorldEditPlugin ePlugin = plugin.getWorldEdit();
			if(args.length == 0) {
				player.sendMessage("Please include a sub command!(see: /house help)");
				return false;
			}
			//Buy Command
			if (args.length >= 1 && args[0].equalsIgnoreCase("buy")) {//buy
				if(plugin.permissionHandler.has(player, "acorus.housing.buy") || player.isOp()) {
					hConfig.setBuying(player.getName(), true);
					sender.sendMessage("Left Click a [forsale] sign to buy or click another block to cancel!");
					return true;
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}				
			//Help Command
			} else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {//none
				sender.sendMessage("/house <reg|buy|setprice|help|info|remove|update|givekey|takekey> [vars]");
				sender.sendMessage("AcorusHousing BETA");
				sender.sendMessage("More help soon");
				sender.sendMessage("More info at: http://www.acorusgame.us");
				return true;
			//Info Command
			} else if (args[0].equals("info")) {//info
				if(plugin.permissionHandler.has(player, "acorus.housing.info") || player.isOp()) {
					if(args.length != 2) {
						player.sendMessage("/house info [house name]");
						return false;
					}
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
					sender.sendMessage("§4Access Denied");
					return false;
				}
			//House Registration Command
			} else if (args[0].equals("reg")) {//admin
				if(args.length != 2) {
					player.sendMessage("/house reg [House Name]");
					return false;
				}
				if(plugin.permissionHandler.has(player, "acorus.housing.admin") || player.isOp()) {
					houseName = args[1];
					boolean isLegal = wPlugin.getRegionManager((player).getWorld()).hasRegion(houseName);
					if (!hConfig.houseExists(houseName)) {
						if (isLegal) {
							//playerListener.isCreatingHouse = true;
							hConfig.setCreating(player.getName(), true);
							playerListener.houseName = houseName;
							sender.sendMessage("Creating House at: " + args[1] + ". Left click door to register.");
							return true;
						} else {
							sender.sendMessage("NOPE");
							return false;
						}
					} else {
						sender.sendMessage("That house already exists! Delete it or dont overwrite it");
						return false;
					}
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}
			//Put a house forsale
			} else if (args[0].equals("setprice")) {//admin
				if(args.length != 3) {
					player.sendMessage("/house setprice [House Name] [Price]");
					return false;
				}
				if(plugin.permissionHandler.has(player, "acorus.housing.admin") || player.isOp()) {
					if (hConfig.houseExists(args[1])) {
						hConfig.setDoorPrice(args[1], args[2]);
						sender.sendMessage("Set price for " + args[1] + " to $" + args[2]);
						return true;
					} else {
						sender.sendMessage("That dosent exist! >:U");
						return false;
					}
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}	
			//Remove a house
			} else if (args[0].equalsIgnoreCase("remove")) {//admin
				if(args.length != 2) {
					player.sendMessage("/house remove [House Name]");
					return false;
				}
				if(plugin.permissionHandler.has(player, "acorus.housing.admin") || player.isOp()) {
					if (hConfig.houseExists(args[1])) {
						String doorLoc = hConfig.getDoorLoc(args[1]);
						if (hConfig.remDoorFromList(doorLoc)) {
							hConfig.remDoorFile(doorLoc);
							hConfig.remDoor(args[1]);
							//wPlugin.getRegionManager(player.getWorld()).removeRegion(args[1]);
							sender.sendMessage("Succesfully Deleted house at: "	+ args[1]);
							return true;
						} else {
							sender.sendMessage("Error removing file");
							return false;
						}
					} else {
						sender.sendMessage("House dosent exist!");
						return false;
					}
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}
			//Update a sign
			} else if (args[0].equalsIgnoreCase("update")) {//admin
				if(args.length != 1) {
					player.sendMessage("/house update");
					return false;
				}
				if(plugin.permissionHandler.has(player, "acorus.housing.admin") || player.isOp()) {
					//playerListener.isUpdating = true;
					hConfig.setUpdating(player.getName(), true);
					sender.sendMessage("Right Click sign to update");
					return true;
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}
			//Give someone a key (also a backup command for giving someone a house if there is no sign for that house kindof :P)
			} else if(args[0].equalsIgnoreCase("givekey")) {//admin
				if(args.length != 3) {
					player.sendMessage("/house givekey [House Name] [Player Name]");
					return false;
				}
				if((plugin.permissionHandler.has(player, "acorus.housing.admin") || hConfig.isDoorOwner(args[1], player.getName()) || player.isOp()) && hConfig.playerExists(args[2]) && !(hConfig.isOwner(args[1], args[2]))) {
					if(hConfig.addDoorOwner(args[2], args[1])) {
						DefaultDomain owners = wPlugin.getRegionManager(player.getWorld()).getRegion(args[1]).getOwners();
						owners.addPlayer(args[2]);
						wPlugin.getRegionManager(player.getWorld()).getRegion(args[1]).setOwners(owners);
						sender.sendMessage("Succesfully gave " + args[2] + " a key!");
						return false;
					} else {
						sender.sendMessage("Error Adding Player!");
						return false;
					}
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}
			//takes away someones ownership of a house
			} else if(args[0].equalsIgnoreCase("takekey")) {//admin
				if(args.length != 3) {
					player.sendMessage("/house takekey [House Name] [Player Name]");
					return false;
				}
				if((plugin.permissionHandler.has(player, "acorus.housing.admin") || hConfig.isDoorOwner(args[0], (player).getName()) || player.isOp()) && hConfig.playerExists(args[2]) && (hConfig.isOwner(args[1], args[2]))) {
					if(hConfig.remDoorOwner(args[2], args[1])) {
						DefaultDomain owners = wPlugin.getRegionManager(player.getWorld()).getRegion(args[1]).getOwners();
						owners.removePlayer(args[2]);
						wPlugin.getRegionManager(player.getWorld()).getRegion(args[1]).setOwners(owners);
						sender.sendMessage("Succesfully took key from " + args[2]);
						return true;
					} else {
						sender.sendMessage("Error Removing Player!");
						return false;
					}
				} else {
					sender.sendMessage("§4Access Denied");
					return false;
				}
			} else if(args.length == 1 && args[0].equalsIgnoreCase("spout")) {
				spoutplayer = (SpoutPlayer)player;
				if(spoutplayer.isSpoutCraftEnabled()) {
					spoutplayer.setTexturePack("http://dl.dropbox.com/u/22923847/BukkitPlugins/moderncraftalpha5mowed.zip");
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		return false;
	}

}