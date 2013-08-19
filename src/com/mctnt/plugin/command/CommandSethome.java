/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.command;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author Harry5573
 */
public class CommandSethome implements Listener, CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandSethome(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("sethome")) {
            if (!sender.hasPermission("mctnt.commandsethome") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that");
                return true;
            }
            if (!(sender instanceof Player)) {
                System.out.println("That is not a console command");
                return true;
            }
            if ((args.length == 1)) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                int blockX = loc.getBlockX();
                int blockY = loc.getBlockY();
                int blockZ = loc.getBlockZ();
                if (args[0].contains("1")) {
                    sender.sendMessage(ChatColor.GREEN + "Your home #1 has been set");
                    plugin.cfManager.getUsersFile().set("Users." + p.getName() + "Home1", blockX + "," + blockY + "," + blockZ);
                    plugin.cfManager.saveUsersFile();
                    return true;
                }
                if (args[0].contains("2")) {
                    sender.sendMessage(ChatColor.GREEN + "Your home #2 has been set");
                    plugin.cfManager.getUsersFile().set("Users." + p.getName() + "Home2", blockX + "," + blockY + "," + blockZ);
                    plugin.cfManager.saveUsersFile();
                    return true;
                }
                if (args[0].contains("3")) {
                    sender.sendMessage(ChatColor.GREEN + "Your home #3 has been set");
                    plugin.cfManager.getUsersFile().set("Users." + p.getName() + "Home3", blockX + "," + blockY + "," + blockZ);
                    plugin.cfManager.saveUsersFile();
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /sethome <1|2|3>");
                    return true;
                }
            }
        }
        if (commandLabel.equalsIgnoreCase("home")) {
            if (!sender.hasPermission("mctnt.commandhome") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that");
                return true;
            }
            if (!(sender instanceof Player)) {
                System.out.println("That is not a console command");
                return true;
            }
            if ((args.length == 1)) {
                Player p = (Player) sender;
                Location loc = p.getLocation();
                int blockX = loc.getBlockX();
                int blockY = loc.getBlockY();
                int blockZ = loc.getBlockZ();
                if (args[0].contains("1")) {
                    if (!plugin.cfManager.getUsersFile().contains("Users." + p.getName() + "Home1")) {
                        p.sendMessage(ChatColor.RED + "You do not have that home set use /sethome first");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have been teleported to your home #1");
                    Location home1 = null;
                    String home1string = (String) plugin.cfManager.getUsersFile().get("Users." + p.getName() + "Home1");
                    String[] spawnfinal1 = home1string.split(",");
                    if (spawnfinal1.length == 3) {
                        int x = Integer.parseInt(spawnfinal1[0]);
                        int y = Integer.parseInt(spawnfinal1[1]);
                        int z = Integer.parseInt(spawnfinal1[2]);
                        home1 = new Location(plugin.getServer().getWorld(p.getWorld().getName()), x, y, z);
                    }
                    p.teleport(home1, PlayerTeleportEvent.TeleportCause.COMMAND);
                    return true;
                }
                if (args[0].contains("2")) {
                    if (!plugin.cfManager.getUsersFile().contains("Users." + p.getName() + "Home2")) {
                        p.sendMessage(ChatColor.RED + "You do not have that home set use /sethome first");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have been teleported to your home #2");
                    Location home2 = null;
                    String home2string = (String) plugin.cfManager.getUsersFile().get("Users." + p.getName() + "Home2");
                    String[] spawnfinal2 = home2string.split(",");
                    if (spawnfinal2.length == 3) {
                        int x = Integer.parseInt(spawnfinal2[0]);
                        int y = Integer.parseInt(spawnfinal2[1]);
                        int z = Integer.parseInt(spawnfinal2[2]);
                        home2 = new Location(plugin.getServer().getWorld(p.getWorld().getName()), x, y, z);
                    }
                    p.teleport(home2, PlayerTeleportEvent.TeleportCause.COMMAND);
                    return true;
                }
                if (args[0].contains("3")) {
                    if (!plugin.cfManager.getUsersFile().contains("Users." + p.getName() + "Home3")) {
                        p.sendMessage(ChatColor.RED + "You do not have that home set use /sethome first");
                        return true;
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have been teleported to your home #3");
                    Location home3 = null;
                    String home3string = (String) plugin.cfManager.getUsersFile().get("Users." + p.getName() + "Home3");
                    String[] spawnfinal1 = home3string.split(",");
                    if (spawnfinal1.length == 3) {
                        int x = Integer.parseInt(spawnfinal1[0]);
                        int y = Integer.parseInt(spawnfinal1[1]);
                        int z = Integer.parseInt(spawnfinal1[2]);
                        home3 = new Location(plugin.getServer().getWorld(p.getWorld().getName()), x, y, z);
                    }
                    p.teleport(home3, PlayerTeleportEvent.TeleportCause.COMMAND);
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /home <1|2|3>");
                    return true;
                }
            }
        }
        return false;
    }
}
