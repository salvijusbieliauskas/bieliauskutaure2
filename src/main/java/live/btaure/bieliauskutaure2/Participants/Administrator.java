package live.btaure.bieliauskutaure2.Participants;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.SerializableAs;
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
    public Administrator(UUID playerID, int debugLevel) {
        super(playerID);
        this.debugMessagesLevel = debugLevel;
    }
    public int getDebugMessagesLevel()
    {
        return debugMessagesLevel;
    }
    public void setDebugMessagesLevel(int level)
    {
        this.debugMessagesLevel = level;
    }
    @Override
    public boolean minigameTeleport() {
        return true;
    }

    @Override
    public boolean teleport(Location loc) {
        return true;
    }
    //<editor-fold desc="Serialization">
    public Administrator(Map<String,Object> map)
    {
        this(UUID.fromString((String) map.get("UUID")),Integer.parseInt((String)map.get("debugLevel")));
    }
    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("UUID",super.getID().toString());
        map.put("debugLevel",String.valueOf(this.debugMessagesLevel));
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
