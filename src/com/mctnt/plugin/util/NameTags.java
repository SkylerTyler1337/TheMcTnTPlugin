/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

/**
 *
 * @author Harry5573
 */
public class NameTags implements Listener {

    public static TheMcTnTPlugin plugin;

    public NameTags(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
   @EventHandler(priority= EventPriority.LOWEST)
   public void onNameTag(PlayerReceiveNameTagEvent e) {
       //If there a spectator
       if (plugin.isSpectator.contains(e.getNamedPlayer())) {
           e.setTag(ChatColor.GREEN + e.getNamedPlayer().getName());
           return;
       }
       //If there red
       if (plugin.isRed.contains(e.getNamedPlayer())) {
           e.setTag(ChatColor.DARK_RED + e.getNamedPlayer().getName());
           return;
       }
       //If there blue
       if (plugin.isBlue.contains(e.getNamedPlayer())) {
           e.setTag(ChatColor.BLUE + e.getNamedPlayer().getName());
           return;
       }
   }
    
    
    
}
