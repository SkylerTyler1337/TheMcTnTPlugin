/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.api;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author Harry5573
 */
public class TeamWinEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Plugin plugin = null;
    private String team = null;

    public TeamWinEvent(Plugin plugin, String team) {
        this.team = team;
        this.plugin = plugin;
    }

    public String getTeam() {
        return this.team;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
