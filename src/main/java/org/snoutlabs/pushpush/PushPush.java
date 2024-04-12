package org.snoutlabs.pushpush;

import org.bukkit.plugin.java.JavaPlugin;
import org.snoutlabs.pushpush.commands.pushpushCommands;

import java.util.Objects;

public final class PushPush extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveConfig();
        Objects.requireNonNull(getCommand("pushpush")).setExecutor(new pushpushCommands(this));
    }
}
