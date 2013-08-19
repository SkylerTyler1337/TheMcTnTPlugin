/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.TagAPI;

/**
 *
 * @author Harry5573
 */
public class Spectate implements Listener {
    
    public static TheMcTnTPlugin plugin;
    
    public Spectate(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void addSpectate(Player p) {
        plugin.isSpectator.add(p);

        
        
        p.sendMessage(ChatColor.DARK_AQUA + "You are now a " + ChatColor.YELLOW + "spectator" + ChatColor.DARK_AQUA + "!");
        p.getOpenInventory().close();
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(true);
        p.setFlying(true);
        
        p.setDisplayName(ChatColor.WHITE + "[S]" + p.getName());
        
        for (Player pl : Bukkit.getOnlinePlayers()) {
            TagAPI.refreshPlayer(pl);
        }
      
        //add the server selctor
        plugin.ts.addSelector(p);
        p.getInventory().addItem(new ItemStack(Material.COMPASS, 1));

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
    }

    public void removeSpectate(Player p) {
        plugin.isSpectator.remove(p);
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            TagAPI.refreshPlayer(p);
        }
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                addSpectate(e.getPlayer());
            }
        }, 1L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setArmorContents(null);

        removeSpectate(e.getPlayer());

        if (plugin.isRed.contains(e.getPlayer())) {
        plugin.isRed.remove(e.getPlayer());
        }
        else if (plugin.isBlue.contains(e.getPlayer())) {
        plugin.isBlue.remove(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = (Player)e.getEntity();
        if (plugin.isSpectator.contains(p)) {
            e.setDeathMessage("");
            removeSpectate(p);
            addSpectate(p);
        }
    }
    
    @EventHandler
    public void onSpectateBreak(BlockBreakEvent e) {
        if (plugin.isSpectator.contains(e.getPlayer()) && (!plugin.isAdminMode.contains(e.getPlayer().getName()))) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onSpectatePlace(BlockPlaceEvent e) {
        if (plugin.isSpectator.contains(e.getPlayer()) && (!plugin.isAdminMode.contains(e.getPlayer().getName()))) {
            e.setCancelled(true);
            return;
        }
    }
    
    @EventHandler
    public void onSpectateInteract(PlayerInteractEvent e) {
        if (plugin.isSpectator.contains(e.getPlayer()) && (!plugin.isAdminMode.contains(e.getPlayer().getName()))) {
            e.setCancelled(true);
            return;
        }
    }
}
