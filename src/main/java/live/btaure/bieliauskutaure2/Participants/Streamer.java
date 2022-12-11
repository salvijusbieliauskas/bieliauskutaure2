package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SerializableAs("Streamer")
public class Streamer extends BTPlayer{
    private static final HashMap<PermissionType,Boolean> permissions = new HashMap<>(){{
        put(PermissionType.BREAK_BLOCKS,true);
        put(PermissionType.SET_ROLE,true);
    }};

    public Streamer(UUID playerID)
    {
        super(playerID, null, "Transliuotojas", ChatColor.DARK_RED);
    }

    @Override
    public HashMap<PermissionType, Boolean> getPermissions()
    {
        return permissions;
    }

    //<editor-fold desc="Serialization">
    public Streamer(Map<String,Object> map)
    {
        this(UUID.fromString((String) map.get("UUID")));
    }
    @Override
    public @NotNull Map<String, Object> serialize()
    {
        Map<String,Object> map = new HashMap<>();
        map.put("UUID",super.getID().toString());
        return map;
    }

    public static Streamer valueOf(Map<String,Object> map)
    {
        return new Streamer(map);
    }

    public static Streamer deserialize(Map<String,Object> map)
    {
        return new Streamer(map);
    }
    //</editor-fold>
}
