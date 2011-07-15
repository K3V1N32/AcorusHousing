package com.K3V1N32.AcorusHousing;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.Material;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * AcorusHousing listener
 * @author K3V1N32
 */
public class AcorusHousingBlockListener extends BlockListener {
    private final AcorusHousing plugin;

    public AcorusHousingBlockListener(final AcorusHousing plugin) {
        this.plugin = plugin;
    }
    
    public void onBlockPlace(BlockPlaceEvent event) {
    	if(event.getBlock().getType().equals(Material.SIGN)) {
    		BlockState state = event.getBlock().getState();
    		if (state instanceof Sign) {
    		    Sign sign = (Sign)state;
    		    if(sign.getLine(1).equalsIgnoreCase("[houseinfo]")) {
    		    	event.getPlayer().sendMessage("YAY IT WORKED LOL");
    		    }
    		}
    	}
    	
    }
    
}
