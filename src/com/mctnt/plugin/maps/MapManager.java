/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.maps;

import com.mctnt.plugin.core.TheMcTnTPlugin;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author Harry5573
 */
public class MapManager {

    public static TheMcTnTPlugin plugin;

    public MapManager(TheMcTnTPlugin instance) {
        this.plugin = instance;
    }

    public void copyMap(String worldname) throws IOException {
        System.out.println("[TheMcTnTPlugin] Copying world");

        File destination = new File(System.getProperty("user.dir") + File.separator + worldname + "backup");
        File source = new File(System.getProperty("user.dir") + File.separator + worldname);

        if (destination.exists()) {
            delete(destination);
            System.out.println("[TheMcTnTPlugin] Folder allready existed.... DELETED");
        }

        try {
            copyMethod(source, destination);
        } catch (IOException e) {
            System.out.println("[TheMcTnTPlugin] Error creating backup world");
        }
        System.out.println("[TheMcTnTPlugin] Created backup world");
    }

    private void copyMethod(File source, File destination) throws IOException {

        Files.copy(source.toPath(), destination.toPath());

        String[] files = source.list();

        if (files != null) {
            for (String file : files) {
                File sourceFile = new File(source, file);
                File destinationFile = new File(destination, file);

                copyMethod(sourceFile, destinationFile);
            }
        }
    }

    public void restoreMap(String worldname) throws IOException {
        System.out.println("[TheMcTnTPlugin] Restoring airshipbattle");

        World world = Bukkit.getWorld(worldname);
        
        File source = new File(System.getProperty("user.dir") + File.separator + worldname + "backup");
        File destination = new File(System.getProperty("user.dir") + File.separator + worldname);

        forceUnloadMap(worldname);

        try {
            restoreMethod(source, destination);
        } catch (IOException e) {
            System.out.println("[TheMcTnTPlugin] Error resetting map");
        }
        System.out.println("[TheMcTnTPlugin] Restored map");

            WorldCreator creator = new WorldCreator(worldname);
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(true);
            world = creator.createWorld();
        world.save();

        for (Player p : Bukkit.getOnlinePlayers()) {
            
        Location specloc = null;
        String speclocstring = (String) plugin.cfManager.getMapsFile().get("Maps." + plugin.getCurrentMap() + ".teamspawns.spectator");
        String[] spawnfinal1 = speclocstring.split(",");
        if (spawnfinal1.length == 3) {
            int x = Integer.parseInt(spawnfinal1[0]);
            int y = Integer.parseInt(spawnfinal1[1]);
            int z = Integer.parseInt(spawnfinal1[2]);
            specloc = new Location(plugin.getServer().getWorld(plugin.getCurrentMap()), x, y, z);
        }
        p.teleport(specloc, PlayerTeleportEvent.TeleportCause.COMMAND);
        }
    }

    private void restoreMethod(File source, File destination) throws IOException {
        //Copy and paste them
        Files.copy(source.toPath(), destination.toPath());

        String[] files = source.list();

        if (files != null) {
            for (String file : files) {
                File sourceFile = new File(source, file);
                File destinationFile = new File(destination, file);

                restoreMethod(sourceFile, destinationFile);
            }
        }
        
    }

    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }

    public void forceUnloadMap(String worldname) throws IOException {
        //Kick them from the map
        for (Player p : Bukkit.getOnlinePlayers()) {
        Location ploc = Bukkit.getServer().getWorld("world").getSpawnLocation();
        p.teleport(ploc);
        }
        
        World world = Bukkit.getWorld(worldname);
        
        //Unloadit
        Bukkit.unloadWorld(world, true);
        
        //Hmm still there
        if (Bukkit.getWorlds().contains(world)) {
            System.out.println("[TheMcTnTPlugin] World stayed after forceunload");
            Bukkit.getWorlds().remove(world);
        }


        File destination = new File(System.getProperty("user.dir") + File.separator + worldname);
        if (destination.exists()) {
            delete(destination);
            System.out.println("[TheMcTnTPlugin] World allready existed.... DELETED .. BOOM");
        }
    }
}
