package io.github.officereso;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
