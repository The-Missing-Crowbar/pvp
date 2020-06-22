package io.github.officereso;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InventoryItem {
    public ItemStack itemStack;
    public Integer invPosition;

    public InventoryItem(ItemStack itemStack, Integer invPosition) {
        this.itemStack = itemStack;
        this.invPosition = invPosition;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @Nullable
    public Integer getInvPosition() {
        return invPosition;
    }
}
