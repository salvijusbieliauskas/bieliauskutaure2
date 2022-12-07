package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Lobby extends Minigame implements Listener {

    public Lobby()
    {
        super(true,"Lobby", GameMode.SURVIVAL);
    }
    private static World world = Bukkit.getServer().createWorld(new WorldCreator("NEWLOBBY"));
    @Override
    public World getWorld()
    {
        return world;
    }

    /**
     * This method applies the scoreboard, gamemode, potion effects and other settings to the given player.
     * This method can be used as a player reset.
     * @param player player to apply effects to
     */
    @Override
    public void applySettings(BTPlayer player)
    {
        if(!super.performChecks(player))
            return;
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.getActivePotionEffects().clear();
        bukkitPlayer.setGameMode(super.getGameMode());
    }

    @Override
    public void teleportParticipant(BTPlayer player)
    {
        player.teleport(new Location(getWorld(),0,0,0));
    }

    @Override
    public void teleportSpectator(BTPlayer player)
    {
        player.teleport(new Location(getWorld(),0,0,0));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId());
        if(player instanceof Administrator || player instanceof Streamer)
            return;
        e.setCancelled(true);
    }
}
