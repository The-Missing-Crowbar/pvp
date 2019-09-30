package io.github.officereso;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public final class pvp extends JavaPlugin implements Listener {
    private PotionEffect heal = new PotionEffect(PotionEffectType.HEAL,1,1);
    private PotionEffect damage = new PotionEffect(PotionEffectType.HARM,1,1);
    private PotionEffect poison = new PotionEffect(PotionEffectType.POISON,66,1);
    private PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,90,2);

    private ItemStack splashHeal = new ItemStack(Material.SPLASH_POTION);
    private ItemStack splashDamage = new ItemStack(Material.SPLASH_POTION);
    private ItemStack splashPoison = new ItemStack(Material.SPLASH_POTION);
    private ItemStack potionSpeed = new ItemStack(Material.POTION);
    private ItemStack lingerHeal = new ItemStack(Material.LINGERING_POTION);

    private ItemStack godapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
    private ItemStack food = new ItemStack(Material.COOKED_BEEF, 64);
    private ItemStack swordMan1 = new ItemStack(Material.STONE_SWORD);
    private ItemStack axeMan1 = new ItemStack(Material.IRON_AXE);
    private ItemStack[] archer1 = {new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 64), new ItemStack(Material.ARROW, 64)};
    private ItemStack seafarer1 = new ItemStack(Material.TRIDENT);
    private ItemStack stick1 = new ItemStack(Material.STICK);
    private ItemStack swordMan2 = new ItemStack(Material.IRON_SWORD);
    private ItemStack axeMan2 = new ItemStack(Material.DIAMOND_AXE);
    private ItemStack[] archer2 = {new ItemStack(Material.CROSSBOW), new ItemStack(Material.ARROW, 64), new ItemStack(Material.ARROW, 64)};
    private ItemStack seafarer2 = new ItemStack(Material.TRIDENT);
    private ItemStack stick2 = new ItemStack(Material.STICK);


    @Override
    public void onEnable() {
        try {
            PotionMeta potionMeta = (PotionMeta) potionSpeed.getItemMeta();
            potionMeta.addCustomEffect(speed, true);
            potionSpeed.setItemMeta(potionMeta);

            PotionMeta splashHealMeta = (PotionMeta) splashHeal.getItemMeta();
            splashHealMeta.addCustomEffect(heal, true);
            splashHeal.setItemMeta(splashHealMeta);

            PotionMeta lingerHealMeta = (PotionMeta) lingerHeal.getItemMeta();
            lingerHealMeta.addCustomEffect(heal, true);
            lingerHeal.setItemMeta(lingerHealMeta);

            PotionMeta splashDamageMeta = (PotionMeta) splashDamage.getItemMeta();
            splashDamageMeta.addCustomEffect(damage, true);
            splashDamage.setItemMeta(splashDamageMeta);

            PotionMeta splashPoisonMeta = (PotionMeta) splashPoison.getItemMeta();
            splashPoisonMeta.addCustomEffect(poison, true);
            splashPoison.setItemMeta(splashPoisonMeta);

            ItemMeta seafarer1EnchantMeta = seafarer1.getItemMeta();
            seafarer1EnchantMeta.addEnchant(Enchantment.LOYALTY, 1, true);
            seafarer1.setItemMeta(seafarer1EnchantMeta);

            getServer().getPluginManager().registerEvents(this, this);
        }
        catch(NullPointerException ignore){ }
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }

    @EventHandler
    public void onSignClickKit(PlayerInteractEvent event){
        try {
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -133 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 6) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(swordMan1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -134 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 6) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(axeMan1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -135 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 4)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(archer1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-4);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -136 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 6)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(seafarer1);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-6);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -137 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 6)) {
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

            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -133 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(swordMan2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-5);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -134 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 7)) {
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
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -135 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setArmorContents(null);
                event.getPlayer().getInventory().addItem(archer2);
                event.getPlayer().getInventory().addItem(food);
                event.getPlayer().getInventory().setChestplate(new ItemStack((Material.IRON_CHESTPLATE)));
                event.getPlayer().setLevel(event.getPlayer().getLevel()-5);
            }
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -136 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 10)) {
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
            else if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp" )&& event.getClickedBlock().getX() == -137 && event.getClickedBlock().getZ() == -248 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 13)) {
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
        catch(NullPointerException ignored){ }

    }

    @EventHandler
    public void onSignClickWeapons(PlayerInteractEvent event){
        try {
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -138 && event.getClickedBlock().getZ() == -249 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().addItem(swordMan2);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 5);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -138 && event.getClickedBlock().getZ() == -250 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 14)) {
                event.getPlayer().getInventory().addItem(seafarer1);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 14);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -138 && event.getClickedBlock().getZ() == -249 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 7)) {
                event.getPlayer().getInventory().addItem(archer1);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 7);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -138 && event.getClickedBlock().getZ() == -250 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 10)) {
                event.getPlayer().getInventory().addItem(archer2);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 7);
            }
        }
        catch(NullPointerException ignore){ }
    }

    @EventHandler
    public void onSignClickPotions(PlayerInteractEvent event){
        try {
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -132 && event.getClickedBlock().getZ() == -250 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(splashHeal);
                event.getPlayer().getInventory().addItem(lingerHeal);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -132 && event.getClickedBlock().getZ() == -249 && event.getClickedBlock().getY() == 6 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(godapple);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -132 && event.getClickedBlock().getZ() == -250 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 5)) {
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashDamage);
                event.getPlayer().getInventory().addItem(splashPoison);
                event.getPlayer().getInventory().addItem(splashPoison);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 5);
            }
            if (event.getClickedBlock().getWorld() == getServer().getWorld("pvp") && event.getClickedBlock().getX() == -132 && event.getClickedBlock().getZ() == -249 && event.getClickedBlock().getY() == 5 && (event.getPlayer().getLevel() >= 3)) {
                event.getPlayer().getInventory().addItem(potionSpeed);
                event.getPlayer().getInventory().addItem(potionSpeed);
                event.getPlayer().getInventory().addItem(potionSpeed);
                event.getPlayer().getInventory().addItem(potionSpeed);
                event.getPlayer().setLevel(event.getPlayer().getLevel() - 3);
            }
        }
        catch(NullPointerException ignore){ }
    }
}
