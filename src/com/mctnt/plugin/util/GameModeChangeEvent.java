/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Harry5573
 */
public class GameModeChangeEvent implements Listener {
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Player" + " " + ChatColor.BLUE + p.getName() + " " + ChatColor.YELLOW + "logged in with gamemode CREATIVE");
        }
    }

}
