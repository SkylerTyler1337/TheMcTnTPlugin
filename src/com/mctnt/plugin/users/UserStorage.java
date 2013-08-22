/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.users;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
            plugin.cfManager.getUsersFile().set("Users." + p.getName() + ".kills", 0);
            plugin.cfManager.getUsersFile().set("Users." + p.getName() + ".deaths", 0);
            plugin.cfManager.saveUsersFile();
        }
    }
    
    @EventHandler
    public void onStatsDeath(PlayerDeathEvent e) {
        
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        
        Player killed = e.getEntity();
        Player killer = killed.getKiller();
        
        if (killer == null) {
            return;
        }
  
        if (killed.getKiller() == killer) {
            
            int killerkills = plugin.cfManager.getUsersFile().getInt("Users." + killer.getName() + ".kills");
            int killernewkills = killerkills + 1;
            plugin.cfManager.getUsersFile().set("Users." + killer.getName() + ".kills", killernewkills);
            plugin.cfManager.saveUsersFile();
            
            int deaddeaths = plugin.cfManager.getUsersFile().getInt("Users." + killed.getName() + ".deaths");
            int deadnewdeaths = deaddeaths + 1;
            plugin.cfManager.getUsersFile().set("Users." + killed.getName() + ".deaths", deadnewdeaths);
            plugin.cfManager.saveUsersFile();
        }
        
    }
}
