package com.K3V1N32.AcorusHousing;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;

/**
 * Handle events for all Player related events
 * @author K3V1N32
 */
public class AcorusHousingPlayerListener extends PlayerListener {
	private final AcorusHousing plugin;
	public boolean inEditMode = false;
	
    HouseConfig hConfig;

    public AcorusHousingPlayerListener(AcorusHousing instance, HouseConfig config) {
    	plugin = instance;
        hConfig = new HouseConfig();
    }
    
    public void onPlayerJoin(PlayerJoinEvent event) {
		// Lets create the users house log for admins to know who placed which door :D.
		hConfig.addPlayer(event.getPlayer());
	}
    
    public void onPlayerInteract(PlayerInteractEvent event) {
    	WorldEditPlugin worldEdit = plugin.getWorldEdit();
    	
    	Selection cuboidSelected = worldEdit.getSelection(event.getPlayer());
    	if(cuboidSelected != null) {
    		//event.getPlayer().sendMessage("h:" + cuboidSelected.getHeight() + " w:" + cuboidSelected.getWidth() + " l:" + cuboidSelected.getLength());
    		//event.getPlayer().sendMessage("min vector:" + cuboidSelected.getMinimumPoint().toVector().toString() + " max vector:" + cuboidSelected.getMaximumPoint().toVector().toString());
    	}
    }

}
