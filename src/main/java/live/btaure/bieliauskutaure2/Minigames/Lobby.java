package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Lobby extends Minigame{

    public Lobby()
    {
        super(true,"Lobby");
    }

    /**
     * This method applies the scoreboard, gamemode, potion effects and other settings to the given player.
     * Does not perform any checks, should be performed before calling
     * @param player player to apply effects to
     */
    @Override
    public void ApplySettings(BTPlayer player)
    {
        Player bukkitPlayer = player.getOfflinePlayer().getPlayer();
        bukkitPlayer.getActivePotionEffects().clear();
        bukkitPlayer.setGameMode(GameMode.SURVIVAL);

    }
}
