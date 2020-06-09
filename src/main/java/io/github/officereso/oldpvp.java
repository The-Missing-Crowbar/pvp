package io.github.officereso;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;


public final class oldpvp extends JavaPlugin implements Listener {
    private final PotionEffect heal = new PotionEffect(PotionEffectType.HEAL, 1, 1);
    private final PotionEffect damage = new PotionEffect(PotionEffectType.HARM, 1, 1);

    private final ItemStack splashHeal = new ItemStack(Material.SPLASH_POTION, 1);
    private final ItemStack splashDamage = new ItemStack(Material.SPLASH_POTION);
    private final ItemStack splashPoison = new ItemStack(Material.SPLASH_POTION);
    private final ItemStack splashSpeed = new ItemStack(Material.SPLASH_POTION);
    private final ItemStack lingerHeal = new ItemStack(Material.LINGERING_POTION);

    private final ItemStack godapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
    private final ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);
    private final ItemStack swordMan1 = new ItemStack(Material.STONE_SWORD);
    private final ItemStack axeMan1 = new ItemStack(Material.IRON_AXE);
    private final ItemStack[] archer1 = {new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 64), new ItemStack(Material.ARROW, 64)};
    private final ItemStack seafarer1 = new ItemStack(Material.TRIDENT);
    private final ItemStack stick1 = new ItemStack(Material.STICK);
    private final ItemStack swordMan2 = new ItemStack(Material.IRON_SWORD);
    private final ItemStack axeMan2 = new ItemStack(Material.DIAMOND_AXE);
    private final ItemStack[] archer2 = {new ItemStack(Material.CROSSBOW), new ItemStack(Material.ARROW, 64), new ItemStack(Material.ARROW, 64)};
    private final ItemStack seafarer2 = new ItemStack(Material.TRIDENT);
    private final ItemStack stick2 = new ItemStack(Material.STICK);

    Location[] spawns;


    @Override
    public void onEnable() {
        try {
            PotionMeta potionMeta = (PotionMeta) splashSpeed.getItemMeta();
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3), true);
            splashSpeed.setItemMeta(potionMeta);

            PotionMeta splashHealMeta = (PotionMeta) splashHeal.getItemMeta();
            splashHealMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 20, 4), true);
            splashHeal.setItemMeta(splashHealMeta);

            PotionMeta lingerHealMeta = (PotionMeta) lingerHeal.getItemMeta();
            lingerHealMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 400, 10), true);
            lingerHeal.setItemMeta(lingerHealMeta);

            PotionMeta splashDamageMeta = (PotionMeta) splashDamage.getItemMeta();
            splashDamageMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 1, 1), true);
            splashDamage.setItemMeta(splashDamageMeta);

            PotionMeta splashPoisonMeta = (PotionMeta) splashPoison.getItemMeta();
            splashPoisonMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 100, 2), true);
            splashPoison.setItemMeta(splashPoisonMeta);

            ItemMeta seafarer1EnchantMeta = seafarer1.getItemMeta();
            seafarer1EnchantMeta.addEnchant(Enchantment.LOYALTY, 1, true);
            seafarer1.setItemMeta(seafarer1EnchantMeta);

            spawns = new Location[]{
                    new Location(getServer().getWorld("spawn"), 2618, 88, 883),
                    new Location(getServer().getWorld("spawn"), 2520, 90, 902),
                    new Location(getServer().getWorld("spawn"), 2512, 109, 926),
                    new Location(getServer().getWorld("spawn"), 2519, 106, 942),
                    new Location(getServer().getWorld("spawn"), 2575, 77, 923),
                    new Location(getServer().getWorld("spawn"), 2586, 73, 943),
                    new Location(getServer().getWorld("spawn"), 2619, 66, 936),
                    new Location(getServer().getWorld("spawn"), 2614, 69, 877),
                    new Location(getServer().getWorld("spawn"), 2528, 101, 880),
                    new Location(getServer().getWorld("spawn"), 2607, 68, 857),
                    new Location(getServer().getWorld("spawn"), 2554, 63, 839),
                    new Location(getServer().getWorld("spawn"), 2566, 96, 876)};

            getServer().getPluginManager().registerEvents(this, this);
        } catch (NullPointerException ignore) {
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    private Location[] jungleRoofs() {
        return spawns;
    }

    @EventHandler
    public void onSignClickKit(PlayerInteractEvent event){
        if(event.getClickedBlock()!=null){
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 807 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 67) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(swordMan1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 808 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 67) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(axeMan1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 809 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 4)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(archer1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-4);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 810 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 6)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(seafarer1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-6);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 811 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 6)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                ItemMeta stick1EnchantMeta = stick1.getItemMeta();
                stick1EnchantMeta.addEnchant(Enchantment.KNOCKBACK, 10, true);
                stick1.setItemMeta(stick1EnchantMeta);
                event.getPlayer().getInventory().addItem(stick1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-6);
            }

            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 807 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 66 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(swordMan2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-5);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 808 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 66 && (event.getPlayer().getLevel() >= 7)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                ItemMeta axeMan2EnchantMeta = axeMan2.getItemMeta();
                axeMan2EnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                axeMan2.setItemMeta(axeMan2EnchantMeta);
                event.getPlayer().getInventory().addItem(axeMan2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-7);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 809 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 66 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(archer2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-5);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 810 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 66 && (event.getPlayer().getLevel() >= 10)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                ItemMeta seafarer2EnchantMeta = seafarer2.getItemMeta();
                seafarer2EnchantMeta.addEnchant(Enchantment.IMPALING, 3, true);
                seafarer2EnchantMeta.addEnchant(Enchantment.LOYALTY, 1, true);
                seafarer2.setItemMeta(seafarer2EnchantMeta);
                event.getPlayer().getInventory().addItem(seafarer2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-10);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn" )&& event.getClickedBlock().getX() == 811 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 66 && (event.getPlayer().getLevel() >= 13)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                ItemMeta stick2EnchantMeta = stick2.getItemMeta();
                stick2EnchantMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);
                stick2.setItemMeta(stick2EnchantMeta);
                event.getPlayer().getInventory().addItem(stick2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-13);
            }
        }

    }

    @EventHandler
    public void onSignClickWeapons(PlayerInteractEvent event){
        if(event.getClickedBlock()!=null){
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 806 && event.getClickedBlock().getZ() == 53 && event.getClickedBlock().getY() == 68 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().addItem(swordMan2);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 5);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 806 && event.getClickedBlock().getZ() == 52 && event.getClickedBlock().getY() == 68 && (event.getPlayer().getLevel() >= 14)) {
                event.getPlayer().getInventory().addItem(seafarer1);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 14);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 806 && event.getClickedBlock().getZ() == 53 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 7)) {
                event.getPlayer().getInventory().addItem(archer1);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 7);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 806 && event.getClickedBlock().getZ() == 52 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 10)) {
                event.getPlayer().getInventory().addItem(archer2);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 7);
            }
        }
    }

    @EventHandler
    public void onSignClickPotions(PlayerInteractEvent event){
        if(event.getClickedBlock()!=null){
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 812 && event.getClickedBlock().getZ() == 52 && event.getClickedBlock().getY() == 68 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(lingerHeal);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 812 && event.getClickedBlock().getZ() == 53 && event.getClickedBlock().getY() == 68 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(godapple);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 812 && event.getClickedBlock().getZ() == 52 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashPoison);
                event.getPlayer().getInventory().addItem(splashPoison);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 5);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 812 && event.getClickedBlock().getZ() == 53 && event.getClickedBlock().getY() == 67 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(splashSpeed);
                event.getPlayer().getInventory().addItem(splashSpeed);
                event.getPlayer().getInventory().addItem(splashSpeed);
                event.getPlayer().getInventory().addItem(splashSpeed);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
        }
    }

    @EventHandler
    public void onSignBattle(PlayerInteractEvent event){
        if(event.getClickedBlock()!=null){
            if (event.getClickedBlock().getWorld() == getServer().getWorld("spawn") && event.getClickedBlock().getX() == 809 && event.getClickedBlock().getZ() == 50 && event.getClickedBlock().getY() == 69){
                event.getPlayer().teleport(jungleRoofs()[new Random().nextInt(jungleRoofs().length)]);
            }
        }
    }
    @EventHandler
    public void onKill(PlayerDeathEvent event){
        if(event.getEntity().getKiller()!=null){
            if (event.getEntity().getWorld() == getServer().getWorld("spawn")) {
                int level = event.getEntity().getLevel();
                event.getEntity().getKiller().setLevel(event.getEntity().getKiller().getLevel() + 2);
                event.getEntity().getInventory().clear();
                event.getEntity().setLevel(level);
            }
        }
    }
}
