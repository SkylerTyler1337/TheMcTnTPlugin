/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Harry5573
 */
public class ItemHandler implements Listener {

    public static TheMcTnTPlugin plugin;

    public ItemHandler(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void giveItems(Player p) {
        for (String itemlist : plugin.cfManager.getMapsFile().getStringList("Maps." + plugin.getCurrentMap() + ".items")) {
            String[] items = itemlist.split(" ");
            if (items.length == 3) {
                Integer item = Integer.valueOf(Integer.parseInt(items[0]));
                Integer shorter = Integer.valueOf(Integer.parseInt(items[1]));
                Integer amount = Integer.valueOf(Integer.parseInt(items[2]));
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.getMaterial(item.intValue()), amount.intValue(), (short) ((short) 0 + shorter))});
            } 
            else if (items.length == 5) {
                Integer item = Integer.valueOf(Integer.parseInt(items[0]));
                Integer shorter = Integer.valueOf(Integer.parseInt(items[1]));
                Integer amount = Integer.valueOf(Integer.parseInt(items[2]));
                
                Enchantment enchant = new EnchantmentWrapper(Integer.parseInt(items[3]));         
                Integer power = Integer.valueOf(Integer.parseInt(items[4]));
                
                ItemStack i = new ItemStack(Material.getMaterial(item.intValue()), amount.intValue(), (short) ((short) 0 + shorter));
                
                i.addUnsafeEnchantment(enchant, power.intValue());
                
                p.getInventory().addItem(new ItemStack[]{i});
            } else {
                Integer item = Integer.valueOf(Integer.parseInt(items[0]));
                p.getInventory().addItem(new ItemStack[]{new ItemStack(Material.getMaterial(item.intValue()))});
            }
        }
    }
}