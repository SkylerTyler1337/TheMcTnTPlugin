/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.command;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class CommandMcTNT implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandMcTNT(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("mctnt") || commandLabel.equalsIgnoreCase("tnt")) {
            //Deny the console
            if (!(sender instanceof Player)) {
                System.out.println("That is not a console command");
                return true;
            }
            //Deny them if they do not have the needed permission
            if (!sender.hasPermission("mctnt.commandmctnt") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that");
                return true;
            }
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                sender.sendMessage(ChatColor.GREEN + "The McTnT Plugin Has Been Reloaded");
                plugin.reloadConfig();
                return true;
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("disable"))) {
                //tnt disable <map>
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".enabled", "false");
                plugin.cfManager.saveMapsFile();
                sender.sendMessage(ChatColor.DARK_GREEN + "Map " + ChatColor.YELLOW + args[1] + ChatColor.DARK_GREEN + " disabled");
                return true;
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("enable"))) {
                //./tnt disable <map>
                if (!plugin.cfManager.getMapsFile().contains("Maps." + args[1])) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".enabled", "true");
                plugin.cfManager.saveMapsFile();
                sender.sendMessage(ChatColor.DARK_GREEN + "Map " + ChatColor.YELLOW + args[1] + ChatColor.DARK_GREEN + " enabled");
                return true;
            }
            if ((args.length == 1) && (args[0].equalsIgnoreCase("admin"))) {
                if (!plugin.isAdminMode.contains(sender.getName())) {
                    sender.sendMessage(ChatColor.YELLOW + "You have enabled admin editing mode");
                    plugin.isAdminMode.add(sender.getName());
                    return true;
                }
                sender.sendMessage(ChatColor.YELLOW + "You have disabled admin editing mode");
                plugin.isAdminMode.remove(sender.getName());
                return true;
            }
            if (((args.length == 2) && (args[0].equalsIgnoreCase("tp")))) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                Player p = (Player) sender;
                Location loc = new Location(plugin.getServer().getWorld(args[1]),
                        plugin.getServer().getWorld(args[1]).getSpawnLocation().getX(),
                        plugin.getServer().getWorld(args[1]).getSpawnLocation().getY(),
                        plugin.getServer().getWorld(args[1]).getSpawnLocation().getZ());
                p.teleport(loc);
                p.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.AQUA + args[1]);
                return true;
            }
            if (((args.length == 2) && (args[0].equalsIgnoreCase("restore")))) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "Restoring " + ChatColor.AQUA + args[1]);
                try {
                    plugin.mmanager.restoreMap(args[1]);
                } catch (IOException ex) {
                    Logger.getLogger(CommandMcTNT.class.getName()).log(Level.SEVERE, null, ex);
                }
                return true;
            }
            if (((args.length == 2) && (args[0].equalsIgnoreCase("setdefault")))) {
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                sender.sendMessage(ChatColor.GREEN + "Set default map and saved it!");
                try {
                    plugin.mmanager.copyMap(args[1]);
                } catch (IOException ex) {
                    sender.sendMessage(ChatColor.RED + "ERROR");
                }
                sender.sendMessage(ChatColor.YELLOW + "SUCCESS");
                return true;
            }
            if ((args.length == 2) && (args[0].equalsIgnoreCase("addmap"))) {
                if (plugin.cfManager.getMapsFile().contains("Maps." + args[1])) {
                    sender.sendMessage(ChatColor.GOLD + "That map already exists :(");
                    return true;
                }
                plugin.cfManager.getMapsFile().createSection("Maps." + args[1]);
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".displayname", "ExampleName" + args[1]);
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".max-players", 25);
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".author", "Example");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".gamemode", "ctc");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".timemax", 900);
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.blue", " ");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.red", " ");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.spectator", " ");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".items", "");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".Core-Red-Lava-X", "");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".Core-Red-Lava-Z", "");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".Core-Blue-Lava-X", "");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".Core-Blue-Lava-Z", "");
                plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".bettertnt", "false");
                sender.sendMessage(ChatColor.AQUA + "Map " + ChatColor.YELLOW + args[1] + ChatColor.AQUA + " has been added");
                plugin.cfManager.saveMapsFile();
                return true;
            }

            if ((args.length == 3) && (args[0].equalsIgnoreCase("setspawn"))) {
                //./tnt setspawn <map> <spawn>
                if (Bukkit.getWorld(args[1]) == null) {
                    sender.sendMessage(ChatColor.GOLD + "That map does not exist!");
                    return true;
                }
                Player p = (Player) sender;
                Location loc = p.getLocation();
                int blockX = loc.getBlockX();
                int blockY = loc.getBlockY();
                int blockZ = loc.getBlockZ();
                if (args[2].equalsIgnoreCase("blue")) {
                    plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.blue", blockX + "," + blockY + "," + blockZ);
                    sender.sendMessage(ChatColor.BLUE + "Blue" + ChatColor.YELLOW + " spawn for " + ChatColor.DARK_RED + args[1] + ChatColor.YELLOW + " has been set!");
                    plugin.cfManager.saveMapsFile();
                    return true;
                }
                if (args[2].equalsIgnoreCase("red")) {
                    plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.red", blockX + "," + blockY + "," + blockZ);
                    sender.sendMessage(ChatColor.BLUE + "Red" + ChatColor.YELLOW + " spawn for " + ChatColor.DARK_RED + args[1] + ChatColor.YELLOW + " has been set!");
                    plugin.cfManager.saveMapsFile();
                    return true;
                }
                if (args[2].equalsIgnoreCase("spectator")) {
                    plugin.cfManager.getMapsFile().set("Maps." + args[1] + ".teamspawns.spectator", blockX + "," + blockY + "," + blockZ);
                    sender.sendMessage(ChatColor.BLUE + "Spectator" + ChatColor.YELLOW + " spawn for " + ChatColor.DARK_RED + args[1] + ChatColor.YELLOW + " has been set!");
                    plugin.cfManager.saveMapsFile();
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /mctnt setspawn <map> <spawn>");
                    return true;
                }
            }
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reloadmaps"))) {
                plugin.cfManager.reloadMapsFile();
                sender.sendMessage(ChatColor.BLUE + "Maps have been reloaded");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /mctnt <reload||setdefault||restore||tp||addmap||reloadmaps||setspawn||admin||enable||disable> <map>");
            }
        }
        return false;
    }
}