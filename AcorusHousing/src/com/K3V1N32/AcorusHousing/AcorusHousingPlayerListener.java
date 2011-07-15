package com.K3V1N32.AcorusHousing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

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
    	
    }

}