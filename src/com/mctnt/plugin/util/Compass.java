/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Harry5573
 */
public class Compass implements Listener {
    
    public static TheMcTnTPlugin plugin;
    
    @EventHandler(priority= EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().getType().equals(Material.COMPASS)) {
            e.setCancelled(true);
        }
    }

    
    @EventHandler(priority= EventPriority.LOWEST)
    public void onCompassUse(PlayerInteractEvent e) {
        
        Player p = e.getPlayer();
        
        if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) 
                || e.getAction().equals(Action.RIGHT_CLICK_BLOCK) 
                || e.getAction().equals(Action.LEFT_CLICK_AIR) 
                || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) 
                && (p.getItemInHand().getType().equals(Material.COMPASS))) {
            
            float pitch = p.getLocation().getPitch();
            float yaw = p.getLocation().getYaw();
    
            //Get the block there teleporting to
            TargetBlock ptbb = new TargetBlock(p);
            Block theTargetBlock = ptbb.getTargetBlock();

            if (theTargetBlock != null) {
                for (int i = 0; i < 100; i++) {
                    int landingType = p.getWorld().getBlockTypeIdAt(theTargetBlock.getX(), theTargetBlock.getY() + i, theTargetBlock.getZ());
                    int landingAbove = p.getWorld().getBlockTypeIdAt(theTargetBlock.getX(), theTargetBlock.getY() + i + 1, theTargetBlock.getZ());

                    if ((landingType == 0) && (landingAbove == 0)) {
                        Location theLoc = theTargetBlock.getLocation();

                        theLoc.setX(theLoc.getX() + 0.5D);
                        theLoc.setZ(theLoc.getZ() + 0.5D);
                        theLoc.setY(theLoc.getY() + i);
                        theLoc.setPitch(pitch);
                        theLoc.setYaw(yaw);
                        p.teleport(theLoc);
                        break;
                    }
                }
            }

        }
    }
}
