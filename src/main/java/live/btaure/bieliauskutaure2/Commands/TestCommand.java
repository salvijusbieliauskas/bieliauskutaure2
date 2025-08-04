package live.btaure.bieliauskutaure2.Commands;

import live.btaure.bieliauskutaure2.Chat.ChatMessageManager;
import live.btaure.bieliauskutaure2.Chat.ChatPattern;
import live.btaure.bieliauskutaure2.SoundManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if (strings.length == 0)
            return true;
        if (strings[0].equalsIgnoreCase("warp"))
        {
            Player player = (Player) commandSender;
            World w = Bukkit.createWorld(new WorldCreator(strings[1]));
            player.teleport(w.getSpawnLocation());
        } else if (strings[0].equalsIgnoreCase("broadcast"))
        {
            String[] argsArray = new String[strings.length - 1];
            System.arraycopy(strings, 1, argsArray, 0, strings.length - 1);
            String joined = String.join(" ", argsArray);

            ChatMessageManager.getInstance().broadcastMessage(joined, SoundManager.getInstance().chatNotificationSound, new ChatPattern[0]);

        } else if (strings[0].equalsIgnoreCase("validate"))
        {
            String[] argsArray = new String[strings.length - 1];
            System.arraycopy(strings, 1, argsArray, 0, strings.length - 1);
            String joined = String.join(" ", argsArray);
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(joined)));
        } else if (strings[0].equalsIgnoreCase("p"))
        {
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(strings[1])));
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(ChatColor.BOLD + strings[1])));
            commandSender.sendMessage(strings[1] + 'L');
            commandSender.sendMessage(ChatColor.BOLD + strings[1] + ChatColor.RESET + 'L');
        }

        return true;
    }
}