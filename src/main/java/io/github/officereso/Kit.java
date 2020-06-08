package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Kit extends JavaPlugin {
    private final String name;
    private List<ItemStack> itemStackList;
    private int cost;
    private int viewPosition;
    private Integer invPosition;


    /**
     * @param name Kit name
     * @param items List of itemStacks that will be in the kit.
     * @param cost The amount in XP that the kit object costs.
     * @param viewPosition Where the kit will show up in the kit selection inventory.
     *                     Must be between 0 and 26.
     *
     */
    public Kit(String name, List<ItemStack> items, int cost, int viewPosition, Integer invPosition){
        this.name = name;
        this.itemStackList = items;
        this.cost = cost;
        this.viewPosition = viewPosition;
        this.invPosition = invPosition;
    }
//
//
//    /**
//     * This constructor takes a string and parses it into
//     * a list of ItemStacks.
//     *
//     * @param name Kit name
//     * @param items String from config formated like
//     *              "DIAMOND_SWORD 1, DIAMOND_SHOVEL 1"
//     * @param cost The amount in XP that the kit object costs.
//     * @param viewPosition Where the kit will show up in the kit selection inventory.
//     *                     Must be between 0 and 26.
//     */
//    @Deprecated
//    public Kit(String name, String items, int cost, int viewPosition){
//        this.name = name;
//        List<String[]> list = new ArrayList<String[]>();
//        try {
//            for (String item : items.split(", ")) {
//                list.add(item.split(" "));
//            }
//
//            for (String[] item : list){
//                itemStackList.add(new ItemStack(Material.getMaterial(item[0]), Integer.parseInt(item[1])));
//                if (!(item[2].equals("NONE"))){ // If the enchantment is not set to none
//
//                }
//            }
//
//            this.cost = cost;
//            this.viewPosition = viewPosition;
//        }
//        catch(NullPointerException e){
//            getLogger().severe("Malformed config for kit "+name+". \n" +
//                    "Items should be ITEM_NAME #ammount, ITEM_NAME #ammount");
//        }
//    }
//
//    public Kit(String name, int cost, int viewPosition){
//        this.name = name;
//        this.cost = cost;
//        this.viewPosition = viewPosition;
//
//
//    }
//
//
//
//    public void addKitToInventory(@NotNull PlayerInventory playerInventory){
//        for (ItemStack itemStack : itemStackList){
//            playerInventory.addItem(itemStack);
//        }
//    }
//
//    public String getKitName(){
//        return name;
//    }
//
//    public List<ItemStack> getItems(){
//        return itemStackList;
//    }
//
//    public int getCost() {
//        return cost;
//    }
//
//    public int getViewPosition() {
//        return viewPosition;
//    }
//
//    @Override
//    public String toString() {
//        return "Kit{" +
//                "name='" + name + '\'' +
//                ", itemStackList=" + itemStackList +
//                ", cost=" + cost +
//                ", viewPosition=" + viewPosition +
//                "} " + super.toString();
//    }
}
