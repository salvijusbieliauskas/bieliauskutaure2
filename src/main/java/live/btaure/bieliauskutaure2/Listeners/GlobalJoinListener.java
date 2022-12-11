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
        Bukkit.getScheduler().runTask(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class), new Runnable() {
            @Override
            public void run()
            {
                MinigameManager.getInstance().getActiveGame().applySettings(player);
                if(!player.getPlayer().getLocation().getWorld().getName().equals(MinigameManager.getInstance().getActiveGame().getWorld().getName()))
                {
                    if(player instanceof Participant)
                        MinigameManager.getInstance().getActiveGame().teleportParticipant(player);
                    else
                        MinigameManager.getInstance().getActiveGame().teleportSpectator(player);
                }
            }
        });
    }
}
