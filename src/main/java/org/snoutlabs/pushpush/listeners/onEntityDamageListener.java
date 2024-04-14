package org.snoutlabs.pushpush.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.snoutlabs.pushpush.PushPush;

public class onEntityDamageListener implements Listener {

    public final PushPush plugin;

    public onEntityDamageListener(PushPush plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player p) {

            if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN)) {
                if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN) == true) {

                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {

                        event.setCancelled(true);
                    } else {
                        p.setHealth(20.0);
                    }

                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player victim){

            if (victim.getPersistentDataContainer().has(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN)) {
                if (victim.getPersistentDataContainer().get(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN) == true) {

                    Player attacker = (Player) event.getDamager();
                    int victimLevel = victim.getLevel();

                    // Add levels in function of weapon
                    if (attacker.getInventory().getItemInMainHand().getType() == Material.STICK) {
                        victim.setLevel(victimLevel+3);
                    } else {
                        victim.setLevel(victimLevel+1);
                    }


                    // Knockback system
                    Vector direction = victim.getLocation().toVector().subtract(attacker.getLocation().toVector()).normalize();
                    double multiplyFactor = victimLevel * plugin.getConfig().getDouble("knockbackMultiplier"); // multiplier level (xp) by multiplier factor
                    double multiplyFactorUp = victimLevel * plugin.getConfig().getDouble("knockbackMultiplierUp");

                    direction.multiply(multiplyFactor);
                    Vector knockback = victim.getVelocity().add(direction);
                    knockback.setY(knockback.getY()+multiplyFactorUp);

                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        victim.setVelocity(knockback);
                    }, 1);
                }
            }
        }
    }
}
