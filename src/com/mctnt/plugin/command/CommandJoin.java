/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.command;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import static com.mctnt.plugin.util.TeamSelector.plugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class CommandJoin implements CommandExecutor {

    public static TheMcTnTPlugin plugin;

    public CommandJoin(TheMcTnTPlugin instance) {
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

        if ((plugin.getConfig().getString("incycle").equals("true"))) {
            p.sendMessage(ChatColor.RED + "You may not do that while the game is cycling");
            return true;
        }


        if (plugin.isRed.contains(p) || plugin.isBlue.contains(p)) {
            p.sendMessage(ChatColor.RED + "You are already on a team");
            return true;
        }

        if (commandLabel.equalsIgnoreCase("join")) {
            plugin.randomteam.randomTeam(p);
        }
        return false;
    }
}
