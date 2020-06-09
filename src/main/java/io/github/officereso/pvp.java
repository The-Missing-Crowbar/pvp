package io.github.officereso;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.MenuFunctionListener;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();
    private HashMap<Integer, Kit> kits = configToKit("kits.");
    private HashMap<Integer, Kit> potions = configToKit("potions.");
    private HashMap<Integer, Kit> armor = configToKit("armor.");
    private HashMap<Integer, Kit> additional = configToKit("additional.");

    @Override
    public void onEnable() {
        Menu signMenu = ChestMenu.builder(6).title("PVP Selection Menu").build();

        for (Map.Entry<Integer, Kit> entryKit : kits.entrySet()) {
            Kit kit = entryKit.getValue();
            Slot slot = signMenu.getSlot(kit.getViewPosition());
            slot.setClickHandler((player, info) -> {

            });
        }

        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);

        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    private HashMap<Integer, Kit> configToKit(String root) {
        HashMap<Integer, Kit> kits = new HashMap<Integer, Kit>();

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

            if (root.equals("kits.") || root.equals("additional.")) {
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
                kits.put(kitClass.getViewPosition(), kitClass);
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
                kits.put(kitClass.getViewPosition(), kitClass);
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
                kits.put(kitClass.getViewPosition(), kitClass);
            }
        }
        return kits;
    }
}
