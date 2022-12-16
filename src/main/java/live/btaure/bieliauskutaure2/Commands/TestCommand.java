package live.btaure.bieliauskutaure2.Commands;

import com.jeff_media.customblockdata.CustomBlockData;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Chat.ChatMessageManager;
import live.btaure.bieliauskutaure2.Chat.ChatPattern;
import live.btaure.bieliauskutaure2.Chat.MinecraftFont;
import live.btaure.bieliauskutaure2.SoundManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import live.btaure.bieliauskutaure2.Minigames.Parkour;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        if(strings.length == 0)
            return true;
        if(strings[0].equalsIgnoreCase("warp")) {
            Player player = (Player) commandSender;
            World w = Bukkit.createWorld(new WorldCreator(strings[1]));
            player.teleport(w.getSpawnLocation());
        }
        else if(strings[0].equalsIgnoreCase("setblock") && strings.length>1)
        {
            Player player = (Player) commandSender;
            Block block = player.getWorld().getBlockAt(player.getLocation().toBlockLocation().subtract(0,1,0));
            PersistentDataContainer customBlockdata = new CustomBlockData(block, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            NamespacedKey key = new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint");
            if(!customBlockdata.has(key, PersistentDataType.INTEGER)) {
                customBlockdata.set(key, PersistentDataType.INTEGER, Integer.parseInt(strings[1]));
                player.sendMessage("set block");
            }
        }
        else if(strings[0].equalsIgnoreCase("removeblock"))
        {
            Player player = (Player) commandSender;
            Block block = player.getWorld().getBlockAt(player.getLocation().toBlockLocation().subtract(0,1,0));
            PersistentDataContainer customBlockdata = new CustomBlockData(block, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            NamespacedKey key = new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint");
            if(customBlockdata.has(key, PersistentDataType.INTEGER)) {
                customBlockdata.remove(key);
                player.sendMessage("removed");
            }
        }
        else if(strings[0].equalsIgnoreCase("getblock"))
        {
            Player player = (Player) commandSender;
            Block block = player.getWorld().getBlockAt(player.getLocation().toBlockLocation().subtract(0,1,0));
            PersistentDataContainer customBlockdata = new CustomBlockData(block, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            NamespacedKey key = new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint");
            //player.sendMessage("XD?");
            if(customBlockdata.has(key, PersistentDataType.INTEGER)) {
                player.sendMessage(customBlockdata.get(key,PersistentDataType.INTEGER).toString());
            }
        }
        else if(strings[0].equalsIgnoreCase("listblock"))
        {
            Player player = (Player) commandSender;
            player.sendMessage("blocks with data in your chunk:");
            for(Block data : CustomBlockData.getBlocksWithCustomData(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),player.getChunk()))
            {
                player.sendMessage(data.getLocation().toString());
            }
        }
        else if(strings[0].equalsIgnoreCase("broadcast"))
        {
            String[] argsArray = new String[strings.length-1];
            for(int x = 1; x < strings.length;x++)
            {
                argsArray[x-1]=strings[x];
            }
            String joined = String.join(" ",argsArray);

            ChatMessageManager.getInstance().broadcastMessage(joined, SoundManager.getInstance().chatNotificationSound, new ChatPattern[0]);

        }
        else if(strings[0].equalsIgnoreCase("validate"))
        {
            String[] argsArray = new String[strings.length-1];
            for(int x = 1; x < strings.length;x++)
            {
                argsArray[x-1]=strings[x];
            }
            String joined = String.join(" ",argsArray);
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(joined)));
        }
        else if(strings[0].equalsIgnoreCase("sudas"))
        {
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength("š")));
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(ChatColor.BOLD+"š")));
        }
        else if(strings[0].equalsIgnoreCase("p"))
        {
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(strings[1])));
            commandSender.sendMessage(String.valueOf(ChatMessageManager.getInstance().getLength(ChatColor.BOLD+strings[1])));
            commandSender.sendMessage(strings[1]+'L');
            commandSender.sendMessage(ChatColor.BOLD+strings[1]+ChatColor.RESET+'L');
        }

        return true;
    }
}
