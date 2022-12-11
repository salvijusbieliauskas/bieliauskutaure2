package live.btaure.bieliauskutaure2.Participants;

import live.btaure.bieliauskutaure2.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Administrators are players that do not participate in minigames and should not be affected by any of their effects. They should be teleported to the spectator starting location.
 */
@SerializableAs("Administrator")
public class Administrator extends BTPlayer {
    private int debugMessagesLevel;
    private static final HashMap<PermissionType,Boolean> permissions = new HashMap<>(){{
        put(PermissionType.BREAK_BLOCKS,true);
        put(PermissionType.SET_ROLE,true);
    }};
    public Administrator(UUID playerID, BTTeam team, int debugLevel) {
        super(playerID,team,"Administratorius", ChatColor.RED);
        this.debugMessagesLevel = debugLevel;
    }

    public int getDebugMessagesLevel()
    {
        return debugMessagesLevel;
    }
    public void setDebugMessagesLevel(int level)
    {
        this.debugMessagesLevel = level;
        PlayerManager.getInstance().saveBTPlayers();
    }

    @Override
    public HashMap<PermissionType, Boolean> getPermissions()
    {
        return permissions;
    }
    //<editor-fold desc="Serialization">
    public Administrator(Map<String,Object> map)
    {
        this(UUID.fromString((String) map.get("UUID")),(BTTeam)map.get("team"),(Integer)map.get("debugLevel"));
    }
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("UUID",super.getID().toString());
        map.put("team",super.getTeam());
        map.put("debugLevel",this.debugMessagesLevel);
        return map;
    }

    public static Administrator valueOf(Map<String,Object> map) {
        return new Administrator(map);
    }

    public static Administrator deserialize(Map<String,Object> map) {
        return new Administrator(map);
    }
    //</editor-fold>
}
