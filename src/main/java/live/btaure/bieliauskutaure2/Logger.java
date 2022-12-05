package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Logger
{
    public static final String prefix = "[MCBieliauskuTaure2] ";
//TODO:send administrators who have enabled debug logging debug messages
    /**
     * Logs a warning to the server console (and administrators who have chosen to enable debug msesages), indicating that something could be wrong
     * @param message Message to log, containing informationa bout the warning
     */
    public static void warning(String message)
    {
        Bukkit.getLogger().log(Level.WARNING,prefix+message);
        if(PlayerManager.getInstance()!=null)
            PlayerManager.getInstance().broadcastDebug(ChatColor.YELLOW+prefix+message,2);
    }

    /**
     * Logs an error to the server console (and administrators who have chosen to enable debug msesages), indicating that something is wrong
     * @param message Message to log, containing information about the error
     */
    public static void error(String message)
    {
        Bukkit.getLogger().log(Level.SEVERE,prefix+message);
        if(PlayerManager.getInstance()!=null)
            PlayerManager.getInstance().broadcastDebug(ChatColor.DARK_RED+prefix+message,3);
    }

    /**
     * Logs information to the server console (and administrators who have chosen to enable debug msesages)
     * @param message Message to log
     */
    public static void info(String message)
    {
        Bukkit.getLogger().log(Level.INFO, prefix+message);
        if(PlayerManager.getInstance()!=null)
            PlayerManager.getInstance().broadcastDebug(prefix+message,1);
    }

    /**
     * Logs a successful check or action to the server console (and administrators who have chosen to enable debug msesages)
     * @param message Message to log
     */
    public static void success(String message)
    {
        Bukkit.getLogger().log(Level.INFO, ChatColor.GREEN+prefix+message);
        if(PlayerManager.getInstance()!=null)
            PlayerManager.getInstance().broadcastDebug(ChatColor.GREEN+prefix+message,0);
    }
}
