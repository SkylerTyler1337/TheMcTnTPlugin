/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.users;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Harry5573
 */
public class UserStorage implements Listener {

    public static TheMcTnTPlugin plugin;

    public UserStorage(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!plugin.cfManager.getUsersFile().contains("Users." + p.getName())) {
            System.out.println("[TheMcTnTPlugin] No user data found for " + p.getName() + " creating...");
            plugin.cfManager.getUsersFile().set("Users." + p.getName(), "");
            plugin.cfManager.saveUsersFile();
        }
    }
}
