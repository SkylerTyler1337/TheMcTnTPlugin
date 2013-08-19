/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.restart;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Harry5573
 */
public class RestartTask extends BukkitRunnable {

    public static TheMcTnTPlugin plugin;
    public int time2restart;

    public RestartTask(TheMcTnTPlugin instance, int time) {
        this.plugin = instance;
        this.time2restart = time;
    }

    public void run() {

        //Just to be sure
        if (time2restart > 0) {

            if (time2restart == 30 || time2restart == 20 || time2restart == 15 || time2restart == 10 || time2restart <= 5) {
                Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "*** SERVER RESTARTING IN " + time2restart + " SECONDS ***");
            }
            time2restart--;
        }

        if (time2restart == 0) {
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "*** SERVER RESTARTING ***");

            for (Player pl : Bukkit.getOnlinePlayers()) {
                pl.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "*** SERVER RESTARTING ***");
            }

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");

            //Stop this timer :(
            this.cancel();

        }
    }
}
