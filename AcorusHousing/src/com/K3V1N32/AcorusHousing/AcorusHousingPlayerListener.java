package com.K3V1N32.AcorusHousing;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;

import java.util.Timer;

import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import com.K3V1N32.AcorusHousing.CommandExecutor;
/**
 * Handle events for all Player related events
 * @author K3V1N32
 */
public class AcorusHousingPlayerListener extends PlayerListener {
	private final AcorusHousing plugin;
	public WorldGuardPlugin wPlugin;
	public Timer time;
	public boolean isCreatingHouse = false;
	public boolean isBuyingHouse = false;
	public boolean isUpdating = false;
	public String houseName;
	public List<String> owners;
	DefaultDomain lister;
    HouseConfig hConfig;

    public AcorusHousingPlayerListener(AcorusHousing instance, HouseConfig config) {
    	plugin = instance;
    }
    public void closeDoor(Block doorBlock) {
    	Door door = (Door)doorBlock.getState().getData();
    	if (!door.isOpen()) {
    		byte closedData = (byte)(door.getData() + 4);
    		doorBlock.setData(closedData);
    		if (door.isTopHalf())
    			closeDoor(doorBlock.getFace(BlockFace.DOWN));
    		else
    			closeDoor(doorBlock.getFace(BlockFace.UP));
    	}
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {
		// Lets create the users house log for admins to know who owns which door :D.
    	hConfig = new HouseConfig();
		hConfig.addPlayer(event.getPlayer().getName());
	}
    
    public void onPlayerInteract(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	if(event.getClickedBlock() == null) {
    		return;
    	} else {
    		hConfig = new HouseConfig();
    		isBuyingHouse = hConfig.isBuying(player.getName());
    		isCreatingHouse = hConfig.isCreating(player.getName());
    		isUpdating = hConfig.isUpdating(player.getName());
    	}
    	if(event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
    		hConfig = new HouseConfig();
    		Block door = event.getClickedBlock();
    		Player player1 = event.getPlayer();
    		String player3 = event.getPlayer().getName();
    		if(hConfig.doorExists(door)) {
    			String house = hConfig.getDoorHouse(door);
    			if(hConfig.isOwner(house, player3) || plugin.permissionHandler.has(player1, "acorus.housing.admin") || player1.isOp()) {
    				
    			} else {
    				player1.sendMessage("§4Access Denied");
    				closeDoor(door);
    			}
    		} else if(hConfig.doorExists(door.getRelative(0, 1, 0))) {
    			String house = hConfig.getDoorHouse(door.getRelative(0, 1, 0));
    			if(hConfig.isOwner(house, player3)  || plugin.permissionHandler.has(player1, "acorus.housing.admin") || player1.isOp()) {
    				
    			} else {
    				player1.sendMessage("§4Access Denied");
    				closeDoor(door);
    			}
    		} else if(hConfig.doorExists(door.getRelative(0, -1, 0))) {
    			String house = hConfig.getDoorHouse(door.getRelative(0, -1, 0));
    			if(hConfig.isOwner(house, player3)  || plugin.permissionHandler.has(player1, "acorus.housing.admin") || player1.isOp()) {
    				
    			} else {
    				player1.sendMessage("§4Access Denied");
    				closeDoor(door);
    			}
    		}
    	}
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
    		//§
    		hConfig = new HouseConfig();
    		if(isCreatingHouse) { 
    			if(!hConfig.doorExists(event.getClickedBlock())) {
    				if(hConfig.addHouse(houseName, event.getClickedBlock())) {
    					hConfig.setCreating(player.getName(), false);
    					isCreatingHouse = false;
    					event.getPlayer().sendMessage("§5Door at: X:" + event.getClickedBlock().getX() + "§5 Y:" + event.getClickedBlock().getY() + "§5 Z:" + event.getClickedBlock().getZ() + ": Has been Registered");
    				} else {
    					event.getPlayer().sendMessage("The House " + houseName + " has already been registered before!");
    					hConfig.setCreating(player.getName(), false);
    					isCreatingHouse = false;
    				}
    			} else {
    				event.getPlayer().sendMessage("That door Already belongs to a house >_>");
    				hConfig.setCreating(player.getName(), false);
    				isCreatingHouse = false;
    			}
    		}
    	}
    	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN))) {
    		hConfig = new HouseConfig();
    		WorldGuardPlugin wPlugin = plugin.getWorldGuard();
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(0).equalsIgnoreCase("[houseinfo]") && plugin.permissionHandler.has(event.getPlayer(), "acorus.housing.admin")) {
    		    	houseName = sign.getLine(1);
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    	} else {
    		    		sign.setLine(0, "");
    		    		sign.setLine(1, "§5NO HOUSE");
    		    		sign.setLine(2, "§5found :(");
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    		owners = null;
    		    	}
    		    	if(!(owners == null)) {
    		    		if(hConfig.houseExists(houseName) && owners.isEmpty()) {
    		    			int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    			sign.setLine(0, houseName);
    		    			sign.setLine(1, "[forsale]");
    		    			sign.setLine(2, "§5" + "$" + price);
    		    			sign.setLine(3, "/house buy");
    		    			sign.update();
    		    		} else if(!hConfig.houseExists(houseName)){
    		    			sign.setLine(0, "");
    		    			sign.setLine(1, "§5NO HOUSE");
    		    			sign.setLine(2, "§5found :(");
    		    			sign.setLine(3, "");
    		    			sign.update();
    		    		} else if(!owners.isEmpty()) {
    		    			sign.setLine(0, houseName);
    		    			sign.setLine(1, "Owner:");
    		    			sign.setLine(2, "§5" + owners.get(0));
    		    			sign.setLine(3, "");
    		    			sign.update();
    		    		}
    		    	} else {
    		    		
    		    	}
    		    } else if(sign.getLine(1).equalsIgnoreCase("[forsale]")) {
    		    	houseName = sign.getLine(0);
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    	} else return;
    		    	//update :P
    		    	if(hConfig.houseExists(houseName) && isUpdating && owners.isEmpty()) {
    		    		int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "[forsale]");
    		    		sign.setLine(2, "§5" + "$" + price);
    		    		sign.setLine(3, "/house buy");
    		    		sign.update();
    		    		isUpdating = false;
    		    		hConfig.setUpdating(player.getName(), false);
    		    	} else if((!owners.isEmpty()) && isUpdating) {
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "Owner:");
    		    		sign.setLine(2, "§5" + owners.get(0));
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    		isUpdating = false;
    		    		hConfig.setUpdating(player.getName(), false);
    		    	} else if(isUpdating) {
    		    		event.getPlayer().sendMessage("...and your done, or not");
    		    		hConfig.setUpdating(player.getName(), false);
    		    		isUpdating = false;
    		    	} 
    		    }
    		}
    	}
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN))) {
    		hConfig = new HouseConfig();
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(1).equalsIgnoreCase("[forsale]") && isBuyingHouse && plugin.permissionHandler.has(event.getPlayer(), "acorus.housing.buy")) {
    		    	houseName = sign.getLine(0);
    		    	String player1 = event.getPlayer().getName();
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    		int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    		if(owners.isEmpty() && (iConomy.getAccount(player1).getHoldings().balance() >= price)) {
    		    			lister = new DefaultDomain();
    		    			lister.addPlayer(player1);
    		    			wPlugin = plugin.getWorldGuard();
    		    			wPlugin.getRegionManager(event.getPlayer().getWorld()).getRegion(houseName).setOwners(lister);
    		    			iConomy.getAccount(event.getPlayer().getName()).getHoldings().add(-(price));
    		    			event.getPlayer().sendMessage("You bought the house at: " + houseName + " for $" + price);
    		    			hConfig.addDoorOwner(player1, houseName);
    		    			owners = hConfig.getDoorOwners(houseName);
    		    			sign.setLine(0, houseName);
        		    		sign.setLine(1, "Owner:");
        		    		sign.setLine(2, "§5" + owners.get(0));
        		    		sign.setLine(3, "");
        		    		sign.update();
        		    		isBuyingHouse = false;
        		    		hConfig.setBuying(player.getName(), false);
    		    		} else if(!owners.isEmpty()) {
    		    			event.getPlayer().sendMessage("Someone already owns that house please alert and admin of this sign!");
    		    			isBuyingHouse = false;
    		    			hConfig.setBuying(player.getName(), false);
    		    		} else if(owners.isEmpty() && !(iConomy.getAccount(event.getPlayer().getName()).getHoldings().balance() >= price)) {
    		    			event.getPlayer().sendMessage("You dont have enough money to buy this house!");
    		    			isBuyingHouse = false;
    		    			hConfig.setBuying(player.getName(), false);
    		    		} else {
    		    			event.getPlayer().sendMessage("Error!!!!");
    		    			isBuyingHouse = false;
    		    			hConfig.setBuying(player.getName(), false);
    		    		}
    		    	}
    		    } else if(isBuyingHouse && sign.getLine(1).equalsIgnoreCase("[forsale]") && !plugin.permissionHandler.has(event.getPlayer(), "acorus.housing.buy")) {
    		    	event.getPlayer().sendMessage("AccessDenied");
    		    }
    		}
    	} else
    	if(isCreatingHouse) {
    		event.getPlayer().sendMessage("Stoped Creating House!");
    		isCreatingHouse = false;
    		hConfig.setCreating(player.getName(), false);
    	} else if(isUpdating) {
    		event.getPlayer().sendMessage("Stoped Updating Sign!");
    		isUpdating = false;
    		hConfig.setUpdating(player.getName(), false);
    	} else if(isBuyingHouse) {
    		event.getPlayer().sendMessage("Stoped Buying House!");
    		isBuyingHouse = false;
    		hConfig.setBuying(player.getName(), false);
    	}
    }

}