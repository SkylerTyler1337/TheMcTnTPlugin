/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Harry5573
 */
public class ConfigurationManager {

    public static TheMcTnTPlugin plugin;
    private FileConfiguration broadcastmessages = null;
    private File broadcastmessagesfile = null;
    private FileConfiguration users = null;
    private File usersfile = null;
    private FileConfiguration maps = null;
    private File mapsfile = null;

    public ConfigurationManager(TheMcTnTPlugin instance) {
        plugin = instance;
    }

    public void loadConfigs() throws IOException {
        //Auto broadcast messages
        if (broadcastmessages == null) {
            broadcastmessagesfile = new File(plugin.getDataFolder(), "broadcastmessages.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded broadcastmessages.yml");

        if (!broadcastmessagesfile.exists()) {
            System.out.println("[TheMcTnTPlugin] broadcastmessages.yml did not exist creating..");
            this.plugin.saveResource("broadcastmessages.yml", true);
            broadcastmessages = YamlConfiguration.loadConfiguration(broadcastmessagesfile);
            broadcastmessages.save(broadcastmessagesfile);
        }
        broadcastmessages = YamlConfiguration.loadConfiguration(broadcastmessagesfile);


        //Users yml
        if (users == null) {
            usersfile = new File(plugin.getDataFolder(), "users.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded users.yml");

        if (!usersfile.exists()) {
            System.out.println("[TheMcTnTPlugin] users.yml did not exist creating..");
            this.plugin.saveResource("users.yml", true);
            users = YamlConfiguration.loadConfiguration(usersfile);
            users.save(usersfile);
        }
        users = YamlConfiguration.loadConfiguration(usersfile);
        
        
        
        
        //Maps
        if (maps == null) {
            mapsfile = new File(plugin.getDataFolder(), "maps.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded maps.yml");

        if (!mapsfile.exists()) {
            System.out.println("[TheMcTnTPlugin] maps.yml did not exist creating..");
            this.plugin.saveResource("maps.yml", true);
            maps = YamlConfiguration.loadConfiguration(mapsfile);
            maps.save(mapsfile);
        }
        maps = YamlConfiguration.loadConfiguration(mapsfile);


    }

    //
    //Broadcast File
    //
    public FileConfiguration getBroadcastFile() {
        return broadcastmessages;
    }

    public void reloadBroadcastFile() {
        if (broadcastmessages == null) {
            broadcastmessagesfile = new File(plugin.getDataFolder(), "broadcastmessages.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded broadcastmessages.yml");
        broadcastmessages = YamlConfiguration.loadConfiguration(broadcastmessagesfile);
    }

    public void saveBroadcastFile() {
        try {
            broadcastmessages.save(broadcastmessagesfile);
        } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error while saving broadcast message");
        }
    }

    public String[] getBroadcastMessages() {
        return Arrays.copyOf(broadcastmessages.getList("announcements").toArray(), broadcastmessages.getList("announcements").toArray().length, String[].class);
    }

    //
    //Users File
    //
    public FileConfiguration getUsersFile() {
        return users;
    }

    public void reloadUsersFile() {
        if (users == null) {
            usersfile = new File(plugin.getDataFolder(), "users.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded users.yml");
        users = YamlConfiguration.loadConfiguration(usersfile);
    }

    public void saveUsersFile() {
        try {
            users.save(usersfile);
        } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error while saving users.yml");
        }
    }
    
    //
    //Maps file
    //
    public FileConfiguration getMapsFile() {
        return maps;
    }

    public void reloadMapsFile() {
        if (maps == null) {
            mapsfile = new File(plugin.getDataFolder(), "maps.yml");
        }
        System.out.println("[TheMcTnTPlugin] Loaded maps.yml");
        maps = YamlConfiguration.loadConfiguration(mapsfile);
    }

    public void saveMapsFile() {
        try {
            maps.save(mapsfile);
        } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error while saving maps.yml");
        }
    }
}