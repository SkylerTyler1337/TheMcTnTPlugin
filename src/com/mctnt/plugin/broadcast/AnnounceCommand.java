/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.broadcast;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Harry5573
 */
public class AnnounceCommand implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public AnnounceCommand(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("announce")) {
            if (!sender.hasPermission("mctnt.announce") || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have the needed permission to do that");
                return true;
            }
            if ((args.length == 1) && (args[0].equalsIgnoreCase("now"))) {
                sender.sendMessage(ChatColor.GREEN + "Announcement sent");
                plugin.bfm.broadcastAnnounce();
                System.out.println("[TheMcTnTPlugin] " + sender.getName() + " forced an announcement");
                return true;
            }
            if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
                sender.sendMessage(ChatColor.GREEN + "Announcements reloaded");
                plugin.cfManager.reloadBroadcastFile();
                System.out.println("[TheMcTnTPlugin] " + sender.getName() + " reloaded the announcements");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /announce <now|reload>");
            }
        }
        return false;

    }
}
