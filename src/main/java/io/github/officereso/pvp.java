package io.github.officereso;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
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
    private HashMap<Integer, Kit> kits;
    private HashMap<Integer, Kit> defaultKits = new HashMap<>();
    private HashMap<Player, Menu> signMenus = new HashMap<>();
    private HashMap<Player, PlayerSelectionWrapper> selectedKits = new HashMap<>();
    private List<io.github.officereso.Map> maps;
    private io.github.officereso.Map enabledMap;
    Location signBlock;
    private boolean giveSteak;

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
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getClickedBlock().getLocation().equals(signBlock)) {
            Menu menu;
            Player player = event.getPlayer();
            if (signMenus.get(player) == null) {
                signMenus.put(player, ChestMenu.builder(6).title("PVP Selection Menu").build());
                menu = signMenus.get(player);

                PlayerSelectionWrapper selectionWrapper;
                if (selectedKits.get(player) == null) {                     // Gets the selectionWrapper
                    selectionWrapper = new PlayerSelectionWrapper(player);  // for the current player.
                    selectedKits.put(player, selectionWrapper);             // Makes one if doesnt exist.
                } else {
                    selectionWrapper = selectedKits.get(player);
                }

                for (Map.Entry<Integer, Kit> entryKit : kits.entrySet()) {
                    Kit kit = entryKit.getValue();
                    int slotPos = entryKit.getKey();
                    Slot slot = menu.getSlot(slotPos);
                    ItemStack itemStack = kit.getInventoryItemList().get(0).getItemStack();
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(kit.getName());
                    itemMeta.setLore(kit.getLore());
                    itemStack.setItemMeta(itemMeta);
                    slot.setItem(kit.getInventoryItemList().get(0).getItemStack());
                    slotFunction(slot, kit, selectionWrapper, menu);
                }
                Slot slot = menu.getSlot(53);
                ItemStack itemStack = new ItemStack(Material.COMPARATOR);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2Go!"));
                itemStack.setItemMeta(itemMeta);
                slot.setItem(itemStack);
                slot.setClickHandler((ignore, info) -> {
                    selectionWrapper.fillInventory();
                    if (giveSteak) {
                        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
                    }
                    for (Map.Entry<Integer, Kit> entryKit : defaultKits.entrySet()) {
                        for (InventoryItem item : entryKit.getValue().getInventoryItemList()) {
                            switch (entryKit.getValue().getType()) {
                                case HELMET:
                                    player.getInventory().setHelmet(item.getItemStack());
                                    continue;

                                case CHESTPLATE:
                                    player.getInventory().setChestplate(item.getItemStack());
                                    continue;

                                case LEGGINGS:
                                    player.getInventory().setLeggings(item.getItemStack());
                                    continue;

                                case BOOTS:
                                    player.getInventory().setBoots(item.getItemStack());
                                    continue;
                            }
                            if (item.getInvPosition() == null) {
                                player.getInventory().addItem(item.itemStack);
                                continue;
                            }
                            player.getInventory().setItem(item.getInvPosition(), item.getItemStack());
                        }
                    }
                    player.setLevel(player.getLevel() - selectionWrapper.getTotalXpCost());
                    selectionWrapper.clean();
                    player.teleport(enabledMap.getSpawnLocation());
                });
            } else {
                menu = signMenus.get(player);
            }
            menu.open(player);
        }
    }

    public void slotFunction(Slot slot, Kit kit, PlayerSelectionWrapper selectionWrapper, Menu menu) {
        slot.setClickHandler((player, info) -> {
            Kit.Type type = kit.getType();
            int playerLevel = player.getLevel();
            switch (type) {

                case KIT:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.setSelectedKit(kit);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.setSelectedKit(null);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case HELMET:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.setSelectedHelmet(kit);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.setSelectedHelmet(null);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case CHESTPLATE:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.setSelectedChestplate(kit);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.setSelectedChestplate(null);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case LEGGINGS:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.setSelectedLeggings(kit);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.setSelectedLeggings(null);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case BOOTS:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.setSelectedBoots(kit);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.setSelectedBoots(null);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case POTION:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.addPotions(kit, 1);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.addPotions(kit, -1);
                            updateMenu(selectionWrapper, menu);
                            break;
                    }
                case ADDITIONAL:
                    switch (info.getClickType()) {
                        case LEFT:
                            if (playerLevel >= (kit.getCost() + selectionWrapper.getTotalXpCost())) {
                                selectionWrapper.addAdditions(kit, 1);
                                updateMenu(selectionWrapper, menu);
                            } else player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1f, 1f);
                            break;
                        case RIGHT:
                            selectionWrapper.addAdditions(kit, -1);
                            updateMenu(selectionWrapper, menu);

                            break;
                    }
            }
        });
    }

    private void updateMenu(PlayerSelectionWrapper selectionWrapper, Menu menu) {
        for (Map.Entry<Integer, Kit> entryKit : kits.entrySet()) {
            Kit updateKit = entryKit.getValue();
            int slotPos = entryKit.getKey();
            Slot updateSlot = menu.getSlot(slotPos);
            if (selectionWrapper.getSelectedSlots().get(slotPos) != null) {
                updateSlot.setItem(new ItemStack(Material.GREEN_WOOL, selectionWrapper.getSelectedSlots().get(slotPos)));
            } else {
                updateSlot.setItem(updateKit.getInventoryItemList().get(0).getItemStack());
            }
        }
    }

    public void load() {
        String[] signPos = config.getString("sign_pos").split(" ");
        World world = Bukkit.getWorld(signPos[0]);
        signBlock = new Location(world, Integer.parseInt(signPos[1]), Integer.parseInt(signPos[2]), Integer.parseInt(signPos[3]));
        kits = configToKit();
        maps = configToMaps();
        enabledMap = maps.get(0); // TODO: 6/21/2020
        giveSteak = config.getBoolean("giveSteak");
    }

    private List<io.github.officereso.Map> configToMaps() {
        List<io.github.officereso.Map> maps = new ArrayList<>();
        for (String map : config.getConfigurationSection("maps").getKeys(false)) {
            maps.add(new io.github.officereso.Map((List<String>) config.getList("maps." + map + ".spawns"), config.getString("maps." + map + ".name")));
        }
        return maps;
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
                List<String> lore = (List<String>) config.getList(root + kit + ".lore");

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
                    if (kit.equals("default")) {
                        defaultKits.put(kitClass.getViewPosition(), kitClass);
                        continue;
                    }
                    kits.put(kitClass.getViewPosition(), kitClass);
                }
            }
        }
        return kits;
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            if (event.getEntity().getWorld() == getServer().getWorld("spawn")) {
                int level = event.getEntity().getLevel();
                event.getEntity().getKiller().setLevel(event.getEntity().getKiller().getLevel() + 2);
                event.getEntity().getInventory().clear();
                event.getEntity().setLevel(level);
                signMenus.remove(event.getEntity());
            }
        }
    }
}
