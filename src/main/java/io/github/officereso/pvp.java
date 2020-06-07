package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        System.out.println(Material.getMaterial("DIAMOND_SWORD"));

        Kit kit = new Kit("yes", "DIAMOND_SWORD 1, DIAMOND_SHOVEL 1",2,1);
        System.out.println(kit);


        this.saveDefaultConfig();
        List<?> maps = config.getList("maps");

        System.out.println(maps);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
