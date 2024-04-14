package org.snoutlabs.pushpush.listeners;

import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.persistence.PersistentDataType;
import org.snoutlabs.pushpush.PushPush;

public class doubleJumpListener implements Listener {

    public final PushPush plugin;

    public doubleJumpListener(PushPush plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player p = event.getPlayer();
        if (p.getPersistentDataContainer().has(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN)) {
            if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN) == true) {

                if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {

                    return;

                } else if (!p.isOnGround()) {

                    event.setCancelled(true);

                    p.setAllowFlight(false);
                    p.setVelocity(p.getVelocity().setY(plugin.getConfig().getDouble("doubleJumpForce")));
                }
            }
        }
    }
}
