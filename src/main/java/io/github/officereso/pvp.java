package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();
    HashMap<String, Kit> kits = configToKit();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    private HashMap<String, Kit> configToKit() {
        HashMap<String, Kit> kits = new HashMap<String, Kit>();

        final @NotNull Set<String> keys = config.getConfigurationSection("kits.sword1.items.1").getKeys(false);

        for (String kit : config.getConfigurationSection("kits").getKeys(false)){
            String name = config.getString("kits."+kit+".name");
            Integer cost = (Integer) config.get("kits."+kit+".cost");
            Integer position = (Integer) config.get("kits."+kit+".position");
            String lore = config.getString("kits."+kit+".lore");

            if (name == null){
                getLogger().severe("Kit " + kit + " has invalid name");
                return null;
            } // Checks for bad names.
            if (cost == null){
                getLogger().severe("Kit " + kit + " has invalid cost");
                return null;
            } // Checks for bad cost.
            if (position == null){
                getLogger().severe("Kit " + kit + " has invalid position");
                return null;
            } // Checks for bad position.

            List<InventoryItem> inventoryItemList = new ArrayList<InventoryItem>();
            for (String item : config.getConfigurationSection("kits."+kit+".items").getKeys(false)) {
                Integer inventoryPos = (Integer) config.get("kits." + kit + ".items." + item + ".inventoryPos");

                ItemStack itemStack = new ItemStack(
                        Material.valueOf(config.getString("kits." + kit + ".items." + item + ".material")), // Material located in config
                        (config.getInt("kits." + kit + ".items." + item + ".amount"))); // Amount of the item

                String enchantments = config.getString("kits." + kit + ".items." + item + ".enchantments");
                if (enchantments == null) {
                    inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                } // If no enchantments create add itemStack to inventoryItemList
                else {
                    List<String[]> list = new ArrayList<String[]>();
                    for (String enchantment : enchantments.split(", ")) {
                        list.add(enchantment.split(" "));
                    }

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    for (String[] enchantmentData : list) {
                        itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentData[0].toLowerCase())), Integer.parseInt(enchantmentData[1]), true);
                    }
                    inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                }
            }
            Kit kitClass = new Kit(name, inventoryItemList, cost, position, lore);
            kits.put(kit, kitClass);
        }

        return kits;
    }
}
