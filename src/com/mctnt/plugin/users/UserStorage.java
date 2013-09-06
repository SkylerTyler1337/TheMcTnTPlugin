/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.users;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.sql.SQLException;
import java.sql.Statement;
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
        
        if (!plugin.getConfig().getString("usemysql").equals("true")) {
            return;
        } else {

        try {
            final Statement statement = plugin.c.createStatement();
            String name = e.getPlayer().getName();
            statement.executeUpdate("INSERT INTO `mctntstats`.`playerstats` (`playername`, `kills`, `deaths`) VALUES ('" + name + "', '0', '0');");
        } catch (SQLException ex) {
            System.out.println("[TheMcTnTPlugin] Could not create SQL statement");
        }
        }
    }
    }

    @EventHandler
    public void onStatsDeath(PlayerDeathEvent e) {

        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player killed = e.getEntity();
        Player killer = killed.getKiller();

        int deaddeaths = plugin.cfManager.getUsersFile().getInt("Users." + killed.getName() + ".deaths");
        int deadnewdeaths = deaddeaths + 1;
        plugin.cfManager.getUsersFile().set("Users." + killed.getName() + ".deaths", deadnewdeaths);
        plugin.cfManager.saveUsersFile();

        if (killer == null) {
            return;
        }
            int killerkills = plugin.cfManager.getUsersFile().getInt("Users." + killer.getName() + ".kills");
            int killernewkills = killerkills + 1;
            plugin.cfManager.getUsersFile().set("Users." + killer.getName() + ".kills", killernewkills);
            plugin.cfManager.saveUsersFile();


        }
}
