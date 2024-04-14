package org.snoutlabs.pushpush.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.snoutlabs.pushpush.PushPush;

public class pushpushCommands implements CommandExecutor {

    public final PushPush plugin;

    public pushpushCommands(PushPush plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {

            if (args[0].equals("setup")) {

                if (p.isOp()) {

                    if (args[1].equals("spawn")) {
                        Location location = p.getLocation();
                        plugin.getConfig().set("spawn", location);
                        plugin.saveConfig();
                        p.sendMessage(ChatColor.GREEN + "Vous venez de définir les coordonnées du spawn PushPush");
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED +"Vous n'avez pas lA permissions de faire cette commande");
                }


            } else if (args[0].equals("join")) {
                Location location = plugin.getConfig().getLocation("spawn");
                if (location != null) {
                    p.teleport(location);
                    p.sendMessage(ChatColor.GREEN + "Vous avez rejoint le PushPush");
                }

            } else if (args[0].equals("leave")) {

                Location location = plugin.getConfig().getLocation("spawn");
                PersistentDataContainer data = p.getPersistentDataContainer();

                p.getPersistentDataContainer().set(new NamespacedKey(plugin, "pushpush"), PersistentDataType.BOOLEAN, false);
                p.sendMessage(ChatColor.RED + "Vous avez quitté(e) le PushPush");
                p.teleport(location);
                PlayerInventory playerInventory = p.getInventory();
                playerInventory.clear();
                p.setAllowFlight(false);
            }
        }


        return true;
    }
}
