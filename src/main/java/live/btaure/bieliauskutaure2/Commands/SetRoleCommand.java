package live.btaure.bieliauskutaure2.Commands;

import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SetRoleCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String name, @NotNull String[] args)
    {
        if (!(commandSender instanceof ConsoleCommandSender))
        {
            UUID commandSenderUUID = ((Player) commandSender).getUniqueId();
            BTPlayer player = PlayerManager.getInstance().getBTPlayer(commandSenderUUID);
            boolean setRolePermission = player.getPermissions().get(PermissionType.SET_ROLE);

            if (!(setRolePermission))
            {
                commandSender.sendMessage(ChatColor.RED + "RAWR! tu netuwi pewmission sitai komandai >:(");
                return false;
            }
        }

        if (args.length < 2)
        {
            commandSender.sendMessage(ChatColor.RED + "RAWR! tu pwivalai iwesti zaidejo vawda iw wole! >:(");
            return false;
        }

        BTPlayer referencedPlayer = PlayerManager.getInstance().getBTPlayer(args[0]);

        if (referencedPlayer == null)
        {
            commandSender.sendMessage(ChatColor.RED + "RAWR! ivestas zaidejas neegzistuoja! :/");
            return false;
        }

        switch (args[1].toLowerCase())
        {
            case "transliuotojas":
            case "translator":
            case "streamer":
                PlayerManager.getInstance().addBTPlayer(new Streamer(referencedPlayer.getID(), true), AddModeType.REPLACE);
                commandSender.sendMessage(ChatColor.GREEN + "Meooowww! Pakeitei zaidejo role i [REDACTED] ! Sveikinu! Tu tai padarei! Ar tu dabar patenkintas savimi? :DDDD");
                return true;
            case "stebėtojas":
            case "spectator":
                PlayerManager.getInstance().addBTPlayer(new Spectator(referencedPlayer.getID(), referencedPlayer.getTeam()), AddModeType.REPLACE);
                commandSender.sendMessage(ChatColor.GREEN + "Meooowww! Pakeitei zaidejo role i [REDACTED] ! Sveikinu! Tu tai padarei! Ar tu dabar patenkintas savimi? :DDDD");
                return true;
            case "dalyvis":
            case "participant":
                PlayerManager.getInstance().addBTPlayer(new Participant(referencedPlayer.getID(), referencedPlayer.getTeam()), AddModeType.REPLACE);
                commandSender.sendMessage(ChatColor.GREEN + "Meooowww! Pakeitei zaidejo role i [REDACTED] ! Sveikinu! Tu tai padarei! Ar tu dabar patenkintas savimi? :DDDD");
                return true;
            case "administratorius":
            case "administrator":
            case "admin":
                PlayerManager.getInstance().addBTPlayer(new Administrator(referencedPlayer.getID(), referencedPlayer.getTeam(), 0), AddModeType.REPLACE);
                commandSender.sendMessage(ChatColor.GREEN + "Meooowww! Pakeitei zaidejo role i [REDACTED] ! Sveikinu! Tu tai padarei! Ar tu dabar patenkintas savimi? :DDDD");
                return true;
        }
        commandSender.sendMessage(ChatColor.RED + "RAWR ! antwasis awgumentas newa role >:/");
        return false;
    }

}