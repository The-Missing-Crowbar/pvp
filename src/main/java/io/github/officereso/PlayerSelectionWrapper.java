package io.github.officereso;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerSelectionWrapper {
    public final Player player;
    public Kit selectedKit;
    public Kit selectedHelmet;

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

    public HashMap<Integer, Integer> getGreenSlots() {
        HashMap<Integer, Integer> slots = new HashMap<>();
        if (selectedKit != null) {
            slots.put(selectedKit.getViewPosition(), selectedKit.getViewPosition());
        }
        if (selectedHelmet != null) {
            slots.put(selectedHelmet.getViewPosition(), selectedHelmet.getViewPosition());
        }

        return slots;
    }

    public void setSelectedHelmet(Kit selectedHelmet) {
        this.selectedHelmet = selectedHelmet;
    }

    public void setSelectedKit(Kit selectedKit) {
        this.selectedKit = selectedKit;
    }
}
