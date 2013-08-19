/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 *
 * @author Harry5573
 */
public class MotdPing implements Listener {

    public static TheMcTnTPlugin plugin;

    public MotdPing(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        String motdstring = plugin.getConfig().getString("motd").replaceAll("(&([a-f0-9]))", "§$2");
        e.setMotd(motdstring);
    }
}
