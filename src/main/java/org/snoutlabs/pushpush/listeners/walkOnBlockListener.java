package org.snoutlabs.pushpush.listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.util.Vector;
import org.snoutlabs.pushpush.PushPush;
import org.bukkit.persistence.PersistentDataType;

public class walkOnBlockListener implements Listener {

    public final PushPush plugin;

    public walkOnBlockListener(PushPush plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player p = event.getPlayer();
        Block block = p.getLocation().subtract(0,1,0).getBlock();
        PersistentDataContainer data = p.getPersistentDataContainer();

        String triggerBlockToJoinName = plugin.getConfig().getString("triggerBlockToJoin");
        Material triggerBlockToJoinType = Material.matchMaterial(triggerBlockToJoinName);

        String triggerBlockDeathName = plugin.getConfig().getString("triggerBlockDeath");
        Material triggerBlockDeathType = Material.matchMaterial(triggerBlockDeathName);

        if (block.getType() == triggerBlockToJoinType) {
            p.getPersistentDataContainer().set(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN, true);
            BoostPlayerVelocity(p, plugin.getConfig().getDouble("triggerBlockToJoinForwardForce"), plugin.getConfig().getDouble("triggerBlockToJoinUpwardForce"));
            JoinPlayerPushPush(p);
        } else if (block.getType() == triggerBlockDeathType) {

            if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN)) {
                if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN) == true) {
                    p.sendMessage(ChatColor.RED + "Vous êtes tombé(e) de l'arène PushPush");
                    p.teleport(plugin.getConfig().getLocation("spawn"));
                    PlayerInventory playerInventory = p.getInventory();
                    playerInventory.clear();
                    p.setLevel(0);
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN, false);
                    }, 10);
                }
            }
        }


    }

    private void BoostPlayerVelocity(Player p, double forceForward, double forceUp) {
        Vector direction = p.getLocation().getDirection().normalize();
        Vector directionUp = new Vector(0, forceUp, 0);

        direction.multiply(forceForward);

        p.setVelocity(direction);
        p.setVelocity(p.getVelocity().add(directionUp));

    }

    private void JoinPlayerPushPush(Player p) {
        // Init Player for PushPush
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setExp(0);
        PlayerInventory playerInventory = p.getInventory();
        playerInventory.clear();

        //Meta Data
        p.getPersistentDataContainer().set(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN, true);

        // Item kit

        //Push Stick
        ItemStack pushStick = new ItemStack(Material.STICK);
        ItemMeta pushStickMeta = pushStick.getItemMeta();
        pushStickMeta.setDisplayName(ChatColor.GREEN + "Push Stick");
        pushStickMeta.addEnchant(Enchantment.KNOCKBACK, 1, false);
        pushStick.setItemMeta(pushStickMeta);

        p.getInventory().setItem(0, pushStick);

        //Snowball Pouf
        ItemStack snowballPouf = new ItemStack(Material.SNOWBALL,16);
        ItemMeta snowballPoufMeta = snowballPouf.getItemMeta();
        snowballPoufMeta.setDisplayName(ChatColor.AQUA + "Pouf Ball");
        snowballPouf.setItemMeta(snowballPoufMeta);

        p.getInventory().setItem(1, snowballPouf);
    }
}
