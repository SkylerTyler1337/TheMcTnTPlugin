/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 *
 * @author Harry5573
 */
public class ChunkGeneration implements Listener {

    public static TheMcTnTPlugin plugin;

    public ChunkGeneration(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onNewChunk(ChunkLoadEvent e) {
        if (e.isNewChunk() && plugin.getConfig().getString("chunkgenoff").equals("true")) {
            e.getChunk().unload(true);
        }
    }
}
