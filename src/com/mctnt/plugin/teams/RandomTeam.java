/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

/**
 *
 * @author Harry5573
 */
public class RandomTeam implements Listener {
        public static TheMcTnTPlugin plugin;

    public RandomTeam(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void randomTeam(Player p) {

        int maxperteam = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".max-players");


        if (((plugin.isRed.size() == plugin.isBlue.size() || plugin.isBlue.size() == plugin.isRed.size()))) {
            if (plugin.isBlue.size() != maxperteam) {
                plugin.rb.joinBlue(p);
                return;
            } else if (plugin.isRed.size() != maxperteam) {
                plugin.rb.joinRed(p);
                return;
            }
        }

        //If reds bigher
        if (plugin.isRed.size() > plugin.isBlue.size() && (plugin.isBlue.size() != maxperteam)) {
            plugin.rb.joinBlue(p);
            return;
        }


        if (plugin.isBlue.size() > plugin.isRed.size() && (plugin.isRed.size() != maxperteam)) {
            plugin.rb.joinRed(p);
            return;
        } else if ((plugin.isRed.size() == maxperteam) && (plugin.isBlue.size() == maxperteam)) {
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "TEAMS FULL");
        }

    }
}
