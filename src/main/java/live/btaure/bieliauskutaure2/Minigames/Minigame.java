package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import org.bukkit.event.Listener;

public abstract class Minigame implements Listener
{
    public final boolean allowsDisconnect;
    public final String name;

    Minigame(boolean allowsDisconnect, String name)
    {
        this.allowsDisconnect = allowsDisconnect;
        this.name = name;
    }

    public abstract void ApplySettings(BTPlayer player);
}
