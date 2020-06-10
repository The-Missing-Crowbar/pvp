package io.github.officereso;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.MenuFunctionListener;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pvp extends JavaPlugin implements Listener {
    private final FileConfiguration config = this.getConfig();
    private HashMap<Integer, Kit> kits = configToKit();
    private HashMap<Player, Menu> signMenus = new HashMap<>();
    private HashMap<Player, PlayerSelectionWrapper> selectedKits = new HashMap<>();
    Block signBlock;


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        load();
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {

        Menu menu;
        Player player = event.getPlayer();
        if (signMenus.get(player) == null) {
            signMenus.put(player, ChestMenu.builder(6).title("PVP Selection Menu").build());
            menu = signMenus.get(player);

            PlayerSelectionWrapper selectionWrapper;
            if (selectedKits.get(player) == null) {                      // Gets the selectionWrapper
                selectionWrapper = new PlayerSelectionWrapper(player);  // for the current player.
                selectedKits.put(player, selectionWrapper);             // Makes one if doesnt exist.
            } else {
                selectionWrapper = selectedKits.get(player);
            }

            for (Map.Entry<Integer, Kit> entryKit : kits.entrySet()) {
                Kit kit = entryKit.getValue();
                int slotPos = entryKit.getKey();
                Slot slot = menu.getSlot(slotPos);
                slot.setItem(kit.getInventoryItemList().get(0).getItemStack());
                slotFunction(slot, kit, selectionWrapper, menu);
            }
        } else {
            menu = signMenus.get(player);
        }
        menu.open(player);
    }

    public void slotFunction(Slot slot, Kit kit, PlayerSelectionWrapper selectionWrapper, Menu menu) {
        slot.setClickHandler((player, info) -> {
            if (kit.getType() == Kit.Type.KIT) {
                if (selectionWrapper.getSelectedKit() != kit) {
                    for (Map.Entry<Integer, Kit> entryKit : kits.entrySet()) {
                        Kit updateKit = entryKit.getValue();
                        int slotPos = entryKit.getKey();
                        Slot updateSlot = menu.getSlot(slotPos);
                        selectionWrapper.setSelectedKit(kit);
                        if (selectionWrapper.getGreenSlots().get(slotPos) != null) {
                            updateSlot.setItem(new ItemStack(Material.GREEN_WOOL));
                        } else {
                            updateSlot.setItem(updateKit.getInventoryItemList().get(0).getItemStack());
                        }
                    }
                }
            }
            System.out.println("ldkfsjld");
        });
    }

    public void load() {
        World world = Bukkit.getWorld("world");
        String[] signPos = config.getString("sign_pos").split(" ");
        signBlock = world.getBlockAt(Integer.parseInt(signPos[0]), Integer.parseInt(signPos[1]), Integer.parseInt(signPos[2]));
        configToKit();
    }

    private HashMap<Integer, Kit> configToKit() {
        HashMap<Integer, Kit> kits = new HashMap<>();
        for (String root : config.getConfigurationSection("").getKeys(false)) {
            if (root.equals("maps") || root.equals("sign_pos") || root.equals("giveSteak")) {
                continue;
            }
            root = root + '.';

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

                List<InventoryItem> inventoryItemList = new ArrayList<>();

                if (root.equals("kits.") || root.equals("additional.")) {
                    for (String item : config.getConfigurationSection(root + kit + ".items").getKeys(false)) {
                        Integer inventoryPos = (Integer) config.get(root + kit + ".items." + item + ".inventoryPos");

                        ItemStack itemStack = new ItemStack(
                                Material.valueOf(config.getString(root + kit + ".items." + item + ".material")), // Material located in config
                                (config.getInt(root + kit + ".items." + item + ".amount"))); // Amount of the item

                        String enchantments = config.getString(root + kit + ".items." + item + ".enchantments");
                        if (enchantments != null) {
                            List<String[]> list = new ArrayList<>();
                            for (String enchantment : enchantments.split(", ")) {
                                list.add(enchantment.split(" "));
                            }

                            ItemMeta itemMeta = itemStack.getItemMeta();
                            for (String[] enchantmentData : list) {
                                itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentData[0].toLowerCase())), Integer.parseInt(enchantmentData[1]), true);
                                itemStack.setItemMeta(itemMeta);
                            }
                        }
                        inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                    }
                    Kit kitClass;
                    if (root.equals("kits.")) {
                        kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.KIT);
                    } else {
                        kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.ADDITIONAL);
                    }
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
                        itemStack.setItemMeta(potionMeta);

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
                            List<String[]> list = new ArrayList<>();
                            for (String enchantment : enchantments.split(", ")) {
                                list.add(enchantment.split(" "));
                            }

                            ItemMeta itemMeta = itemStack.getItemMeta();
                            for (String[] enchantmentData : list) {
                                itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchantmentData[0].toLowerCase())), Integer.parseInt(enchantmentData[1]), true);
                                itemStack.setItemMeta(itemMeta);
                            }
                        }
                        inventoryItemList.add(new InventoryItem(itemStack, inventoryPos));
                    }
                    Kit kitClass = new Kit(name, inventoryItemList, cost, position, lore, Kit.Type.valueOf(type));
                    kits.put(kitClass.getViewPosition(), kitClass);
                }
            }
        }
        return kits;
    }
}
