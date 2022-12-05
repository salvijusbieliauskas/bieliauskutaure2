package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * participants are participants... of the tournament..?
 */
@SerializableAs("Participant")
public class Participant extends BTPlayer implements ConfigurationSerializable
{
    public Participant(UUID playerID,BTTeam team)
    {
        super(playerID,team);
    }

    @Override
    public boolean minigameTeleport()
    {
        return true;
    }
    //<editor-fold desc="Serialization">
    public Participant(Map<String,Object> map)
    {
        super(UUID.fromString((String) map.get("UUID")),(BTTeam)map.get("team"));
    }
    @Override
    public @NotNull Map<String, Object> serialize()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("UUID",super.getID().toString());
        map.put("team",super.getTeam());
        return map;
    }

    public static Participant valueOf(Map<String,Object> map)
    {
        return new Participant(map);
    }

    public static Participant deserialize(Map<String,Object> map)
    {
        return new Participant(map);
    }
    //</editor-fold>
}
