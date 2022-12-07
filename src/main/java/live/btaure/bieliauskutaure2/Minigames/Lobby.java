package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class Lobby extends Minigame{

    public Lobby()
    {
        super(true,"Lobby", GameMode.SURVIVAL);
    }
    private World world = Bukkit.getWorld("NEWLOBBY");
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

    @EventHandler
    public void onBlockBreak(BlockDestroyEvent e)
    {

    }
}
