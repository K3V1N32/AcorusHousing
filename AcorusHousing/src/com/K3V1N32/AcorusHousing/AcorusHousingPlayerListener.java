package com.K3V1N32.AcorusHousing;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
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
    public void openDoor(Block doorBlock) {
    	Door door = (Door)doorBlock.getState().getData();
    	if (!door.isOpen()) {
    		byte closedData = (byte)(door.getData() + 4);
    		doorBlock.setData(closedData);
    		if (door.isTopHalf())
    			openDoor(doorBlock.getFace(BlockFace.DOWN));
    		else
    			openDoor(doorBlock.getFace(BlockFace.UP));
    	}
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {
		// Lets create the users house log for admins to know who owns which door :D.
    	hConfig = new HouseConfig();
		hConfig.addPlayer(event.getPlayer().getName());
	}
    
    public void onPlayerInteract(PlayerInteractEvent event) {
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
    		//§
    		hConfig = new HouseConfig();
    		
    		if(isCreatingHouse) {
    			isCreatingHouse = false;
    			if(hConfig.addHouse(houseName) && hConfig.addDoor(houseName, event.getClickedBlock())) {
    				event.getPlayer().sendMessage("§5Door at: X:" + event.getClickedBlock().getX() + "§5 Y:" + event.getClickedBlock().getY() + "§5 Z:" + event.getClickedBlock().getZ() + ": Has been Registered");
    			} else {
    				event.getPlayer().sendMessage("The House " + houseName + " has already been registered!");
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
    		    	} else if((!owners.isEmpty()) && isUpdating) {
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "Owner:");
    		    		sign.setLine(2, "§5" + owners.get(0));
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    		isUpdating = false;
    		    	} else if(isUpdating) {
    		    		event.getPlayer().sendMessage("...and your done, or not");
    		    	} else {
    		    		event.getPlayer().sendMessage("Just spread chunky peanutbutter on your internet vent!");
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
    		    	String player = event.getPlayer().getName();
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    		int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    		if(owners.isEmpty() && (iConomy.getAccount(player).getHoldings().balance() >= price)) {
    		    			hConfig.addDoorOwner(player, houseName);
    		    			lister = null;
    		    			lister.addPlayer(player);
    		    			wPlugin.getRegionManager(event.getPlayer().getWorld()).getRegion(houseName).setOwners(lister);
    		    			iConomy.getAccount(event.getPlayer().getName()).getHoldings().add(-(price));
    		    			event.getPlayer().sendMessage("You bought the house at: " + houseName + " for $" + price);
    		    			owners = hConfig.getDoorOwners(houseName);
    		    			sign.setLine(0, houseName);
        		    		sign.setLine(1, "Owner:");
        		    		sign.setLine(2, "§5" + owners.get(0));
        		    		sign.setLine(3, "");
        		    		sign.update();
        		    		isBuyingHouse = false;
    		    		} else if(!owners.isEmpty()) {
    		    			event.getPlayer().sendMessage("Someone already owns that house please alert and admin of this sign!");
    		    			isBuyingHouse = false;
    		    		} else if(owners.isEmpty() && !(iConomy.getAccount(event.getPlayer().getName()).getHoldings().balance() >= price)) {
    		    			event.getPlayer().sendMessage("You dont have enough money to buy this house!");
    		    			isBuyingHouse = false;
    		    		} else {
    		    			event.getPlayer().sendMessage("Error!!!!");
    		    			isBuyingHouse = false;
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
    	} else if(isUpdating) {
    		event.getPlayer().sendMessage("Stoped Updating Sign!");
    		isUpdating = false;
    	} else if(isBuyingHouse) {
    		event.getPlayer().sendMessage("Stoped Buying House!");
    		isBuyingHouse = false;
    	}
    }

}