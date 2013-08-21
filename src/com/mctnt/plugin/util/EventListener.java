/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_6_R2.block.CraftDispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Harry5573
 */
public class EventListener implements Listener {

    public static TheMcTnTPlugin plugin;

    public EventListener(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onNewChunk(ChunkLoadEvent e) {
        if (e.isNewChunk() && plugin.getConfig().getString("chunkgenoff").equals("true")) {
            e.getChunk().unload(true);
        }
    }

    @EventHandler
    public void Sign(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockState sign = block.getState();
        for (int i = 0; i <= 3; i++) {
            String linie = event.getLine(i);

            linie = linie.replace("&", "§");
            linie = linie.replace("&", "§");
            event.setLine(i, linie);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.DARK_AQUA + " joined the server!");

        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Player" + " " + ChatColor.BLUE + p.getName() + " " + ChatColor.YELLOW + "logged in with gamemode CREATIVE");
        }
    }

    @EventHandler
    public void onFall(EntityDamageEvent e) {
        Player p = null;
        if (e.getEntity() instanceof Player) {
            p = (Player) e.getEntity();
        }

        if (plugin.isSpectator.contains(p)) {
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
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
            } else if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
                return;
            }
        }
    }

    //Stop the player from clicking the dispensor
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        if (e.getClickedBlock().getType().equals(Material.DISPENSER)) {
            e.setCancelled(true);
            return;
        } else {
            return;
        }
    }

    //Add the item shot back to the dispensor
    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (plugin.getConfig().getString("infinite").equalsIgnoreCase("false")) {
            return;
        }
        CraftDispenser dispenser = new CraftDispenser(e.getBlock());

        ItemStack newItemList = e.getItem().clone();
        dispenser.getInventory().addItem(new ItemStack[]{newItemList});
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        String motdstring = plugin.getConfig().getString("motd").replaceAll("(&([a-f0-9]))", "§$2");
        e.setMotd(motdstring);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.AQUA + e.getPlayer().getName() + ChatColor.RED + " left the server!");
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSnowForm(BlockFormEvent e) {
        if (e.getNewState().getData().getItemType() == Material.SNOW) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onTeamBreak(BlockBreakEvent e) {
        if (plugin.isRed.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
        if (plugin.isBlue.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onTeamPlace(BlockPlaceEvent e) {
        if (plugin.isRed.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
        if (plugin.isBlue.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onTeamInteract(PlayerInteractEvent e) {
        if (plugin.isRed.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
        if (plugin.isBlue.contains(e.getPlayer()) && (plugin.getConfig().get("inpregame").equals(true))) {
            e.setCancelled(true);
            return;
        }
    }
}
