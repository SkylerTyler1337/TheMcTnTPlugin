/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

/**
 *
 * @author Harry5573
 */
public class SnowFix implements Listener {
    
    @EventHandler(priority= EventPriority.LOWEST)
    public void onSnowForm(BlockFormEvent e) {
        if (e.getNewState().getData().getItemType() == Material.SNOW) {
            e.setCancelled(true);
        }
    }
}
