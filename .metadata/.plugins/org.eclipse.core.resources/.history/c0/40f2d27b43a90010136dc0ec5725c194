package com.K3V1N32.AcorusHousing;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * AcorusHousing for Bukkit
 *
 * @author K3V1N32
 */
public class AcorusHousing extends JavaPlugin {
    private final AcorusHousingPlayerListener playerListener = new AcorusHousingPlayerListener(this);
    private final AcorusHousingBlockListener blockListener = new AcorusHousingBlockListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    // NOTE: There should be no need to define a constructor any more for more info on moving from
    // the old constructor see:
    // http://forums.bukkit.org/threads/too-long-constructor.5032/

    public void onDisable() {
        // TODO: Place any custom disable code here

        // NOTE: All registered events are automatically unregistered when a plugin is disabled

        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("Goodbye world!");
    }

    public void onEnable() {
        // TODO: Place any custom enable code here including the registration of any events
    	
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
