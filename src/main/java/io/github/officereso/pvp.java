package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();


    @Override
    public void onEnable() {
        HashMap<String, Kit> kits = configToKit("kits.");
        HashMap<String, Kit> potions = configToKit("potions.");
        HashMap<String, Kit> armor = configToKit("armor.");

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    private HashMap<String, Kit> configToKit(String root) {
        HashMap<String, Kit> kits = new HashMap<String, Kit>();

        for (String kit : config.getConfigurationSection(root).getKeys(false)) {
            String name = config.getString(root + kit + ".name");
            Integer cost = (Integer) config.get(root + kit + ".cost");
            Integer position = (Integer) config.get(root + kit + ".position");
            String lore = config.getString(root + kit + ".lore");

            if (name == null) {
                getLogger().severe(root + ' ' + kit + " has invalid name");
                return null;
            } // Checks for bad names.
            if (cost == null) {
                getLogger().severe(root + ' ' + kit + " has invalid cost");
                return null;
            } // Checks for bad cost.
            if (position == null) {
                getLogger().severe(root + ' ' + kit + " has invalid position");
                return null;
            } // Checks for bad position.

            List<InventoryItem> inventoryItemList = new ArrayList<InventoryItem>();

            if (root.equals("kits.")) {
                for (String item : config.getConfigurationSection(root + kit + ".items").getKeys(false)) {
                    Integer inventoryPos = (Integer) config.get(root + kit + ".items." + item + ".inventoryPos");

                    ItemStack itemStack = new ItemStack(
                            Material.valueOf(config.getString(root + kit + ".items." + item + ".material")), // Material located in config
                            (config.getInt(root + kit + ".items." + item + ".amount"))); // Amount of the item

                    String enchantments = config.getString(root + kit + ".items." + item + ".enchantments");
                    if (enchantments != null) {
                        List<String[]> list = new ArrayList<String[]>();
                        for (String enchantment : enchantments.split(", ")) {
                            list.add(enchantment.split(" "));
                        }

                        ItemMeta itemMeta = itemStack.getItemMeta();
                        for (String[] enchantmentData : list) {
                            itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentData[0].toLowerCase())), Integer.parseInt(enchantmentData[1]), true);
                        }
                    }
                    inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                }
                Kit kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.KIT);
                kits.put(kit, kitClass);
            }
            if (root.equals("potions.")) {
                for (String item : config.getConfigurationSection(root + kit + ".items").getKeys(false)) {
                    Integer inventoryPos = (Integer) config.get(root + kit + ".items." + item + ".inventoryPos");

                    ItemStack itemStack = new ItemStack(
                            Material.valueOf(config.getString(root + kit + ".items." + item + ".material")), // Material located in config
                            (config.getInt(root + kit + ".items." + item + ".amount"))); // Amount of the item

                    PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(
                            PotionType.valueOf(config.getString(root + kit + ".items." + item + ".basePotionData")),
                            config.getBoolean(root + kit + ".items." + item + ".extended"),
                            config.getBoolean(root + kit + ".items." + item + ".upgraded")));

                    inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                }
                Kit kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.POTION);
                kits.put(kit, kitClass);
            }
            if (root.equals("armor.")) {
                String type = config.getString(root + kit + ".type");

                for (String item : config.getConfigurationSection(root + kit + ".items").getKeys(false)) {
                    Integer inventoryPos = (Integer) config.get(root + kit + ".items." + item + ".inventoryPos");

                    ItemStack itemStack = new ItemStack(
                            Material.valueOf(config.getString(root + kit + ".items." + item + ".material")), // Material located in config
                            (config.getInt(root + kit + ".items." + item + ".amount"))); // Amount of the item

                    String enchantments = config.getString(root + kit + ".items." + item + ".enchantments");
                    if (enchantments != null) {
                        List<String[]> list = new ArrayList<String[]>();
                        for (String enchantment : enchantments.split(", ")) {
                            list.add(enchantment.split(" "));
                        }

                        ItemMeta itemMeta = itemStack.getItemMeta();
                        for (String[] enchantmentData : list) {
                            itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentData[0].toLowerCase())), Integer.parseInt(enchantmentData[1]), true);
                        }
                    }
                    inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                }
                Kit kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.valueOf(type));
                kits.put(kit, kitClass);
            }
        }
        return kits;
    }
}
