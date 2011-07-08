package com.K3V1N32.AcorusHousing;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.iConomy.*;
import java.util.logging.Logger;

import com.K3V1N32.AcorusHousing.CommandExecutor;
/**
 * AcorusHousing for Bukkit
 *
 * @author K3V1N32
 */
public class AcorusHousing extends JavaPlugin {
	public iConomy iConomy = null;
	Logger log = Logger.getLogger("Minecraft");	
    private final AcorusHousingPlayerListener playerListener = new AcorusHousingPlayerListener(this);
    private final AcorusHousingBlockListener blockListener = new AcorusHousingBlockListener(this);

    // NOTE: There should be no need to define a constructor any more for more info on moving from
    // the old constructor see:
    // http://forums.bukkit.org/threads/too-long-constructor.5032/
    
    public void onDisable() {
        // TODO: Place any custom disable code here
    	
        // NOTE: All registered events are automatically unregistered when a plugin is disabled    	
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        System.out.println("TTYL");
    }
    
    public void onEnable() {
    	PluginManager pm = getServer().getPluginManager();
        // TODO: Place any custom enable code here including the registration of any event
    	pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Type.PLUGIN_ENABLE, new Server(this), Priority.Monitor, this);
        getServer().getPluginManager().registerEvent(Type.PLUGIN_DISABLE, new Server(this), Priority.Monitor, this);
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is Enhancing Your Housing!" );
    }
}
