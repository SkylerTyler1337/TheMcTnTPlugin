/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.google.common.base.Preconditions;
import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 *
 * @author Harry5573
 */
public class BetterTnT implements Listener {
 
    TheMcTnTPlugin plugin;
    
    public BetterTnT(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityClick(PlayerInteractEntityEvent e) {

        //Return if the map dosent have tntshears enabled
        if (!plugin.cfManager.getMapsFile().getString("Maps." + plugin.getCurrentMap() + ".bettertnt").equals("true")) {
            return;
        }

        Entity clicked = e.getRightClicked();
        Player player = e.getPlayer();
        if (player.getItemInHand().getType().equals(Material.SHEARS) && (clicked instanceof TNTPrimed)) {
            e.getPlayer().sendMessage(ChatColor.YELLOW + "You defused some tnt that was " + ChatColor.AQUA + clicked.getTicksLived() + "%" + ChatColor.YELLOW + " primed!");
            clicked.remove();
        }
    }
    
}
