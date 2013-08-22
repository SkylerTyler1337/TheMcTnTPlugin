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
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class CommandStats implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandStats(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("stats")) {
            if (!(sender instanceof Player)) {
                System.out.println("That is not a console command");
                return true;
            }
            if (args.length == 0) {
                
                Double statskills = plugin.cfManager.getUsersFile().getDouble("Users." + sender.getName() + ".kills");
                Double statsdeaths = plugin.cfManager.getUsersFile().getDouble("Users." + sender.getName() + ".deaths");

                if (statskills == 0) {
                    sender.sendMessage(ChatColor.GOLD + "Please get a few more kills before doing that!");
                    return true;
                }       

                if (statsdeaths == 0) {
                    sender.sendMessage(ChatColor.GOLD + "Please get a few more kills before doing that!");
                    return true;
                }

                Double kdr = statskills / statsdeaths;
                sender.sendMessage(ChatColor.DARK_RED + "=====================================");
                sender.sendMessage(ChatColor.GOLD + "Viewing stats for:" + ChatColor.BLUE + " " + sender.getName());
                sender.sendMessage(ChatColor.YELLOW + "Kills: " + ChatColor.DARK_GREEN + statskills);
                sender.sendMessage(ChatColor.YELLOW + "Deaths: " + ChatColor.DARK_GREEN + statsdeaths);
                sender.sendMessage(ChatColor.YELLOW + "KDR: " + ChatColor.DARK_GREEN + kdr);
                sender.sendMessage(ChatColor.DARK_RED + "=====================================");
                return true;
            }
        }
        return false;
    }
}
