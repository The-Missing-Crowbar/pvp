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

    public HashMap<Integer, Integer> getGreenSlots() {
        HashMap<Integer, Integer> slots = new HashMap<>();
        if (selectedKit != null) {
            slots.put(selectedKit.getViewPosition(), 1);
        }
        if (selectedHelmet != null) {
            slots.put(selectedHelmet.getViewPosition(), 1);
        }
        if (selectedChestplate != null) {
            slots.put(selectedChestplate.getViewPosition(), 1);
        }
        if (selectedLeggings != null) {
            slots.put(selectedLeggings.getViewPosition(), 1);
        }
        if (selectedBoots != null) {
            slots.put(selectedBoots.getViewPosition(), 1);
        }
        if (selectedPotions != null) {
            for (Map.Entry<Kit, Integer> entryKit : selectedPotions.entrySet()) {
                if (entryKit.getValue() == null)
                    continue;
                slots.put(entryKit.getKey().getViewPosition(), entryKit.getValue());
            }
        }
        if (selectedAdditions != null) {
            for (Map.Entry<Kit, Integer> entryKit : selectedAdditions.entrySet()) {
                if (entryKit.getValue() == null)
                    continue;
                slots.put(entryKit.getKey().getViewPosition(), entryKit.getValue());
            }
        }

        return slots;
    }

    public void setSelectedAdditions(HashMap<Kit, Integer> selectedAdditions) {
        this.selectedAdditions = selectedAdditions;
    }

    public void addAdditions(Kit kit, int amount) {
        selectedAdditions.putIfAbsent(kit, 0);
        if (selectedAdditions.get(kit) + amount <= 0) {
            selectedAdditions.put(kit, null);
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
            selectedPotions.put(kit, null);
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
}
