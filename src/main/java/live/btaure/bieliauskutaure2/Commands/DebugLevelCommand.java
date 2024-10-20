package live.btaure.bieliauskutaure2.Commands;
import live.btaure.bieliauskutaure2.Participants.Administrator;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DebugLevelCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String name, @NotNull String[] args)
    {
        if(!(commandSender instanceof Player))
        {
            commandSender.sendMessage(ChatColor.RED + "literaliai nusizudyk ka tu cia nx bandai daryti (comanda gali naudot tik player)");
            return false;
        }
        if(args.length == 0)
        {
            commandSender.sendMessage(ChatColor.RED + "literaliai nusizudyk ka tu cia nx bandai daryti (parasyk actual debug level)");
            return false;
        }
        int debugLevel;
        try
        {
            debugLevel = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException exeption)
        {
            commandSender.sendMessage(ChatColor.RED + "meow yous has wwitted nowt a numbew :/");
            return false;
        }

        if(debugLevel <0 || debugLevel > 4)
        {
            commandSender.sendMessage(ChatColor.LIGHT_PURPLE + "meoww you hawe written a wrong debug lewel :/ (debug lewels 0-4");
            return false;
        }

        UUID commandSenderUUID = ((Player)commandSender).getUniqueId();
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(commandSenderUUID);
        if(!(player instanceof Administrator))
        {
            commandSender.sendMessage(ChatColor.RED + "meow you awe not pewmitted to umse thims command");
            return false;
        }
        ((Administrator) player).setDebugMessagesLevel(debugLevel);
        commandSender.sendMessage(String.format("%1$smeow youw debug lewel had been setted to %2$s",ChatColor.LIGHT_PURPLE, debugLevel));

        return true;
    }


}
