package live.btaure.bieliauskutaure2.Listeners;

import live.btaure.bieliauskutaure2.Participants.AddModeType;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import live.btaure.bieliauskutaure2.Participants.Spectator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class GlobalJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        PlayerManager.getInstance().addBTPlayer(new Spectator(event.getPlayer().getUniqueId(),null), AddModeType.CHECK);
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(event.getPlayer().getUniqueId());
        player.updateScoreboard();
    }
}
