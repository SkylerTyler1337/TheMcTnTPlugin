/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.util;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Harry5573
 */
public class TeamSelector implements Listener {

    public static TheMcTnTPlugin plugin;

    public TeamSelector(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void addSelector(Player p) {

        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
        ItemMeta bookmeta = book.getItemMeta();

        List booklore = new ArrayList();
        booklore.add(ChatColor.DARK_PURPLE + "" + ChatColor.ITALIC + "Use this to select your team!");

        bookmeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Team Selector");

        bookmeta.setLore(booklore);

        book.setItemMeta(bookmeta);

        p.getInventory().setItem(0, book);
    }

    //Create the menu
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.getItemInHand() == null) {
            return;
        }
        

        if (e.getAction() == null) {
            return;
        }

        if (!p.getItemInHand().hasItemMeta()) {
            return;
        }

        if (!p.getItemInHand().getItemMeta().hasDisplayName()) {
            return;
        }

        if ((p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.BOLD + "Team Selector")) && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))) {
            
            if ((plugin.getConfig().getString("incycle").equals("true"))) {
                p.sendMessage(ChatColor.RED + "You may not do that while the game is cycling");
                return;
            } 
            Inventory selectormenu = Bukkit.createInventory(null, 9, ChatColor.DARK_RED + "Pick a team");

            ItemStack random = new ItemStack(Material.NETHER_STAR, 1);
            ItemStack red = new ItemStack(Material.STAINED_CLAY, plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".max-players"), (short) 14);
            ItemStack blue = new ItemStack(Material.STAINED_CLAY, plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".max-players"), (short) 11);


            ItemMeta randommeta = random.getItemMeta();
            ItemMeta redmeta = red.getItemMeta();
            ItemMeta bluemeta = blue.getItemMeta();

            List randomlore = new ArrayList();
            List redlore = new ArrayList();
            List bluelore = new ArrayList();

            int rednumber = plugin.isRed.size();
            int bluenumber = plugin.isBlue.size();

            randomlore.add(ChatColor.RED + "" + rednumber + ChatColor.GOLD + " / " + ChatColor.BLUE + bluenumber);
            randomlore.add(ChatColor.AQUA + "Puts you on the team with the least players");

            redlore.add(ChatColor.RED + "" + rednumber + ChatColor.GOLD + " / " + ChatColor.BLUE + bluenumber);
            redlore.add(ChatColor.AQUA + "Puts you on the red team " + ChatColor.GREEN + "" + ChatColor.BOLD + "(Premium ONLY)");

            bluelore.add(ChatColor.BLUE + "" + bluenumber + ChatColor.GOLD + " / " + ChatColor.RED + "" + rednumber);
            bluelore.add(ChatColor.AQUA + "Puts you on the blue team " + ChatColor.GREEN + "" + ChatColor.BOLD + "(Premium ONLY)");

            randommeta.setLore(randomlore);
            redmeta.setLore(redlore);
            bluemeta.setLore(bluelore);

            randommeta.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Random Team");
            redmeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Red Team");
            bluemeta.setDisplayName(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blue Team");

            random.setItemMeta(randommeta);
            red.setItemMeta(redmeta);
            blue.setItemMeta(bluemeta);

            selectormenu.addItem(random, red, blue);

            ItemStack close = new ItemStack(Material.EYE_OF_ENDER, 1);
            ItemMeta closemeta = close.getItemMeta();
            closemeta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Close This Menu");
            close.setItemMeta(closemeta);

            selectormenu.setItem(8, close);

            e.getPlayer().openInventory(selectormenu);
        } else {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getCurrentItem() == null) {
            return;
        }

        int maxperteam = plugin.cfManager.getMapsFile().getInt("Maps." + plugin.getCurrentMap() + ".max-players");

        if (p.getOpenInventory().getTitle().equals(ChatColor.DARK_RED + "Pick a team")) {

            if (plugin.isRed.contains(p) || plugin.isBlue.contains(p)) {
                p.sendMessage(ChatColor.RED + "You are already on a team");
                e.setCancelled(true);
                p.updateInventory();
                return;
            }


            if (e.getCurrentItem().getType() == Material.EYE_OF_ENDER) {
                p.getOpenInventory().close();
            }

            if (e.getCurrentItem().getType() == Material.NETHER_STAR) {
                plugin.randomteam.randomTeam(p);

                e.setCancelled(true);
                p.updateInventory();
            }
            

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "" + ChatColor.BOLD + "Red Team")) {
                if (!p.hasPermission("mctnt.premium")) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU MUST BE PREMIUM TO DO THAT!");
                    e.setCancelled(true);
                    p.updateInventory();
                    return;
                }

                if (plugin.isRed.size() == maxperteam) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "TEAM FULL");
                    e.setCancelled(true);
                    p.updateInventory();
                    return;
                }
                plugin.rb.joinRed(p);

                e.setCancelled(true);
                p.updateInventory();
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Blue Team")) {
                if (!p.hasPermission("mctnt.premium")) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "YOU MUST BE PREMIUM TO DO THAT!");
                    e.setCancelled(true);
                    p.updateInventory();
                    return;
                }
                if (plugin.isBlue.size() == maxperteam) {
                    p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "TEAM FULL");
                    e.setCancelled(true);
                    p.updateInventory();
                    return;
                }
                plugin.rb.joinBlue(p);

                e.setCancelled(true);
                p.updateInventory();
            } else if (p.getOpenInventory().getTitle().equals(ChatColor.DARK_RED + "Pick a team")) {
                e.setCancelled(true);
                p.updateInventory();
            }
        }
    }
}
