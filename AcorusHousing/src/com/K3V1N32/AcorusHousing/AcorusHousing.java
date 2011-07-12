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
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
/**
 * AcorusHousing for Bukkit
 *
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

	// Event Listener
    private final AcorusHousingPlayerListener playerListener = new AcorusHousingPlayerListener(this, config);
    private final AcorusHousingBlockListener blockListener = new AcorusHousingBlockListener(this);
    
    // Command Executor
    CommandExecutor commandExecutor = new CommandExecutor(playerListener);

    
    public void onDisable() {
        System.out.println("[AcorusHousing] We are going bye bye now, TTYL!");
    }
    
    public void onEnable() {
    	// PluginManager = Yummy.
    	PluginManager pm = getServer().getPluginManager();
    	
    	// Let's hook into Permissions, because we love Permissions *sarcasm*
    	setupPermissions();
    	
    	// Event Registration
    	pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Type.PLUGIN_ENABLE, new Server(this), Priority.Monitor, this);
        pm.registerEvent(Type.PLUGIN_DISABLE, new Server(this), Priority.Monitor, this);
        
        // Command Executor Init
        getCommand("houseregA").setExecutor(commandExecutor);
		getCommand("houseregB").setExecutor(commandExecutor);
		getCommand("facepalm").setExecutor(commandExecutor);

        // Heellllooooo CraftBukkit!
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is Enhancing Your Housing!" );
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
    
    
    // WorldEdit - Yummy functions to get cuboid selections to register to doors. Should make life a lot easier.
    public WorldEditPlugin getWorldEdit() {
        Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEdit == null) {
            log.info("[AcorusHousing] WorldEdit doesn't appear to be enabled. ERRRORRRR!");
            getServer().getPluginManager().disablePlugin(this);
        }
        
        if (worldEdit instanceof WorldEditPlugin) {
            return (WorldEditPlugin) worldEdit;
        } else {
            log.info("[AcorusHousing] WorldEdit doesn't appear to be enabled. ERRRORRRR!");
            getServer().getPluginManager().disablePlugin(this);
        }
        
        return null;
    }
}