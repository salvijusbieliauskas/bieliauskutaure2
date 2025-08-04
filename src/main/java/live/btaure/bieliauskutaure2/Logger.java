package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class Logger
{
    private static Logger logger = null;
    public String prefix;

    private Logger()
    {
        prefix = "[MCBieliauskuTaure2] ";
    }

    public static Logger getInstance()
    {
        if (logger == null)
            logger = new Logger();
        return logger;
    }

    /**
     * Logs a warning to the server console (and administrators who have chosen to enable debug msesages), indicating that something could be wrong
     *
     * @param message Message to log, containing informationa bout the warning
     */
    public void warning(String message)
    {
        Bukkit.getLogger().log(Level.WARNING, prefix + message);
        if (PlayerManager.getInstance() != null)
            PlayerManager.getInstance().broadcastDebug(ChatColor.YELLOW + prefix + message, 2);
    }

    /**
     * Logs an error to the server console (and administrators who have chosen to enable debug msesages), indicating that something is wrong
     *
     * @param message Message to log, containing information about the error
     */
    public void error(String message)
    {
        Bukkit.getLogger().log(Level.SEVERE, prefix + message);
        if (PlayerManager.getInstance() != null)
            PlayerManager.getInstance().broadcastDebug(ChatColor.DARK_RED + prefix + message, 3);
    }

    /**
     * Logs information to the server console (and administrators who have chosen to enable debug msesages)
     *
     * @param message Message to log
     */
    public void info(String message)
    {
        Bukkit.getLogger().log(Level.INFO, prefix + message);
        PlayerManager.getInstance().broadcastDebug(prefix + message, 1);
    }

    /**
     * Logs a successful check or action to the server console (and administrators who have chosen to enable debug msesages)
     *
     * @param message Message to log
     */
    public void success(String message)
    {
        Bukkit.getLogger().log(Level.INFO, prefix + message);
        PlayerManager.getInstance().broadcastDebug(ChatColor.GREEN + prefix + message, 0);
    }
}