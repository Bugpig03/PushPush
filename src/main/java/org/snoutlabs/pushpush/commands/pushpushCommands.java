package org.snoutlabs.pushpush.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
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
                        p.sendMessage("Vous venez de définir les coordonnées du spawn PushPush");
                    }
                } else {
                    p.sendMessage("Vous n'avez pas le permissions de faire cette commande");
                }
            } else if (args[0].equals("join")) {
                Location location = plugin.getConfig().getLocation("spawn");
                if (location != null) {
                    p.teleport(location);
                }
                // Init Player for PushPush
                p.setGameMode(GameMode.SURVIVAL);
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setLevel(0);
                p.setExp(0);
                PlayerInventory playerInventory = p.getInventory();
                playerInventory.clear();

                // Item kit

                //Push Stick
                ItemStack pushStick = new ItemStack(Material.STICK);
                ItemMeta pushStickMeta = pushStick.getItemMeta();
                pushStickMeta.setDisplayName(ChatColor.GREEN + "Push Stick");
                pushStickMeta.addEnchant(Enchantment.KNOCKBACK, 2, false);
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


        return true;
    }
}
