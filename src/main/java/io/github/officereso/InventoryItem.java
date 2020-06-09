package io.github.officereso;

import org.bukkit.inventory.ItemStack;

public class InventoryItem {
    public ItemStack itemStack;
    public Integer invPosition;

    public InventoryItem(ItemStack itemStack, Integer invPosition){
        this.itemStack = itemStack;
        this.invPosition = invPosition;
    }

}
