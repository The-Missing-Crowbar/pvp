package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;



public final class pvp extends JavaPlugin implements Listener {
    private ItemStack[] swordMan = {new ItemStack(Material.DIAMOND_SWORD)};

    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        getServer().getPluginManager().registerEvents(this,this);
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent event){
        try {
            if (event.getClickedBlock().getWorld() == getServer().getWorld("plot" )&& event.getClickedBlock().getX() == 0 && event.getClickedBlock().getZ() == 0 && event.getClickedBlock().getY() == 64) {
                event.getPlayer().getInventory().addItem(swordMan);
            }
        }
        catch(NullPointerException ignored){ }

    }
}
