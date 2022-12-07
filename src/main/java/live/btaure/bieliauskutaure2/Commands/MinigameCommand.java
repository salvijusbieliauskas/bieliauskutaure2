package live.btaure.bieliauskutaure2.Commands;

import live.btaure.bieliauskutaure2.Minigames.Minigame;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MinigameCommand  implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(args.length==0 || args[0].equalsIgnoreCase("info"))
        {
            commandSender.sendMessage("Minigame manager info: "+MinigameManager.getInstance().toString());
            return true;
        }
        switch(args[0].toLowerCase())
        {
            case "advance":
                return MinigameManager.getInstance().advance();
            case "init":
                if(args.length==1)
                {
                    commandSender.sendMessage("Minigame to initialize not specified.");
                    return false;
                }
                String fixedName = args[1].toUpperCase().substring(0,1)+args[1].toLowerCase().substring(1);
                Class c = Minigame.getMinigameClass(fixedName);
                if(c == null)
                {
                    commandSender.sendMessage("Minigame to initialize not found.");
                    return false;
                }
                Object instance = null;
                try {
                    instance = c.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    commandSender.sendMessage("Failed to create a minigame instance.");
                    return false;
                }
                return MinigameManager.getInstance().init((Minigame) instance);
            case "begin":
                return MinigameManager.getInstance().begin();
            case "end":
                return MinigameManager.getInstance().end();
            case "reset":
                return MinigameManager.getInstance().reset();
            default:
                commandSender.sendMessage("Invalid first argument");
                return false;
        }
    }
}
