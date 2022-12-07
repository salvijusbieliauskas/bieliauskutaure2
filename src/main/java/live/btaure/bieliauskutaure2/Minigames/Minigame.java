package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.Participant;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.event.Listener;

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

    Minigame(boolean allowsDisconnect, String name, GameMode gameMode)
    {
        this.allowsDisconnect = allowsDisconnect;
        this.name = name;
        this.gameMode = gameMode;
    }

    public abstract void applySettings(BTPlayer player);
    public abstract void teleportParticipant(BTPlayer player);
    public abstract void teleportSpectator(BTPlayer player);
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
}
