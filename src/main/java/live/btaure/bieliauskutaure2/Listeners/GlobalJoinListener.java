package live.btaure.bieliauskutaure2.Listeners;

import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class GlobalJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        PlayerManager.getInstance().addBTPlayer(new Spectator(event.getPlayer().getUniqueId(),null), AddModeType.CHECK);
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(event.getPlayer().getUniqueId());
        player.updateScoreboard();
        MinigameManager.getInstance().getActiveGame().applySettings(player);
    }
}
