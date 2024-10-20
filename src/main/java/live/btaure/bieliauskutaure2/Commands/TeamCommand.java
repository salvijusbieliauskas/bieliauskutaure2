package live.btaure.bieliauskutaure2.Commands;

import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(!(commandSender instanceof ConsoleCommandSender))
        {
            UUID commandSenderUUID = ((Player) commandSender).getUniqueId();
            BTPlayer player = PlayerManager.getInstance().getBTPlayer(commandSenderUUID);
            boolean setRolePermission = player.getPermissions().get(PermissionType.MANAGE_TEAMS);

            if(!(setRolePermission))
            {
                commandSender.sendMessage(ChatColor.RED + "RAWR! tu netuwi pewmission sitai komandai >:( (kaina pewmissionam: 1000 euwu i mano banko saskaita");
                return false;
            }
        }

        if(args.length < 3)
        {
            commandSender.sendMessage(ChatColor.RED + "RAWR! tu pwivalai iwesti bent 3 awgumentus >:(");
            return false;
        }

        BTPlayer referencedPlayer = PlayerManager.getInstance().getBTPlayer(args[1]);
        BTTeam referencedTeam = PlayerManager.getInstance().getTeam(args[2]);

        if(referencedPlayer == null)
        {
            commandSender.sendMessage(ChatColor.RED + "RAWR! ivestas zaidejas neegzistuoja! :/");
            return false;
        }
        if(referencedTeam == null)
        {
            commandSender.sendMessage(ChatColor.RED + "RAWR! ivestas komandos pavadinimas neegzistuoja! :/");
            return false;
        }

        switch(args[0].toLowerCase())
        {
            case "add":
                PlayerManager.getInstance().addTeam(new BTTeam(args[1]), AddModeType.CHECK); //umm idk koks AddModeType yra tsg pridejimui kaip list.add
                return true;
            case "set": // apkeitimas vienos komandos su kita kaip suprantu kad ir ka tai reiksia
                PlayerManager.getInstance().addTeam(new BTTeam(args[1]), AddModeType.REPLACE); //as pretty sure sitas darys absoliutu suda
                return true;
            case "remove":
                //PlayerManager.getInstance().removeTeam(); XDDDD kodel reikia UUID kad removint team XD? as einu miegot
                return true;
            case "removemember":
                return true;
            case "addmember":
                return true;
        }


        return false;
    }
}
