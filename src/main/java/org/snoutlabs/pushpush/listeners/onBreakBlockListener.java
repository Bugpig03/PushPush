package org.snoutlabs.pushpush.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.snoutlabs.pushpush.PushPush;

public class onBreakBlockListener implements Listener {

    public final PushPush plugin;

    public onBreakBlockListener(PushPush plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN)) {
            if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN) == true) {
                event.setCancelled(true);
            }
        }
    }
}
