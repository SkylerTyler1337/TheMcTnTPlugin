/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Harry5573
 */
public class AntiTeamkill implements Listener {

    public static TheMcTnTPlugin plugin;

    public AntiTeamkill(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
    @EventHandler(priority= EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        Player attacker = null;
        Player damaged = null;
        
        if (e.getDamager() instanceof Player) {
            attacker = (Player)e.getDamager();
        }
        
        if (e.getEntity() instanceof Player) {
            damaged = (Player)e.getEntity();
        }
        
        if (damaged == null) {
            return;
        }
        
        if (attacker == null) {
            return;
        }
        
        if (plugin.isSpectator.contains(attacker)) {
            e.setCancelled(true);
            return;
        }
        if (plugin.isSpectator.contains(damaged)) {
            e.setCancelled(true);
            return;
        }

        if (plugin.isRed.contains(damaged) && plugin.isRed.contains(attacker)) {
            e.setCancelled(true);
            return;
        }
        if (plugin.isBlue.contains(damaged) && plugin.isBlue.contains(attacker)) {
            e.setCancelled(true);
        }
    }
    
}
