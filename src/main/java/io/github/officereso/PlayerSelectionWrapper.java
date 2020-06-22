package io.github.officereso;

import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerSelectionWrapper {
    public final Player player;
    public Kit selectedKit;
    public Kit selectedHelmet;
    public Kit selectedChestplate;
    public Kit selectedLeggings;
    public Kit selectedBoots;
    public HashMap<Kit, Integer> selectedPotions = new HashMap<>();
    public HashMap<Kit, Integer> selectedAdditions = new HashMap<>();

    public PlayerSelectionWrapper(Player player, Kit kit) {
        this.player = player;
        this.selectedKit = kit;
    }

    public PlayerSelectionWrapper(Player player) {
        this.player = player;
    }

    public Kit getSelectedKit() {
        return selectedKit;
    }

    public Player getPlayer() {
        return player;
    }

    public Kit getSelectedHelmet() {
        return selectedHelmet;
    }

    public HashMap<Kit, Integer> getSelectedAdditions() {
        return selectedAdditions;
    }

    public HashMap<Kit, Integer> getSelectedPotions() {
        return selectedPotions;
    }

    public HashMap<Integer, Integer> getSelectedSlots() {
        HashMap<Integer, Integer> slots = new HashMap<>();
        if (selectedKit != null)
            slots.put(selectedKit.getViewPosition(), 1);
        if (selectedHelmet != null)
            slots.put(selectedHelmet.getViewPosition(), 1);
        if (selectedChestplate != null)
            slots.put(selectedChestplate.getViewPosition(), 1);
        if (selectedLeggings != null)
            slots.put(selectedLeggings.getViewPosition(), 1);
        if (selectedBoots != null)
            slots.put(selectedBoots.getViewPosition(), 1);
        for (Map.Entry<Kit, Integer> entryKit : selectedPotions.entrySet()) {
            if (entryKit.getValue() == null)
                continue;
            slots.put(entryKit.getKey().getViewPosition(), entryKit.getValue());
        }
        for (Map.Entry<Kit, Integer> entryKit : selectedAdditions.entrySet()) {
            if (entryKit.getValue() == null)
                continue;
            slots.put(entryKit.getKey().getViewPosition(), entryKit.getValue());
        }

        return slots;
    }

    public void fillInventory() {
        player.getInventory().clear();
        HashMap<Kit, Integer> allKits = new HashMap<>();
        allKits.put(selectedKit, 1);
        allKits.put(selectedHelmet, 1);
        allKits.put(selectedChestplate, 1);
        allKits.put(selectedLeggings, 1);
        allKits.put(selectedBoots, 1);
        for (Map.Entry<Kit, Integer> entryKit : selectedPotions.entrySet()) {
            allKits.put(entryKit.getKey(), entryKit.getValue());
        }
        for (Map.Entry<Kit, Integer> entryKit : selectedAdditions.entrySet()) {
            allKits.put(entryKit.getKey(), entryKit.getValue());
        }

        for (Map.Entry<Kit, Integer> entryKit : allKits.entrySet()) {
            if (entryKit.getKey() == null) {
                continue;
            }
            for (InventoryItem item : entryKit.getKey().getInventoryItemList()) {
                if (entryKit.getKey().getType() == Kit.Type.HELMET) {
                    player.getInventory().setHelmet(item.getItemStack());
                    continue;
                }
                if (entryKit.getKey().getType() == Kit.Type.CHESTPLATE) {
                    player.getInventory().setChestplate(item.getItemStack());
                    continue;
                }
                if (entryKit.getKey().getType() == Kit.Type.LEGGINGS) {
                    player.getInventory().setLeggings(item.getItemStack());
                    continue;
                }
                if (entryKit.getKey().getType() == Kit.Type.BOOTS) {
                    player.getInventory().setBoots(item.getItemStack());
                    continue;
                }
                if (item.getInvPosition() == null) {
                    player.getInventory().addItem(item.getItemStack());
                    continue;
                }
                player.getInventory().setItem(item.getInvPosition(), item.getItemStack());
            }
        }
    }

    public void clean() {
        selectedKit = null;
        selectedHelmet = null;
        selectedChestplate = null;
        selectedLeggings = null;
        selectedBoots = null;
        selectedPotions = new HashMap<>();
        selectedAdditions = new HashMap<>();
    }

    public void setSelectedAdditions(HashMap<Kit, Integer> selectedAdditions) {
        this.selectedAdditions = selectedAdditions;
    }

    public void addAdditions(Kit kit, int amount) {
        selectedAdditions.putIfAbsent(kit, 0);
        if (selectedAdditions.get(kit) + amount <= 0) {
            selectedAdditions.remove(kit);
            return;
        }
        selectedAdditions.put(kit, selectedAdditions.get(kit) + amount);
    }

    public void setSelectedBoots(Kit selectedBoots) {
        this.selectedBoots = selectedBoots;
    }

    public void setSelectedChestplate(Kit selectedChestplate) {
        this.selectedChestplate = selectedChestplate;
    }

    public void setSelectedLeggings(Kit selectedLeggings) {
        this.selectedLeggings = selectedLeggings;
    }

    public void setSelectedPotions(HashMap<Kit, Integer> selectedPotions) {
        this.selectedPotions = selectedPotions;
    }

    public void addPotions(Kit kit, int amount) {
        selectedPotions.putIfAbsent(kit, 0);
        if (selectedPotions.get(kit) + amount <= 0) {
            selectedPotions.remove(kit);
            return;
        }
        selectedPotions.put(kit, selectedPotions.get(kit) + amount);
    }

    public void setSelectedHelmet(Kit selectedHelmet) {
        this.selectedHelmet = selectedHelmet;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;
    }

    public int getTotalXpCost() {
        int cost = 0;
        if (selectedKit != null)
            cost += selectedKit.getCost();
        if (selectedHelmet != null)
            cost += selectedHelmet.getCost();
        if (selectedChestplate != null)
            cost += selectedChestplate.getCost();
        if (selectedLeggings != null)
            cost += selectedLeggings.getCost();
        if (selectedBoots != null)
            cost += selectedBoots.getCost();
        for (Map.Entry<Kit, Integer> entryKit : selectedPotions.entrySet()) {
            cost += entryKit.getKey().getCost() * entryKit.getValue();
        }
        for (Map.Entry<Kit, Integer> entryKit : selectedAdditions.entrySet()) {
            cost += entryKit.getKey().getCost() * entryKit.getValue();
        }
        return cost;
    }
}
