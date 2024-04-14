package org.snoutlabs.pushpush;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.snoutlabs.pushpush.commands.pushpushCommands;
import org.snoutlabs.pushpush.listeners.doubleJumpListener;
import org.snoutlabs.pushpush.listeners.onBreakBlockListener;
import org.snoutlabs.pushpush.listeners.onEntityDamageListener;
import org.snoutlabs.pushpush.listeners.walkOnBlockListener;

import java.util.Objects;

public final class PushPush extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Load Config.yml
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        //Register Events
        getServer().getPluginManager().registerEvents(new walkOnBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new onEntityDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new onBreakBlockListener(this), this);
        getServer().getPluginManager().registerEvents(new doubleJumpListener(this),this);

        //Register Commands
        Objects.requireNonNull(getCommand("pushpush")).setExecutor(new pushpushCommands(this));

    }
}
