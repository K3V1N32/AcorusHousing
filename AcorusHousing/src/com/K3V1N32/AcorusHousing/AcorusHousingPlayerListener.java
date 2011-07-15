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

import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import com.iConomy.iConomy;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

import com.K3V1N32.AcorusHousing.CommandExecutor;
/**
 * Handle events for all Player related events
 * @author K3V1N32
 */
public class AcorusHousingPlayerListener extends PlayerListener {
	private final AcorusHousing plugin;
	public boolean isCreatingHouse = false;
	public String houseName;
	public Plugin myPlugin;
	public List<String> owners;
    HouseConfig hConfig;

    public AcorusHousingPlayerListener(AcorusHousing instance, HouseConfig config) {
    	plugin = instance;
    	this.myPlugin = plugin;
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {
		// Lets create the users house log for admins to know who placed which door :D.
    	hConfig = new HouseConfig();
		hConfig.addPlayer(event.getPlayer().getName());
	}
    
    public void onPlayerInteract(PlayerInteractEvent event) {
    	WorldEditPlugin worldEdit = plugin.getWorldEdit();
    	
    	Selection cuboidSelected = worldEdit.getSelection(event.getPlayer());
    	if(cuboidSelected != null) {
    		//Not using this just yet ;D
    		//event.getPlayer().sendMessage("h:" + cuboidSelected.getHeight() + " w:" + cuboidSelected.getWidth() + " l:" + cuboidSelected.getLength());
    		//event.getPlayer().sendMessage("min vector:" + cuboidSelected.getMinimumPoint().toVector().toString() + " max vector:" + cuboidSelected.getMaximumPoint().toVector().toString());
    	}
    	
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.WOODEN_DOOR)) {
    		//§
    		hConfig = new HouseConfig();
    		if(isCreatingHouse) {
    			isCreatingHouse = false;
    			
    			if(hConfig.addHouse(houseName, event.getClickedBlock())) {
    				event.getPlayer().sendMessage("§5Door at: X:" + event.getClickedBlock().getX() + "§5 Y:" + event.getClickedBlock().getY() + "§5 Z:" + event.getClickedBlock().getZ() + ": Has been Registered");
    			} else {
    				event.getPlayer().sendMessage("The Region " + houseName + " has already been registered!");
    			}
    		}
    	}
    	if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN))) {
    		hConfig = new HouseConfig();
    		if(event.getPlayer().isOp()) {
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(0).equalsIgnoreCase("[houseinfo]")) {
    		    	houseName = sign.getLine(1);
    		    	if(hConfig.houseExists(houseName)) {
    		    		int price = hConfig.getDoorPrice(houseName);
    		    		sign.setLine(0, houseName);
    		    		sign.setLine(1, "[forsale]");
    		    		sign.setLine(2, "$" + price);
    		    		sign.setLine(3, "Click To Buy!");
    		    		sign.update();
    		    	} else {
    		    		sign.setLine(0, "");
    		    		sign.setLine(1, "§5NO HOUSE LOL");
    		    		sign.setLine(2, "");
    		    		sign.setLine(3, "");
    		    		sign.update();
    		    	}
    		    } else if(sign.getLine(1).equalsIgnoreCase("[forsale]")) {
    		    	houseName = sign.getLine(0);
    		    	owners = hConfig.getDoorOwners(houseName);
    		    	if(hConfig.houseExists(houseName)) {
    		    		
    		    	}
	    			sign.setLine(0, houseName);
		    		sign.setLine(1, "Owner:");
		    		sign.setLine(2, owners.get(0));
		    		sign.setLine(3, "");
		    		sign.update();
    		    }
    		}
    		}
    	}
    	
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.SIGN_POST) || event.getClickedBlock().getType().equals(Material.WALL_SIGN))) {
    		hConfig = new HouseConfig();
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(1).equalsIgnoreCase("[forsale]")) {
    		    	houseName = sign.getLine(0);
    		    	int price = hConfig.getDoorPrice(houseName);
    		    	if(hConfig.houseExists(houseName)) {
    		    		owners = hConfig.getDoorOwners(houseName);
    		    		if(owners.isEmpty() && (iConomy.getAccount(event.getPlayer().getName()).getHoldings().balance() >= price)) {
    		    			hConfig.addDoorOwner(event.getPlayer().getName(), houseName);
    		    			event.getPlayer().sendMessage("You Have Successfully bought the house at: " + houseName + "for $" + price);
    		    			owners = hConfig.getDoorOwners(houseName);
    		    			sign.setLine(0, houseName);
        		    		sign.setLine(1, "Owner:");
        		    		sign.setLine(2, owners.get(0));
        		    		sign.setLine(3, "");
        		    		sign.update();
    		    		} else if(!owners.isEmpty()) {
    		    			event.getPlayer().sendMessage("Someone already owns that house please alert and admin of this sign!");
    		    		} else if(owners.isEmpty() && !(iConomy.getAccount(event.getPlayer().getName()).getHoldings().balance() >= price)) {
    		    			event.getPlayer().sendMessage("You dont have enough money to buy this house!");
    		    		} else {
    		    			event.getPlayer().sendMessage("Error!!!!");
    		    		}
    		    	}
    		    }
    		}
    	}
    	
    	if(event.getAction().equals(Action.LEFT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.IRON_DOOR_BLOCK)) {
    		event.getPlayer().sendMessage("Its a door fo sho WTF!");
    		BlockState state = event.getClickedBlock().getState();
    		if (state instanceof Door) {
    		    Door door = (Door)state;
    		    if(door.isOpen()) {
    		    	door.setOpen(false);
    		    } else {
    		    	door.setOpen(true);
    		    }    		    
    		} else {
    			event.getPlayer().sendMessage("Well, its a door but not a Door :U");
    		}
    	}
    	
    }

}