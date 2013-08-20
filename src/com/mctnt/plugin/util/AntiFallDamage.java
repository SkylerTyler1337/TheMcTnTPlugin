/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 *
 * @author Harry5573
 */
public class AntiFallDamage implements Listener {

    public static TheMcTnTPlugin plugin;

    public AntiFallDamage(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
    @EventHandler
    public void onFall(EntityDamageEvent e) {
        Player p = null;
        if (e.getEntity() instanceof Player) {
            p = (Player) e.getEntity();
        }

        if (plugin.isSpectator.contains(p)) {
            if (e.getCause() == DamageCause.VOID) {
                Location specloc = null;
                String speclocstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.spectator");
                String[] spawnfinal1 = speclocstring.split(",");
                if (spawnfinal1.length == 3) {
                    int x = Integer.parseInt(spawnfinal1[0]);
                    int y = Integer.parseInt(spawnfinal1[1]);
                    int z = Integer.parseInt(spawnfinal1[2]);
                    specloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
                }
                p.teleport(specloc);
                e.setCancelled(true);
                return;
            } else if (e.getCause() == DamageCause.FALL) {
                e.setCancelled(true);
                return;
            }
        }
    }
}
