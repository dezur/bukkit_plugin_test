package cica.cicahe;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Cicahe extends JavaPlugin {

    private final PlayerListener playerListener = new PlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        PluginDescriptionFile pdfFile = this.getDescription();
        getLogger().info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    @Override
    public void onDisable() {
        getLogger().info("Test plugin stopped!");
        // Plugin shutdown logic
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
