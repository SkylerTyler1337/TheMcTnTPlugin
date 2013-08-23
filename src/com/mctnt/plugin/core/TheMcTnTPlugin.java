/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mctnt.plugin.core;

import com.mctnt.countdown.PreGame;
import com.mctnt.plugin.broadcast.AnnounceCommand;
import com.mctnt.plugin.broadcast.BroadcastCommand;
import com.mctnt.plugin.broadcast.BroadcastManager;
import com.mctnt.plugin.command.CommandCycle;
import com.mctnt.plugin.command.CommandJoin;
import com.mctnt.plugin.command.CommandMap;
import com.mctnt.plugin.command.CommandMcTNT;
import com.mctnt.plugin.command.CommandSethome;
import com.mctnt.plugin.command.CommandStats;
import com.mctnt.plugin.gamemodes.CaptureTheCore;
import com.mctnt.plugin.maps.MapManager;
import com.mctnt.plugin.restart.RestartCommand;
import com.mctnt.plugin.teams.AntiTeamkill;
import com.mctnt.plugin.teams.RandomTeam;
import com.mctnt.plugin.teams.RedBlue;
import com.mctnt.plugin.teams.RespawnListener;
import com.mctnt.plugin.teams.Spectate;
import com.mctnt.plugin.users.UserStorage;
import com.mctnt.plugin.teams.AntiBuildInBase;
import static com.mctnt.plugin.teams.AntiBuildInBase.GLOBAL_BUILD;
import static com.mctnt.plugin.gamemodes.CaptureTheCore.RED_CORE;
import static com.mctnt.plugin.gamemodes.CaptureTheCore.BLUE_CORE;
import com.mctnt.plugin.movement.Compass;
import com.mctnt.plugin.mysql.MySQL;
import com.mctnt.plugin.teams.ItemHandler;
import com.mctnt.plugin.util.BetterTnT;
import com.mctnt.plugin.util.EventListener;
import com.mctnt.plugin.util.NameTags;
import com.mctnt.plugin.util.TeamSelector;
import com.mctnt.plugin.util.WorldGuardUtils;
import com.mewin.WGCustomFlags.WGCustomFlagsPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class TheMcTnTPlugin extends JavaPlugin {
    
    public static TheMcTnTPlugin plugin;
    
    public static PluginDescriptionFile pdfFile = null;
    
    public int mapcounter = 0;
    
    public BroadcastManager bfm;
    public ConfigurationManager cfManager;
    public MapManager mmanager;
    public PreGame pgc;
    public TeamSelector ts;
    public Spectate smanager;
    public RedBlue rb;
    public RandomTeam randomteam;
    public ItemHandler itemhandler;
    public CommandCycle cc;
    public CaptureTheCore ctc;
    
    //3 Lists For Teams
    public ArrayList<Player> isSpectator = new ArrayList<Player>();
    public ArrayList<Player> isRed = new ArrayList<Player>();
    public ArrayList<Player> isBlue = new ArrayList<Player>();
   
    //Admin edit mode
    public List<String> isAdminMode = new ArrayList<String>();
    
    //Custom worldguard flags
    private WGCustomFlagsPlugin pluginWGCustomFlags;
    private WorldGuardPlugin pluginWorldGuard;
    
    MySQL MySQL = new MySQL("localhost", "3306", "mctnt", "harry5573", "");
    public Connection c = null;

    @Override
    public void onEnable() {
        plugin = this;
     
        
        this.bfm = new BroadcastManager(this);
        this.cfManager = new ConfigurationManager(this);
        this.mmanager = new MapManager(this);
        this.ts = new TeamSelector(this);
        this.smanager = new Spectate(this);
        this.rb = new RedBlue(this);
        this.randomteam = new RandomTeam(this);
        this.itemhandler = new ItemHandler(this);
        this.cc = new CommandCycle(this);
        this.ctc = new CaptureTheCore(this);

        //Kick everyone on reload/start if they somehow get in :)
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.kickPlayer(ChatColor.DARK_RED + "" + ChatColor.BOLD + "*** SERVER RESTARTING ***");
        }
        
        //Clear all of the arraylists
        isSpectator.clear();
        isRed.clear();
        isBlue.clear();
        isAdminMode.clear();
        
        pdfFile = plugin.getDescription();
       
        getServer().getPluginManager().registerEvents(new Compass(), this);
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandSethome(this), this);
        getServer().getPluginManager().registerEvents(new AntiBuildInBase(this), this);
        getServer().getPluginManager().registerEvents(new Spectate(this), this);
        getServer().getPluginManager().registerEvents(new TeamSelector(this), this);
        getServer().getPluginManager().registerEvents(new RedBlue(this), this);
        getServer().getPluginManager().registerEvents(new RandomTeam(this), this);
        getServer().getPluginManager().registerEvents(new NameTags(this), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new AntiTeamkill(this), this);
        getServer().getPluginManager().registerEvents(new UserStorage(this), this);
        getServer().getPluginManager().registerEvents(new CaptureTheCore(this), this);
        getServer().getPluginManager().registerEvents(new BetterTnT(this), this);
        
        this.getCommand("announce").setExecutor(new AnnounceCommand(this));
        this.getCommand("broadcast").setExecutor(new BroadcastCommand(this));
        this.getCommand("bcast").setExecutor(new BroadcastCommand(this));
        this.getCommand("tnt").setExecutor(new CommandMcTNT(this));
        this.getCommand("mctnt").setExecutor(new CommandMcTNT(this));
        this.getCommand("sethome").setExecutor(new CommandSethome(this));
        this.getCommand("home").setExecutor(new CommandSethome(this));
        this.getCommand("map").setExecutor(new CommandMap(this));
        this.getCommand("reboot").setExecutor(new RestartCommand(this));
        this.getCommand("join").setExecutor(new CommandJoin(this));
        this.getCommand("cycle").setExecutor(new CommandCycle(this));
        this.getCommand("stats").setExecutor(new CommandStats(this));

        this.pluginWorldGuard = WorldGuardUtils.getWorldGuard(this);
        this.pluginWGCustomFlags = WorldGuardUtils.getWGCustomFlags(this);

        getWGCFP().addCustomFlag(GLOBAL_BUILD);
        getWGCFP().addCustomFlag(RED_CORE);
        getWGCFP().addCustomFlag(BLUE_CORE);

        saveDefaultConfig();
        try {
            cfManager.loadConfigs();
        } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error loading configs :(");
        }
        
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled");
        
        bfm.scheduleAnnouncerTask();
        
        //Generate maps
        List<String> maps = plugin.getConfig().getStringList("maps");
        for (String sl : maps) {
            genMaps(sl);
        }
        
        //Safespot
        Plugin wg = plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg == null) {
            System.out.println("[TheMcTnTPlugin] Worldguard not found....");
            System.out.println("[TheMcTnTPlugin] Plugin Disabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        Plugin tagapi = plugin.getServer().getPluginManager().getPlugin("TagAPI");
        if (tagapi == null) {
            System.out.println("[TheMcTnTPlugin] TagAPI not found....");
            System.out.println("[TheMcTnTPlugin] Plugin Disabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        Plugin worldedit = plugin.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit == null) {
            System.out.println("[TheMcTnTPlugin] WorldEdit not found....");
            System.out.println("[TheMcTnTPlugin] Plugin Disabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        Plugin cflags = plugin.getServer().getPluginManager().getPlugin("WGCustomFlags");
        if (cflags == null) {
            System.out.println("[TheMcTnTPlugin] World guard custom flags not found....");
            System.out.println("[TheMcTnTPlugin] Plugin Disabled");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        //Before we set the configs to default lets again regen the oldmap incase of "CRASH"
        try {
         plugin.mmanager.restoreMap(plugin.getCurrentMap());
            System.out.println("[TheMcTnTPlugin] Regenerated map");
       } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error while regenerating map");
        }

        //Set the configs to "Defaults"
        getConfig().set("currentmap", "airshipbattle");
        getConfig().set("inpregame", true);
        getConfig().set("incycle", false);
        saveConfig();
        
        //
        //MySQL
        //
        c = MySQL.open();
    
        //Start le game
        new PreGame(plugin, 30).runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void onDisable() {
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been disabled");
        try {
            plugin.mmanager.restoreMap(plugin.getCurrentMap());
            System.out.println("[TheMcTnTPlugin] Regenerated map");
        } catch (IOException ex) {
            System.out.println("[TheMcTnTPlugin] Error while regenerating map");
        }
    }

    public void genMaps(String worldname) {
        World world = Bukkit.getWorld(worldname);
        if (world == null) {
            WorldCreator creator = new WorldCreator(worldname);
            creator.environment(World.Environment.NORMAL);
            creator.generateStructures(true);
            world = creator.createWorld();
            world.save();
        }
    }
    
    public String getCurrentMap() {
        return this.getConfig().getString("currentmap");
    }

    public WGCustomFlagsPlugin getWGCFP() {
        return this.pluginWGCustomFlags;
    }

    public WorldGuardPlugin getWGP() {
        return this.pluginWorldGuard;
    }
    
    //Get list of maps from the configuration 
    public String[] getMaps() {
        return Arrays.copyOf(getConfig().getList("maps").toArray(), getConfig().getList("maps").toArray().length, String[].class);
    }
 
    //
    //Cycle the maps BETA 
    //
    public void cycleMaps() {
        if (this.mapcounter >= getMaps().length) {
            this.mapcounter = 0;
        }
        getConfig().set("cycletomap", getMaps()[this.mapcounter]);
        saveConfig();
        System.out.println("[TheMcTnTPlugin] Cycling to " + getConfig().getString("cycletomap"));
        
        //Add 1
        this.mapcounter += 1;
    }
 
}
