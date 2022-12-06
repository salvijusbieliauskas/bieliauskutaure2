package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Commands.TeamCommand;
import live.btaure.bieliauskutaure2.Listeners.GlobalJoinListener;
import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        Logger.getInstance().success("prisitriedziau ziauriai (plugin uzsikrove)");
    }
    @Override
    public void onDisable()
    {

    }
    private void registerSerialization()
    {
        ConfigurationSerialization.registerClass(BTTeam.class,"BTTeam");
        ConfigurationSerialization.registerClass(Spectator.class,"Spectator");
        ConfigurationSerialization.registerClass(Participant.class,"Participant");
        ConfigurationSerialization.registerClass(Administrator.class,"Administrator");
    }
    private void registerCommands()
    {
        this.getCommand("team").setExecutor(new TeamCommand());
    }
    private void registerGlobalListeners()
    {
        getServer().getPluginManager().registerEvents(new GlobalJoinListener(),this);
    }
}
