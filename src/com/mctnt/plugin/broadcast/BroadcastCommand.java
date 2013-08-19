/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.broadcast;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Harry5573
 */
public class BroadcastCommand implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public BroadcastCommand(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("broadcast") || commandLabel.equalsIgnoreCase("bcast")) {
            if (!sender.hasPermission("mctnt.broadcast") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have the needed permission to do that");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(ChatColor.RED + "Not enough arguments");
                return true;
            }
            StringBuilder str = new StringBuilder(args[0]);
            for (int i = 1; i < args.length; i++) {
                str.append(' ').append(args[i]);
            }
            
            String bcastprefix = plugin.getConfig().getString("broadcastprefix").replaceAll("(&([a-f0-9]))", "§$2");
            Bukkit.broadcastMessage(bcastprefix + " " + str.toString().replaceAll("(&([a-f0-9]))", "§$2"));
            return true;
        }
        return false;
    }
}
