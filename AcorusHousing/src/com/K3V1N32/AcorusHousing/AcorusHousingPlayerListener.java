package com.K3V1N32.AcorusHousing;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Handle events for all Player related events
 * @author K3V1N32
 */
public class AcorusHousingPlayerListener extends PlayerListener {
    private final AcorusHousing plugin;

    public AcorusHousingPlayerListener(AcorusHousing instance) {
        plugin = instance;
    }
}
