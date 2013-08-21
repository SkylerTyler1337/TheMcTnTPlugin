/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 *
 * @author Harry5573
 */
public class AntiBuildInBase implements Listener {
    
    public static TheMcTnTPlugin plugin;
    public static final StateFlag GLOBAL_BUILD = new StateFlag("globalbuild", true);
    
    public AntiBuildInBase(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
    //
    //Each map will have 2 possible regions
    //Regions flags will be
    //bluedeny
    //reddeny
    //
    //
    //
    //
    
    
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        WorldGuardPlugin wgp = this.plugin.getWGP();

        Location playerloc = e.getBlock().getLocation();
        RegionManager rm = wgp.getRegionManager(playerloc.getWorld());
        ApplicableRegionSet applicableRegions = rm.getApplicableRegions(playerloc);

        if (applicableRegions.allows(GLOBAL_BUILD) && (!plugin.isAdminMode.contains(player.getName()))) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not break blocks outside of the gamezone");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        WorldGuardPlugin wgp = this.plugin.getWGP();

        Location playerloc = e.getBlock().getLocation();
        RegionManager rm = wgp.getRegionManager(playerloc.getWorld());
        ApplicableRegionSet applicableRegions = rm.getApplicableRegions(playerloc);

        if (applicableRegions.allows(GLOBAL_BUILD) && (!plugin.isAdminMode.contains(player.getName()))) {
            e.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You may not place blocks outside of the gamezone");
        }
    }
}
