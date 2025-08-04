package live.btaure.bieliauskutaure2.Participants;

//TODO: if team loses or gets disqualified or leaves an active minigame, members might become Spectators. this should be implemented in the manager

import live.btaure.bieliauskutaure2.Logger;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * class that represents a team of participants that can vary in size.
 */
@SerializableAs("BTTeam")
public class BTTeam implements ConfigurationSerializable
{
    private final int score;
    private final UUID ID;
    private final String name;

    public BTTeam()
    {
        this(0, UUID.randomUUID(), "Unnamed Team");
    }

    public BTTeam(String name)
    {
        this(0, UUID.randomUUID(), name);
    }

    public BTTeam(int initialScore, UUID teamID, String name)
    {
        this.score = initialScore;
        this.ID = teamID;
        this.name = name;
        Logger.getInstance().warning("A team was created that is smaller than the maximum team size");
    }

    public BTTeam(Map<String, Object> map)
    {
        this(Integer.parseInt((String) map.get("score")), UUID.fromString((String) map.get("UUID")), (String) map.get("name"));
    }

    public static BTTeam valueOf(Map<String, Object> map)
    {
        return new BTTeam(map);
    }

    public static BTTeam deserialize(Map<String, Object> map)
    {
        return new BTTeam(map);
    }

    public String getName()
    {
        return this.name;
    }

    public UUID getID()
    {
        return this.ID;
    }

    public int getScore()
    {
        return this.score;
    }

    @Override
    public String toString()
    {
        return String.format("%1$s (%2$s)", this.getName(), this.getID());
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof BTTeam)
            return ((BTTeam) other).getID().equals(this.getID());
        return false;
    }

    @Override
    public @NotNull Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("score", String.valueOf(score));
        map.put("UUID", ID.toString());
        map.put("name", name);
        return map;
    }
}