package live.btaure.bieliauskutaure2.Listeners;

import live.btaure.bieliauskutaure2.Participants.AddModeType;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import live.btaure.bieliauskutaure2.Participants.Spectator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class GlobalJoinListener implements Listener {
    private PlayerManager playerManager;
    public GlobalJoinListener(PlayerManager playerManager)
    {
        this.playerManager = playerManager;
    }
    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event)
    {
        playerManager.addBTPlayer(new Spectator(event.getPlayer().getUniqueId(),null), AddModeType.CHECK);
    }
}
