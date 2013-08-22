/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.countdown;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Harry5573
 */
public class Cycle extends BukkitRunnable {

    public static TheMcTnTPlugin plugin;
    private int time2Cycle = 30;

    public Cycle(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void run() {

        //Just to be sure
        if (time2Cycle > 0) {

            if (time2Cycle == 30) {
                plugin.ctc.setWinner("no");
                
                
                plugin.getConfig().set("incycle", true);
                plugin.saveConfig();
                plugin.cycleMaps();

                plugin.isBlue.clear();
                plugin.isRed.clear();
                plugin.isSpectator.clear();

                //Tp everyone because we cleared teams
                for (Player ptp : Bukkit.getOnlinePlayers()) {
                    //Make em all spectators
                    plugin.smanager.addSpectate(ptp);
                    //We do not need to tp them as add spectate method allready does
                }
            }

            if (time2Cycle == 30 || time2Cycle == 20 || time2Cycle == 15 || time2Cycle == 10 || time2Cycle <= 5) {
                String cycleto = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getConfig().get("cycletomap") + ".displayname").replaceAll("(&([a-f0-9]))", "§$2");
                String cycletoauthor = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getConfig().get("cycletomap") + ".author").replaceAll("(&([a-f0-9]))", "§$2");
                Bukkit.broadcastMessage(ChatColor.GREEN + "Cycling to " + cycleto + ChatColor.GREEN + " By " + cycletoauthor + ChatColor.GREEN + " In " + ChatColor.DARK_RED + time2Cycle + ChatColor.GREEN + " seconds!");
            }
            time2Cycle--;
        }

        if (time2Cycle == 0) {

            //Set the cycle map to the currentmap
            plugin.getConfig().set("currentmap", plugin.getConfig().getString("cycletomap"));
            plugin.saveConfig();

            String map = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getConfig().get("currentmap") + ".displayname").replaceAll("(&([a-f0-9]))", "§$2");
            String mapauthor = plugin.cfManager.getMapsFile().getString("Maps." + plugin.getConfig().get("cycletomap") + ".author").replaceAll("(&([a-f0-9]))", "§$2");

            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Now playing " + map + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + " By " + mapauthor + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "!");
            try {
                //Restore the old map
                plugin.mmanager.restoreMap(plugin.getConfig().getString("currentmap"));
            } catch (IOException ex) {
                System.out.println("[TheMcTnTPlugin] Error while restoring map");
            }

            for (Player ps : Bukkit.getOnlinePlayers()) {
                ps.getOpenInventory().close();
            }

            //Tp everyone and clear teams AGAIN because we want to be sure
            plugin.isBlue.clear();
            plugin.isRed.clear();
            plugin.isSpectator.clear();

            //Tp everyone because we cleared teams
            for (Player ptp : Bukkit.getOnlinePlayers()) {
                //Make em all spectators
                plugin.smanager.addSpectate(ptp);
                //We do not need to tp them as add spectate method allready does
            }

            //Start the pregame timer 30 seconds
            new PreGame(plugin, 30).runTaskTimer(plugin, 0L, 20L);

            //Set in cycle
            plugin.getConfig().set("incycle", false);
            plugin.saveConfig();



            //Stop this timer :(
            this.cancel();
        }
    }
}