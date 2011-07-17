package com.K3V1N32.AcorusHousing;

import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.iConomy.iConomy;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;
/**
 * AcorusHousing for Bukkit
 * @author K3V1N32
 */
public class AcorusHousing extends JavaPlugin {	
	//House Config for saving/loading house information on the server
	HouseConfig config;
	
	// iConomy and Permissions
	public iConomy iConomy = null;
	public static PermissionHandler permissionHandler;

	// Logging.
	Logger log = Logger.getLogger("Minecraft");

	// Event Listeners
    private final AcorusHousingPlayerListener playerListener = new AcorusHousingPlayerListener(this, config);
    private final AcorusHousingBlockListener blockListener = new AcorusHousingBlockListener(this);
    
    // Command Executor
    CommandExecutor commandExecutor = new CommandExecutor(playerListener, this);

    
    
    //onDisable
    public void onDisable() {
        log.info("[AcorusHousing] See You Later!");
    }
    
    //onEnable
    public void onEnable() {
    	// PluginManager = Yummy.
    	PluginManager pm = getServer().getPluginManager();
    	// Event Registration
    	pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_ENABLE, new Server(this), Priority.Monitor, this);
        pm.registerEvent(Type.PLUGIN_DISABLE, new Server(this), Priority.Monitor, this);
        
        // Command Executor Init
        getCommand("house").setExecutor(commandExecutor);
		getCommand("ragequit").setExecutor(commandExecutor);
		
		//houseConfig
		config = new HouseConfig();
		
        // Heellllooooo CraftBukkit!
        PluginDescriptionFile pdfFile = this.getDescription();
        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is Enhancing Your Housing!" );
    }
    
    private void setupPermissions() {
        if (permissionHandler != null) {
            return;
        }
        
        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
        
        if (permissionsPlugin == null) {
            log.info("[AcorusHousing] Permission system not detected, defaulting to OP");
            return;
        }
        
        permissionHandler = ((Permissions) permissionsPlugin).getHandler();
        log.info("[AcorusHousing] Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
    }
    //Yay WORLDGUARD LOL P.S. I HATE WORLDGUARD WITH A PASSION >:U
    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");//for some god damn reason, this line gives a null pointer exception even if worldguard is loaded >:[
     
        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }
        return (WorldGuardPlugin) plugin;
    }
    
}