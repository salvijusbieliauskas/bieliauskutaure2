package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Commands.DebugLevelCommand;
import live.btaure.bieliauskutaure2.Commands.MinigameCommand;
import live.btaure.bieliauskutaure2.Commands.TeamCommand;
import live.btaure.bieliauskutaure2.Commands.TestCommand;
import live.btaure.bieliauskutaure2.Listeners.GlobalJoinListener;
import live.btaure.bieliauskutaure2.Listeners.GlobalWeatherChangeListener;
import live.btaure.bieliauskutaure2.Listeners.GlobalWorldLoadListener;
import live.btaure.bieliauskutaure2.Minigames.Lobby;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import live.btaure.bieliauskutaure2.Participants.*;
import live.btaure.bieliauskutaure2.Minigames.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

public class BieliauskuTaure2 extends JavaPlugin {
    private ConfigManager configManager;
    private PlayerManager playerManager;
    @Override
    public void onEnable()
    {
        registerSerialization();
        registerCommands();
        registerGlobalListeners();

        preloadWorlds();

        MinigameManager.getInstance();//ensures that lobby is loaded as a minigame

        Logger.getInstance().success("prisitriedziau ziauriai (plugin uzsikrove)");
    }
    private void preloadWorlds()
    {
        new Lobby();
        new Parkour();//sitas sudas padaro kad static World uzsikrautu. cj nera butina nes anyway yra init faze nebent kad cia mobs isjungt
        for(World w : Bukkit.getServer().getWorlds())
        {
            w.setAmbientSpawnLimit(0);
            w.setMonsterSpawnLimit(0);
            w.setWaterAmbientSpawnLimit(0);
            w.setWaterAnimalSpawnLimit(0);
        }
    }
    @Override
    public void onDisable()
    {
        Logger.getInstance().warning("plugin issikrove");
    }
    private void registerSerialization()
    {
        ConfigurationSerialization.registerClass(BTTeam.class,"BTTeam");
        ConfigurationSerialization.registerClass(Streamer.class,"Streamer");
        ConfigurationSerialization.registerClass(Spectator.class,"Spectator");
        ConfigurationSerialization.registerClass(Participant.class,"Participant");
        ConfigurationSerialization.registerClass(Administrator.class,"Administrator");
    }
    private void registerCommands()
    {
        this.getCommand("team").setExecutor(new TeamCommand());
        this.getCommand("minigame").setExecutor(new MinigameCommand());
        this.getCommand("debuglevel").setExecutor(new DebugLevelCommand());

        this.getCommand("test").setExecutor(new TestCommand());
    }
    private void registerGlobalListeners()
    {
        getServer().getPluginManager().registerEvents(new GlobalJoinListener(),this);
        getServer().getPluginManager().registerEvents(new GlobalWeatherChangeListener(),this);
        getServer().getPluginManager().registerEvents(new GlobalWorldLoadListener(),this);
    }
}
