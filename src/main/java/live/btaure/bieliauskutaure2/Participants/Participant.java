package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.ChatColor;
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
    private static final HashMap<PermissionType,Boolean> permissions = new HashMap<>(){{
        put(PermissionType.BREAK_BLOCKS,false);
        put(PermissionType.PLACE_BLOCKS,false);
        put(PermissionType.DAMAGE_ENTITIES,false);
    }};
    public Participant(UUID playerID,BTTeam team)
    {
        super(playerID,team, "Dalyvis", ChatColor.WHITE);
        HashMap<PermissionType,Boolean> permissions = new HashMap<>();
    }

    @Override
    public HashMap<PermissionType, Boolean> getPermissions()
    {
        return permissions;
    }
    //<editor-fold desc="Serialization">
    public Participant(Map<String,Object> map)
    {
        this(UUID.fromString((String) map.get("UUID")),(BTTeam)map.get("team"));
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
