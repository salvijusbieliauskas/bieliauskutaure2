package live.btaure.bieliauskutaure2.Commands;

import com.jeff_media.customblockdata.CustomBlockData;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings)
    {
        Player player = (Player) commandSender;
        if(strings.length == 0)
            return true;
        if(strings[0].equalsIgnoreCase("warp")) {
            World w = Bukkit.createWorld(new WorldCreator(strings[1]));
            player.teleport(w.getSpawnLocation());
        }
        else if(strings[0].equalsIgnoreCase("setblock") && strings.length>1)
        {
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
            Block block = player.getWorld().getBlockAt(player.getLocation().toBlockLocation().subtract(0,1,0));
            PersistentDataContainer customBlockdata = new CustomBlockData(block, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            NamespacedKey key = new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint");
            if(customBlockdata.has(key, PersistentDataType.INTEGER)) {
                player.sendMessage(customBlockdata.get(key,PersistentDataType.INTEGER).toString());
            }
        }
        else if(strings[0].equalsIgnoreCase("listblock"))
        {
            player.sendMessage("blocks with data in your chunk:");
            for(Block data : CustomBlockData.getBlocksWithCustomData(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),player.getChunk()))
            {
                player.sendMessage(data.getLocation().toString());
            }
        }

        return true;
    }
}
