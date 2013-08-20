/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R2.block.CraftDispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Harry5573
 */
public class InfiniteDispensor implements Listener {

    public static TheMcTnTPlugin plugin;

    public InfiniteDispensor(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    
    //Stop the player from clicking the dispensor
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        } 
        if (e.getClickedBlock().getType().equals(Material.DISPENSER)) {
        e.setCancelled(true);
        return;
        } else {
            return;
        }
    }
    
    //Add the item shot back to the dispensor
    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (plugin.getConfig().getString("infinite").equalsIgnoreCase("false")) {
            return;
        }
        CraftDispenser dispenser = new CraftDispenser(e.getBlock());

        ItemStack newItemList = e.getItem().clone();
        dispenser.getInventory().addItem(new ItemStack[]{newItemList});
    }
}
