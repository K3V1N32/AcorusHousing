package com.K3V1N32.AcorusHousing;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import java.util.Timer;

import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

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
	public Plugin myPlugin;
	public List<String> owners;
    HouseConfig hConfig;

    public AcorusHousingPlayerListener(AcorusHousing instance, HouseConfig config) {
    	plugin = instance;
    	this.myPlugin = plugin;
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {
		// Lets create the users house log for admins to know who owns which door :D.
    	hConfig = new HouseConfig();
		hConfig.addPlayer(event.getPlayer().getName());
	}
    
    public void onPlayerInteract(PlayerInteractEvent event) { 
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
    		//�
    		hConfig = new HouseConfig();
    		if(isCreatingHouse) {
    			isCreatingHouse = false;
    			if(hConfig.addHouse(houseName)) {
    				event.getPlayer().sendMessage("�5Door at: X:" + event.getClickedBlock().getX() + "�5 Y:" + event.getClickedBlock().getY() + "�5 Z:" + event.getClickedBlock().getZ() + ": Has been Registered");
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
    		    if(sign.getLine(0).equalsIgnoreCase("[houseinfo]") && event.getPlayer().isOp()) {
    		    	houseName = sign.getLine(1);
    		    	if(hConfig.houseExists(houseName)) {
    		    		int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "[forsale]");
    		    		sign.setLine(2, "�5" + "$" + price);
    		    		sign.setLine(3, "/house buy");
    		    		sign.update();
    		    	} else {
    		    		sign.setLine(0, "");
    		    		sign.setLine(1, "�5NO HOUSE");
    		    		sign.setLine(2, "�5found :(");
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    	}
    		    } else if(sign.getLine(1).equalsIgnoreCase("[forsale]")) {
    		    	houseName = sign.getLine(0);
    		    	owners = hConfig.getDoorOwners(houseName);
    		    	//update :P
    		    	if(hConfig.houseExists(houseName) && isUpdating && owners.get(0) == null) {
    		    		int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "[forsale]");
    		    		sign.setLine(2, "�5" + "$" + price);
    		    		sign.setLine(3, "/house buy");
    		    		sign.update();
    		    		isUpdating = false;
    		    	} else if(!owners.isEmpty()) {
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "Owner:");
    		    		sign.setLine(2, owners.get(0));
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    	} else if(isUpdating) {
    		    		event.getPlayer().sendMessage("...and your done, or not");
    		    	} else {
    		    		
    		    	}
    		    }
    		}
    	}
    	
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN))) {
    		hConfig = new HouseConfig();
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(1).equalsIgnoreCase("[forsale]") && isBuyingHouse) {
    		    	houseName = sign.getLine(0);
    		    	int price = Integer.parseInt(hConfig.getDoorPrice(houseName));
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    		if(owners.isEmpty() && (iConomy.getAccount(event.getPlayer().getName()).getHoldings().balance() >= price)) {
    		    			hConfig.addDoorOwner(event.getPlayer().getName(), houseName);
    		    			iConomy.getAccount(event.getPlayer().getName()).getHoldings().add(-(price));
    		    			event.getPlayer().sendMessage("You bought the house at: " + houseName + "for $" + price);
    		    			owners = hConfig.getDoorOwners(houseName);
    		    			sign.setLine(0, houseName);
        		    		sign.setLine(1, "Owner:");
        		    		sign.setLine(2, owners.get(0));
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
    		    }
    		}
    	} 
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