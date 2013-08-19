/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.countdown;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Harry5573
 */
public class PreGame extends BukkitRunnable {

    public static TheMcTnTPlugin plugin;
    private int starttime;

    public PreGame(TheMcTnTPlugin instance, int starttime) {
        this.starttime = starttime;
        this.plugin = instance;
    }

    public void run() {

        if (starttime > 0) {

            if (starttime == 30) {
                plugin.getConfig().set("inpregame", true);
                plugin.saveConfig();
            }

            if (starttime == 30 || starttime == 15 || starttime == 10) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Match starting in " + ChatColor.RED + starttime + ChatColor.GREEN + " seconds!");
            }
            if (starttime <= 5) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "Match starting in " + ChatColor.RED + starttime + ChatColor.GREEN + " seconds!");
                //Add a cool tick tock sound
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    pls.playSound(pls.getLocation(), Sound.CLICK, 5.0F, 5.0F);
                }
            }
            starttime--;
        }

        if (starttime == 0) {
            //Set it to not in pregame
            plugin.getConfig().set("inpregame", false);
            plugin.saveConfig();

            //Teleport all players that have /join'ed
            int time = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".timemax");

            //Start the gam
            new Game(plugin, time).runTaskTimer(plugin, 0L, 20L);
            //Stop this
            this.cancel();
        }
    }
}
