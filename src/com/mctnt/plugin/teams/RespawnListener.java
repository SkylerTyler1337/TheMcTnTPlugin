/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author Harry5573
 */
public class RespawnListener implements Listener {

    public static TheMcTnTPlugin plugin;

    public RespawnListener(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(PlayerRespawnEvent e) {
        if (plugin.isRed.contains(e.getPlayer())) {
            Location redloc = null;
            String redstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.red");
            String[] spawnfinal1 = redstring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                redloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            e.setRespawnLocation(redloc);
            plugin.itemh.giveItems(e.getPlayer());
            return;
        }
        if (plugin.isBlue.contains(e.getPlayer())) {
            Location blueloc = null;
            String bluestring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.blue");
            String[] spawnfinal1 = bluestring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                blueloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            e.setRespawnLocation(blueloc);
            plugin.itemh.giveItems(e.getPlayer());
            return;
        } else {
            Location specloc = null;
            String speclocstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.spectator");
            String[] spawnfinal1 = speclocstring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                specloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            e.setRespawnLocation(specloc);
        }
    }
}
