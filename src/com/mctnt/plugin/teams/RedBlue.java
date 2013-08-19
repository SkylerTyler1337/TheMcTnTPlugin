/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.teams;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

/**
 *
 * @author Harry5573
 */
public class RedBlue implements Listener {

    public static TheMcTnTPlugin plugin;

    public RedBlue(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void joinRed(Player p) {
        plugin.isRed.add(p);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.getOpenInventory().close();
        p.sendMessage(ChatColor.YELLOW + "You are now on the" + ChatColor.RED + " red " + ChatColor.YELLOW + "team!");
        plugin.smanager.removeSpectate(p);
        p.setDisplayName(ChatColor.DARK_RED + "[RED]" + p.getName());
        for (Player pl : Bukkit.getOnlinePlayers()) {
            TagAPI.refreshPlayer(pl);
        }
        //Teleport the player and give the items if the game HASNT started

        if ((plugin.getConfig().getString("incycle").equals("false") && (plugin.getConfig().getString("inpregame").equals("false")))) {
            p.getOpenInventory().close();
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setAllowFlight(false);
            p.setFlying(false);

            plugin.itemh.giveItems(p);

            Location redloc = null;
            String redstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.red");
            String[] spawnfinal1 = redstring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                redloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            p.teleport(redloc);
        }

    }

    public void joinBlue(Player p) {
        plugin.isBlue.add(p);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.getOpenInventory().close();
        p.sendMessage(ChatColor.YELLOW + "You are now on the" + ChatColor.BLUE + " blue " + ChatColor.YELLOW + "team!");
        plugin.smanager.removeSpectate(p);
        p.setDisplayName(ChatColor.DARK_AQUA + "[BLUE]" + p.getName());
        for (Player pl : Bukkit.getOnlinePlayers()) {
            TagAPI.refreshPlayer(pl);
        }
        //Teleport the player and give the items if the game HASNT started
        if ((plugin.getConfig().getString("incycle").equals("false") && (plugin.getConfig().getString("inpregame").equals("false")))) {
            p.getOpenInventory().close();
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setAllowFlight(false);
            p.setFlying(false);

            plugin.itemh.giveItems(p);

            Location blueloc = null;
            String bluestring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.blue");
            String[] spawnfinal1 = bluestring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                blueloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            p.teleport(blueloc);
        }
    }

    public void forceTpTeams(Player p) {
        p.setHealth(20);
        p.setFoodLevel(20);
        if (plugin.isRed.contains(p)) {
            p.getOpenInventory().close();
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setAllowFlight(false);
            p.setFlying(false);

            plugin.itemh.giveItems(p);

            Location redloc = null;
            String redstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.red");
            String[] spawnfinal1 = redstring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                redloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            p.teleport(redloc);
            return;
        } //If there blue
        else if (plugin.isBlue.contains(p)) {
            p.getOpenInventory().close();
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
            p.setAllowFlight(false);
            p.setFlying(false);

            plugin.itemh.giveItems(p);

            Location blueloc = null;
            String bluestring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.blue");
            String[] spawnfinal1 = bluestring.split(",");
            if (spawnfinal1.length == 3) {
                int x = Integer.parseInt(spawnfinal1[0]);
                int y = Integer.parseInt(spawnfinal1[1]);
                int z = Integer.parseInt(spawnfinal1[2]);
                blueloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
            }
            p.teleport(blueloc);
            return;
        }
        System.out.println("[TheMcTnTPlugin] Force teleported all players");
    }
}
