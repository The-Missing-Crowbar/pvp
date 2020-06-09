package io.github.officereso;

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
    private List<InventoryItem> inventoryItemList;
    private int cost;
    private int viewPosition;
    private String lore;
    private Type type;


    /**
     * @param name              Kit name
     * @param inventoryItemList List of itemStacks that will be in the kit.
     * @param cost              The amount in XP that the kit object costs.
     * @param viewPosition      Where the kit will show up in the kit selection inventory.
     *                          Must be between 0 and 26.
     */
    public Kit(String name, List<InventoryItem> inventoryItemList, int cost, int viewPosition, String lore, Type type) {
        this.name = name;
        this.inventoryItemList = inventoryItemList;
        this.cost = cost;
        this.viewPosition = viewPosition;
        this.lore = lore;
        this.type = type;
    }

    public enum Type {
        KIT,
        POTION,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS
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
}
