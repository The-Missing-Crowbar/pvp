package io.github.officereso;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Kit {
    private final String name;
    private final List<InventoryItem> inventoryItemList;
    private int cost;
    private final int viewPosition;
    private final List<String> lore;
    private final Type type;


    /**
     * @param name              Kit name
     * @param inventoryItemList List of itemStacks that will be in the kit.
     * @param cost              The amount in XP that the kit object costs.
     * @param viewPosition      Where the kit will show up in the kit selection inventory.
     *                          Must be between 0 and 26.
     */
    public Kit(String name, List<InventoryItem> inventoryItemList, int cost, int viewPosition, List<String> lore, Type type) {
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.inventoryItemList = inventoryItemList;
        this.cost = cost;
        this.viewPosition = viewPosition;
        List<String> _lore = new ArrayList<>();
        if (lore == null) {
            this.lore = null;
        } else {
            for (String item : lore) {
                _lore.add(ChatColor.translateAlternateColorCodes('&', item));
            }
            this.lore = _lore;
        }
        this.type = type;
    }

    public enum Type {
        KIT,
        POTION,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        ADDITIONAL
    }

    public String getName() {
        return name;
    }

    public List<InventoryItem> getInventoryItemList() {
        return inventoryItemList;
    }

    public int getCost() {
        return cost;
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public List<String> getLore() {
        return lore;
    }

    public Type getType() {
        return type;
    }
}
