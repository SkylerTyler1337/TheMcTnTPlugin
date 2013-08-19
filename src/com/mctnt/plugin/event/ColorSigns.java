/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author Harry5573
 */
public class ColorSigns implements Listener {
    
    @EventHandler
    public void Sign(SignChangeEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        BlockState sign = block.getState();
        for (int i = 0; i <= 3; i++) {
            String linie = event.getLine(i);

            linie = linie.replace("&", "§");
            linie = linie.replace("&", "§");
            event.setLine(i, linie);
        }
    }
}
