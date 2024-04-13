package org.snoutlabs.pushpush.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
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
}
