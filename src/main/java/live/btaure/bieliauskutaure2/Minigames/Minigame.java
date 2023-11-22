package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.Participant;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.UUID;

public abstract class Minigame implements Listener
{
    public final boolean allowsDisconnect;
    public final String name;

    public GameMode getGameMode()
    {
        return gameMode;
    }
    public abstract World getWorld();

    public void setGameMode(GameMode gameMode)
    {
        this.gameMode = gameMode;
    }

    private GameMode gameMode;

    protected Minigame(boolean allowsDisconnect, String name, GameMode gameMode)
    {
        this.allowsDisconnect = allowsDisconnect;
        this.name = name;
        this.gameMode = gameMode;
    }
    public abstract Location getSpectatorSpawnLocation();
    public abstract void applySettings(BTPlayer player);
    public void applySettings(Player player)
    {
        applySettings(PlayerManager.getInstance().getBTPlayer(player));
    }

    /**
     * puts the given player in the appropriate location(for example, if the minigame is boatrace, the player would be put in a boat at the starting line)
     * @param player player to teleport
     */
    public abstract void teleportParticipant(BTPlayer player);

    /**
     * teleports the given player to the location provided by getSpectatorSpawnLocation()
     * @param player player to teleport
     */
    public void teleportSpectator(BTPlayer player)
    {
        player.teleport(getSpectatorSpawnLocation());
    }
    public abstract boolean init();
    public abstract boolean begin();
    public abstract boolean end();
    public abstract List<String> getScoreboardContent(UUID playerID);
    /**
     * Checks whether the provided player should have minigame-specific settings applied to them
     * @param player player to check
     * @return true if player should receive minigame-specific settings, false otherwise
     */
    public boolean performChecks(BTPlayer player)
    {
        if(!player.isOnline())
            return false;
        if(!(player instanceof Participant))
            return false;
        return true;
    }
    public static Class getMinigameClass(String name)
    {
        try {
            Class aClass = Class.forName("live.btaure.bieliauskutaure2.Minigames."+name);
            if(aClass.getSuperclass() != null && aClass.getSuperclass().getName().equals(Minigame.class.getName()))
                return aClass;
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    @Override
    public String toString()
    {
        return name;
    }
}
