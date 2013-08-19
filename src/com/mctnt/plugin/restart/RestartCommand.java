/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.restart;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class RestartCommand implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public RestartCommand(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("reboot")) {
            //Deny them if they do not have the needed permission
            if (!sender.hasPermission("mctnt.commandmctnt") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /restart <time>");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Server will restart in " + args[0] + " seconds!");
            int reboot = Integer.valueOf(args[0]);
            
            new RestartTask(plugin, reboot).runTaskTimer(plugin, 0L, 20L);
            return true;
        }
        return false;
    }
}
