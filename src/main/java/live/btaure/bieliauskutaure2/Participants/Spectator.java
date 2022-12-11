package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.ChatColor;
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
    private static final HashMap<PermissionType,Boolean> permissions = new HashMap<>(){{
        put(PermissionType.BREAK_BLOCKS,false);
        put(PermissionType.SET_ROLE,false);
    }};
    public Spectator(UUID playerID,BTTeam team)
    {
        super(playerID,team, "Stebėtojas", ChatColor.GRAY);
    }

    @Override
    public HashMap<PermissionType, Boolean> getPermissions()
    {
        return permissions;
    }

    //<editor-fold desc="Serialization">
    public Spectator(Map<String,Object> map)
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
