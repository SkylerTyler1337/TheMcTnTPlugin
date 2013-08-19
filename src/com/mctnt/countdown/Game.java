/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.countdown;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Harry5573
 */
public class Game extends BukkitRunnable {

    public static TheMcTnTPlugin plugin;
    private int time;

    public Game(TheMcTnTPlugin instance, int time) {
        this.time = time;
        this.plugin = instance;
    }
    
    public void cancelTask() {
        this.cancel();
    }
    
    public void run() 
    {
        
        if (time > 0) {
            
            if (time == plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".timemax") - 1) {
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "###########################################");
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "#############" + ChatColor.GREEN + "The Match has started" + ChatColor.DARK_PURPLE + "#########");
                Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "###########################################");
                
                //Force teleport everyone
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    plugin.rb.forceTpTeams(pls);
                }
            }
            
            time--;
        }
        if (time == 0) {
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "###########################################");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "##############" + ChatColor.DARK_RED + "The Match is over!" + ChatColor.DARK_PURPLE + "##############");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "######" + ChatColor.AQUA + "" + ChatColor.BOLD + "The Match Expired With No Winner" + ChatColor.DARK_PURPLE + "######");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "###########################################");
            new Cycle(plugin).runTaskTimer(plugin, 0L, 20L);
            System.out.println("[TheMcTnTPlugin] Cycling maps..");
            
            this.cancel();
        }
    }
}
