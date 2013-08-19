/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.command;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Harry5573
 */
public class CommandMap implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandMap(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("map")) {
            String mapname = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getCurrentMap() + ".displayname").replaceAll("(&([a-f0-9]))", "§$2");
            String authorname = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getCurrentMap() + ".author").replaceAll("(&([a-f0-9]))", "§$2");
            sender.sendMessage(ChatColor.GREEN + "Currenty Playing: " + ChatColor.RED + mapname + ChatColor.GREEN + " By " + authorname);
            return true;
        }
        return false;

    }
}
