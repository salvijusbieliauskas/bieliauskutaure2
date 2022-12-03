package live.btaure.bieliauskutaure2.Participants;

//TODO: if team loses or gets disqualified or leaves an active minigame, members might become Spectators. this should be implemented in the manager

import live.btaure.bieliauskutaure2.Logger;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * class that represents a team of participants that can vary in size.
 */
@SerializableAs("BTTeam")
public class BTTeam implements ConfigurationSerializable {
    public static final int maxTeamSize = 2;
    private List<BTPlayer> teamMembers;
    private int score;
    private UUID ID;
    private String name;

    public BTTeam()
    {
        this(new ArrayList<BTPlayer>(), 0, UUID.randomUUID(), "Unnamed Team");
    }

    public BTTeam(String name)
    {
        this(new ArrayList<BTPlayer>(), 0, UUID.randomUUID(), name);
    }

    public BTTeam(List<BTPlayer> members, int initialScore, UUID teamID, String name)
    {
        this.teamMembers = members;
        this.score = initialScore;
        this.ID = teamID;
        this.name = name;
        if(members.size()==maxTeamSize)
            return;
        if(members.size()>maxTeamSize)
        {
            Logger.warning("A team was created that is larger than the maximum team size");
            return;
        }
        Logger.warning("A team was created that is smaller than the maximum team size");
    }

    /**
     * Checks if the size of the team matches with the max team size
     * @return true if the team is valid, false otherwise
     */
    public boolean isSizeValid()
    {
        return teamMembers.size() == maxTeamSize;
    }
    public BTPlayer getMember(int index)
    {
        return teamMembers.get(index);
    }
    public int getMemberCount()
    {
        return teamMembers.size();
    }

    public void addMember(BTPlayer newMember)
    {
        teamMembers.add(newMember);
        if(teamMembers.size()>maxTeamSize)
            Logger.warning("A member was added to a team that was full");
    }
    public void removeMember(int index)
    {
        teamMembers.remove(index);
        Logger.warning("A member was removed from a team. Entire team removal should be used as some minigames might not work properly.");
    }
    public String getName()
    {
        return this.name;
    }
    public UUID getID()
    {
        return this.ID;
    }
    @Override
    public String toString()
    {
        return String.format("%1$s (%2$s)",this.getName(),this.getID());
    }

    //<editor-fold desc="Serialization">
    public BTTeam(Map<String, Object> map)
    {
        this((List<BTPlayer>) map.get("members"), Integer.parseInt((String) map.get("score")), UUID.fromString((String) map.get("UUID")), (String) map.get("name"));
    }

    @Override
    public @NotNull Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("members", teamMembers);
        map.put("score", String.valueOf(score));
        map.put("UUID", ID.toString());
        map.put("name", name);
        return map;
    }

    public static BTTeam valueOf(Map<String, Object> map)
    {
        return new BTTeam(map);
    }

    public static BTTeam deserialize(Map<String, Object> map)
    {
        return new BTTeam(map);
    }
    //</editor-fold>
}
