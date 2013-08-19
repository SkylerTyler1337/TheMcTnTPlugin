/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.broadcast;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author Harry5573
 */
public class BroadcastManager implements Listener {

    public static TheMcTnTPlugin plugin;
    
    public BroadcastManager(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }
    
     public int counter = 0;

    public BukkitTask scheduleAnnouncerTask() {
        return plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            public void run() {
                broadcastAnnounce();
            }
        }, 20, 6000);
    }
    
    
        public void broadcastAnnounce() {
        if (this.counter >= plugin.cfManager.getBroadcastMessages().length) {
            this.counter = 0;
        }
        doAnnounce(this.counter);
        this.counter += 1;
    }

    public void doAnnounce(int index) {
        if (index < plugin.cfManager.getBroadcastMessages().length) {
            String endprefix = ChatColor.BLACK + "<" + ChatColor.YELLOW + "~" + ChatColor.BLACK + ">";

            plugin.getServer().broadcastMessage(colorize(plugin.cfManager.getBroadcastMessages()[index]));
        }
    }

    public static String colorize(String announce) {
        announce = announce.replaceAll("&0", ChatColor.BLACK.toString());
        announce = announce.replaceAll("&1", ChatColor.DARK_BLUE.toString());
        announce = announce.replaceAll("&2", ChatColor.DARK_GREEN.toString());
        announce = announce.replaceAll("&3", ChatColor.DARK_AQUA.toString());
        announce = announce.replaceAll("&4", ChatColor.DARK_RED.toString());
        announce = announce.replaceAll("&5", ChatColor.DARK_PURPLE.toString());
        announce = announce.replaceAll("&6", ChatColor.GOLD.toString());
        announce = announce.replaceAll("&7", ChatColor.GRAY.toString());
        announce = announce.replaceAll("&8", ChatColor.DARK_GRAY.toString());
        announce = announce.replaceAll("&9", ChatColor.BLUE.toString());
        announce = announce.replaceAll("&a", ChatColor.GREEN.toString());
        announce = announce.replaceAll("&b", ChatColor.AQUA.toString());
        announce = announce.replaceAll("&c", ChatColor.RED.toString());
        announce = announce.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString());
        announce = announce.replaceAll("&e", ChatColor.YELLOW.toString());
        announce = announce.replaceAll("&f", ChatColor.WHITE.toString());
        announce = announce.replaceAll("&k", ChatColor.MAGIC.toString());
        announce = announce.replaceAll("&l", ChatColor.BOLD.toString());
        announce = announce.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
        announce = announce.replaceAll("&n", ChatColor.UNDERLINE.toString());
        announce = announce.replaceAll("&o", ChatColor.ITALIC.toString());
        announce = announce.replaceAll("&r", ChatColor.RESET.toString());

        announce = announce.replaceAll("%playerList", plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers());
        announce = announce.replaceAll("%serverVersion", plugin.getServer().getVersion());

        return announce;
    }
}
