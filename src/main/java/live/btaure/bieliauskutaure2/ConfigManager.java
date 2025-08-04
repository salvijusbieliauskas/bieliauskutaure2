package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.BTTeam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfigManager
{
    private static ConfigManager configManagerInstance = null;
    private final BieliauskuTaure2 plugin;

    private ConfigManager()
    {
        plugin = BieliauskuTaure2.getPlugin(BieliauskuTaure2.class);
    }

    public static ConfigManager getInstance()
    {
        if (configManagerInstance == null)
        {
            configManagerInstance = new ConfigManager();
        }
        return configManagerInstance;
    }

    public List<BTTeam> getTeams() //TODO:isitikinti kad sitas(ir kitas get) ass metodas nethrowins exception
    {
        Object obj = plugin.getConfig().get("teams");
        if (obj == null)
            return new ArrayList<BTTeam>();
        return (List<BTTeam>) plugin.getConfig().get("teams");
    }

    public void setTeams(Collection<BTTeam> teams)//TODO:sita dalis ziauriai neoptimized nes kiekviena karta kai kazkurio player type bus pakeistas, kiekvienas player bus serialized per nauja
    {
        plugin.getConfig().set("teams", new ArrayList<BTTeam>(teams));
        save();
    }

    public void save()
    {
        plugin.saveConfig();
        Logger.getInstance().info("Configuration updated and saved");
    }

    public List<BTPlayer> getBTPlayers()
    {
        Object obj = plugin.getConfig().get("BTPlayers");
        if (obj == null)
            return new ArrayList<BTPlayer>();
        return (List<BTPlayer>) obj;
    }

    public void setNonParticipants(Collection<BTPlayer> BTPlayers)
    {
        plugin.getConfig().set("BTPlayers", new ArrayList<BTPlayer>(BTPlayers));
        save();
    }

    public void reload()
    {
        plugin.reloadConfig();
        Logger.getInstance().info("Configuration file reloaded");
    }
}