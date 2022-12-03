package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO:implement a check that runs every 3-10 seconds that teleports all spectators to the active minigame
/**
 * spectators are players that are in the server, but can only observe the tournament
 */
@SerializableAs("Spectator")
public class Spectator extends BTPlayer
{
    public Spectator(UUID playerID)
    {
        super(playerID);
    }

    @Override
    public boolean minigameTeleport()
    {
        return true;
    }
    //<editor-fold desc="Serialization">
    public Spectator(Map<String,Object> map)
    {
        super(UUID.fromString((String) map.get("UUID")));
    }
    @Override
    public @NotNull Map<String, Object> serialize()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("UUID",super.getID().toString());
        return map;
    }

    public static Spectator valueOf(Map<String,Object> map)
    {
        return new Spectator(map);
    }

    public static Spectator deserialize(Map<String,Object> map)
    {
        return new Spectator(map);
    }
    //</editor-fold>
}
