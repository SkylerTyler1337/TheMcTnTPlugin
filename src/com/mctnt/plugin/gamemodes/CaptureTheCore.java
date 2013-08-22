/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.gamemodes;

import com.mctnt.api.TeamWinEvent;
import com.mctnt.countdown.Cycle;
import com.mctnt.plugin.core.TheMcTnTPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Harry5573
 */
public class CaptureTheCore implements Listener {

    public static TheMcTnTPlugin plugin;
    public static final StateFlag RED_CORE = new StateFlag("redcore", false);
    public static final StateFlag BLUE_CORE = new StateFlag("bluecore", false);
    
    private String won = "no";

    public CaptureTheCore(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        WorldGuardPlugin wgp = this.plugin.getWGP();

        Location playerloc = e.getBlock().getLocation();
        RegionManager rm = wgp.getRegionManager(playerloc.getWorld());
        ApplicableRegionSet applicableRegions = rm.getApplicableRegions(playerloc);

        if ((applicableRegions.allows(RED_CORE)) && (plugin.isRed.contains(player))) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not break your own core");
            return;
        }
        if ((applicableRegions.allows(BLUE_CORE)) && (plugin.isBlue.contains(player))) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not break your own core");
            return;
        }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        WorldGuardPlugin wgp = this.plugin.getWGP();

        Location playerloc = e.getBlock().getLocation();
        RegionManager rm = wgp.getRegionManager(playerloc.getWorld());
        ApplicableRegionSet applicableRegions = rm.getApplicableRegions(playerloc);

        if ((applicableRegions.allows(RED_CORE)) || (applicableRegions.allows(BLUE_CORE) && (!plugin.isAdminMode.contains(player.getName())))) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not place blocks in the cores");
            return;
        }
    }
    
    @EventHandler
    public void onLavaFlow(BlockFromToEvent e) {
        
        if (!plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".gamemode").equals("ctc")) {
            return;
        }

        int coreredx = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".Core-Red-Lava-X");
        int coreredz = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".Core-Red-Lava-Z");
        int corebluex = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".Core-Blue-Lava-X");
        int corebluez = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".Core-Blue-Lava-Z");

        //Just to be sure
        if (!e.getBlock().getWorld().getName().equals(plugin.getCurrentMap())) {
            return;
        }

        if (!(e.getBlock().getType() == Material.STATIONARY_LAVA || e.getBlock().getType() == Material.LAVA)) {
            return;
        }
        
        //Check that it is not in cycle and its not in pre game
        if (!(plugin.getConfig().getString("incycle").equals("false") && (plugin.getConfig().getString("inpregame").equals("false")))) {
            return;
        }
        //Patch A1 BETA for spam win
        
        //Cooldown return if true
        if (won == "yes") {
            return;
        }
        
        if ((e.getBlock().getLocation().getBlockX() == coreredx) && (e.getBlock().getLocation().getBlockZ() == coreredz)) {
           
            //Cancel the game task
            Bukkit.getScheduler().cancelAllTasks();
            //Quick fix for it canceling auto broadcasts
            plugin.bfm.scheduleAnnouncerTask();
            //Cycle the maps!
            new Cycle(plugin).runTaskTimer(plugin, 0L, 20L);
            
            System.out.println("[TheMcTnTPlugin] Blue team won the game!");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "#################");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "##" + ChatColor.GOLD + "  Game Over!  " + ChatColor.DARK_PURPLE + "####");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "##" + ChatColor.BLUE + " Blue Team Wins! " + ChatColor.DARK_PURPLE + "##");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "#################");

            //Set to yes
            setWinner("yes");

            teamWin(plugin, "blue");
            return;
        }
        if ((e.getBlock().getLocation().getBlockX() == corebluex) && (e.getBlock().getLocation().getBlockZ() == corebluez)) {

            //Cancel the game task
            Bukkit.getScheduler().cancelAllTasks();
            //Quick fix for it canceling auto broadcasts
            plugin.bfm.scheduleAnnouncerTask();
            //Cycle the maps!
            new Cycle(plugin).runTaskTimer(plugin, 0L, 20L);

            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "#################");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "##" + ChatColor.GOLD + "  Game Over!  " + ChatColor.DARK_PURPLE + "####");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "##" + ChatColor.DARK_RED + " Red Team Wins! " + ChatColor.DARK_PURPLE + "##");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "#################");

            //Set to yes
            setWinner("yes");

            teamWin(plugin, "red");
            return;
        }
    }

    public void teamWin(Plugin plugin, String team) {
        TeamWinEvent event = new TeamWinEvent(plugin, team);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
    
    public void setWinner(String s) {
        this.won = s;
    }
}
