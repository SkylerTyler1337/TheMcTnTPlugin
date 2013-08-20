/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.command;

import com.mctnt.countdown.Cycle;
import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class CommandCycle implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandCycle(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = null;
        if (!(sender instanceof Player)) {
            System.out.println("That is not a console command");
            return true;
        }
        if (sender instanceof Player) {
            p = (Player) sender;
        }

        if (commandLabel.equals("cycle")) {
            
            if (!p.hasPermission("mctnt.cycle")) {
                p.sendMessage(ChatColor.RED + "You do not have permission to perform that command");
                return true;
            }
            
            p.sendMessage(ChatColor.GREEN + "Killing all tasks....");
            Bukkit.getScheduler().cancelAllTasks();
            plugin.bfm.scheduleAnnouncerTask();
            new Cycle(plugin).runTaskTimer(plugin, 0L, 20L);
            p.sendMessage(ChatColor.GREEN + "Map has been cycled");
            return true;
        }

        return false;
    }
}
