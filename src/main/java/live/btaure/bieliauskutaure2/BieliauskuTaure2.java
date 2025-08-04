package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Commands.*;
import live.btaure.bieliauskutaure2.Listeners.GlobalJoinListener;
import live.btaure.bieliauskutaure2.Listeners.GlobalWeatherChangeListener;
import live.btaure.bieliauskutaure2.Listeners.GlobalWorldLoadListener;
import live.btaure.bieliauskutaure2.Minigames.Lobby;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import live.btaure.bieliauskutaure2.Minigames.Parkour;
import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class BieliauskuTaure2 extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        registerSerialization();
        registerCommands();
        registerGlobalListeners();

        preloadWorlds();

        MinigameManager.getInstance();//ensures that lobby is loaded as a minigame

        Logger.getInstance().success("plugin uzsikrove");
    }

    private void preloadWorlds()
    {
        new Lobby();
        new Parkour();//sitas padaro kad static World uzsikrautu. cj nera butina nes anyway yra init faze nebent kad cia mobs isjungt
        for (World w : Bukkit.getServer().getWorlds())
        {
            w.setSpawnLimit(SpawnCategory.AMBIENT, 0);
            w.setSpawnLimit(SpawnCategory.ANIMAL, 0);
            w.setSpawnLimit(SpawnCategory.AXOLOTL, 0);
            w.setSpawnLimit(SpawnCategory.MONSTER, 0);
            w.setSpawnLimit(SpawnCategory.WATER_AMBIENT, 0);
            w.setSpawnLimit(SpawnCategory.WATER_ANIMAL, 0);
            w.setSpawnLimit(SpawnCategory.WATER_UNDERGROUND_CREATURE, 0);
        }
    }

    @Override
    public void onDisable()
    {
        Logger.getInstance().warning("plugin issikrove");
    }

    private void registerSerialization()
    {
        ConfigurationSerialization.registerClass(BTTeam.class, "BTTeam");
        ConfigurationSerialization.registerClass(Streamer.class, "Streamer");
        ConfigurationSerialization.registerClass(Spectator.class, "Spectator");
        ConfigurationSerialization.registerClass(Participant.class, "Participant");
        ConfigurationSerialization.registerClass(Administrator.class, "Administrator");
    }

    private void registerCommands()
    {
        Objects.requireNonNull(this.getCommand("team")).setExecutor(new TeamCommand());
        Objects.requireNonNull(this.getCommand("minigame")).setExecutor(new MinigameCommand());
        Objects.requireNonNull(this.getCommand("debuglevel")).setExecutor(new DebugLevelCommand());
        Objects.requireNonNull(this.getCommand("setrole")).setExecutor(new SetRoleCommand());

        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());
    }

    private void registerGlobalListeners()
    {
        getServer().getPluginManager().registerEvents(new GlobalJoinListener(), this);
        getServer().getPluginManager().registerEvents(new GlobalWeatherChangeListener(), this);
        getServer().getPluginManager().registerEvents(new GlobalWorldLoadListener(), this);
    }
}
