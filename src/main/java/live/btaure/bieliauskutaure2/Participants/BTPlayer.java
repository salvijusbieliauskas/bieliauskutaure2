package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.UUID;


public abstract class BTPlayer implements ConfigurationSerializable {

    private OfflinePlayer player;

    public BTPlayer(UUID playerID)
    {
        this.player = Bukkit.getOfflinePlayer(playerID);
    }

    public OfflinePlayer getOfflinePlayer()
    {
        return player;
    }

    /**
     * @return java.util.UUID of the associated player
     */
    public UUID getID()
    {
        return player.getUniqueId();
    }

    /**
     * @return the name of the associated player
     */
    public String getName()
    {
        return player.getName();
    }

    /**
     * used by minigames in order to teleport the player to their appropriate position once it begins or ends
     *
     * @return true if successful, false otherwise
     */
    public abstract boolean minigameTeleport();

    /**
     * teleports this player to a specific location
     *
     * @return true if successful, false otherwise
     */
    public boolean teleport(Location loc)
    {
        if (!player.isOnline()) return false;
        player.getPlayer().teleport(loc);
        return true;
    }

    public boolean isOnline()
    {
        return player.isOnline();
    }

    @Override
    public String toString()
    {
        return String.format("%1$s (%2$s)", getName(), getID().toString());
    }
}
