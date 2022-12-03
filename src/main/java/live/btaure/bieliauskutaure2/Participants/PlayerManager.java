package live.btaure.bieliauskutaure2.Participants;

import live.btaure.bieliauskutaure2.ConfigManager;
import live.btaure.bieliauskutaure2.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * class that contains static methods in order to manage players and houses a list of all the players in the server.
 */
public class PlayerManager//TOOD:pertvarkyti teamu struktura
{
    private List<BTPlayer> nonParticipants = new ArrayList<BTPlayer>();//contains only spectators, admins and broadcasters
    private List<BTTeam> BTTeams = new ArrayList<BTTeam>();//contains teams which contain participants
    private final ConfigManager configManager;

    /**
     * constructor
     * @param configManager ConfigManager instance that is used to load and save player data
     */
    public PlayerManager(ConfigManager configManager)
    {
        this.configManager = configManager;
        this.nonParticipants = loadNonParticipants();
        this.BTTeams = loadTeams();
        Logger.info("Teams and non-participants have been loaded from config");
    }

    /**
     * Gets and returns non-participants from the plugin configuration
     * @return a list of all registered non-participants within the configuration
     */
    private List<BTPlayer> loadNonParticipants()
    {
        return configManager.getNonParticipants();
    }

    /**
     * Gets and returns teams from the plugin configuration
     * @return a list of all registered teams within the configuration
     */
    private List<BTTeam> loadTeams()
    {
        return configManager.getTeams();
    }

    /**
     * Reloads the config manager, applying changes made to the config file externally and reloads team and non-participant information from it.
     */
    public void reload()
    {
        configManager.reload();
        this.nonParticipants = loadNonParticipants();
        this.BTTeams = loadTeams();
        Logger.info("Teams and non-participants have been loaded from config");
    }

    /**
     * Gets and returns a team by its index within the manager
     * @param index index of the team to find
     * @return the team at the index provided
     */
    public BTTeam getBTTeam(int index)
    {
        return BTTeams.get(index);
    }
    /**
     * Finds and returns a team by its ID
     * @param ID UUID of the team to find
     * @return the matching BTTeam or null if one is not found
     */
    public BTTeam getBTTeam(UUID ID)
    {
        int index = findTeam(ID);
        if(index == -1)
            return null;
        return BTTeams.get(index);
    }

    /**
     * Gets and returns a non-participant by his ID
     * @param ID UUID of the non-participant to find
     * @return a BTPlayer instance of matching non-participant. null if not found
     */
    public BTPlayer getNonParticipant(UUID ID)
    {
        int index = findNonParticipant(ID);
        if(index == -1)
            return null;
        return nonParticipants.get(index);
    }

    /**
     * Gets and returns a non-participant by his index within the manager
     * @param index index of the non-participant to get
     * @return BTPlayer of non-participant at the given index
     */
    public BTPlayer getNonParticipant(int index)
    {
        return nonParticipants.get(index);
    }

    /**
     * @return the count of registered players that are not participants
     */
    public int getNonParticipantCount()
    {
        return nonParticipants.size();
    }

    /**
     * @return the count of active teams
     */
    public int getTeamCount()
    {
        return BTTeams.size();
    }


    /**
     * Finds the index of a non-participant player by his UUID.
     * @param ID UUID of the non-participant to find
     * @return the index of the player within the manager or -1 if no player was found
     */
    private int findNonParticipant(UUID ID)
    {
        for(int x = 0; x < nonParticipants.size(); x++)
        {
            if(nonParticipants.get(x).getID().equals(ID))
                return x;
        }
        return -1;
    }

    /**
     * Finds a non-participant player by his name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the player to find
     * @return index of the non-participant that was found. -1 if there were no matches,
     * -2 if there were several possible players with this name and the name parameter was not a perfect match with any of them
     */
    public int findNonParticipant(String name)//HAHAHAHAHA SITAS METODAS as netikiu kad nera geresnio budo sita padaryt
    {
        List<Integer> indexes = new ArrayList<Integer>();
        for(int x = 0; x < nonParticipants.size(); x++)//checks if any of the players are an exact match
        {
            if(nonParticipants.get(x).getName().equals(name))
                return x;
            if(nonParticipants.get(x).getName().toLowerCase().contains(name.toLowerCase()))
                indexes.add(x);
        }
        if(indexes.size()==0)
            return -1;
        if(indexes.size()==1)
            return indexes.get(0);
        List<Integer> indexes1 = new ArrayList<Integer>();
        //checks if any of the players that weren't an exact match, are a match if case is ignored
        for (Integer index : indexes) {
            if (nonParticipants.get(index).getName().toLowerCase().equals(name.toLowerCase()))
                indexes1.add(index);
        }
        if(indexes1.size()==1)//checks if there was only one match(minecraft names are case-sensitive and there can unironically be two people on the server who have the names "person" and "persOn")
            return indexes1.get(0);
        return -2;
    }
    /**
     * Finds the index of a team by its UUID
     * @param ID UUID of the team to find
     * @return the index of the team within the manager or -1 if no team was found
     */
    private int findTeam(UUID ID)
    {
        for(int x = 0; x < BTTeams.size(); x++)
        {
            if(BTTeams.get(x).getID().equals(ID))
                return x;
        }
        return -1;
    }
    /**
     * Finds a team by its name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the team to find
     * @return index of the team that was found. -1 if there were no matches, -2 if there were several possible teams with this name and the name parameter was not a perfect match with any of them
     */
    public int findTeam(String name)//as cba
    {
        List<Integer> indexes = new ArrayList<Integer>();
        for(int x = 0; x < BTTeams.size(); x++)
        {
            if(BTTeams.get(x).getName().equals(name))
                return x;
            if(BTTeams.get(x).getName().toLowerCase().contains(name.toLowerCase()))
                indexes.add(x);
        }
        if(indexes.size()==0)
            return -1;
        if(indexes.size()==1)
            return indexes.get(0);
        List<Integer> indexes1 = new ArrayList<Integer>();
        for (Integer index : indexes) {
            if (BTTeams.get(index).getName().toLowerCase().equals(name.toLowerCase()))
                indexes1.add(index);
        }
        if(indexes1.size()==1)
            return indexes1.get(0);
        return -2;
    }
    public ParticipantIndex findParticipant(String name)//kaip tai sugeba blogeti
    {
        List<ParticipantIndex> indexes = new ArrayList<ParticipantIndex>();
        for(int x = 0; x < BTTeams.size(); x++)
        {
            for(int y = 0; y < BTTeams.get(x).getMemberCount();y++)
            {
                if(BTTeams.get(x).getMember(y).getName().equals(name))
                    return new ParticipantIndex(x,y);
                if(BTTeams.get(x).getMember(y).getName().toLowerCase().contains(name.toLowerCase()))
                    indexes.add(new ParticipantIndex(x,y));
            }
        }
        if(indexes.size()==0)
            return new ParticipantIndex(-1,-1);
        if(indexes.size()==1)
            return indexes.get(0);
        List<ParticipantIndex> indexes1 = new ArrayList<ParticipantIndex>();
        for (ParticipantIndex index : indexes) {
            if (BTTeams.get(index.getTeamIndex()).getMember(index.getPlayerIndex()).getName().toLowerCase().equals(name.toLowerCase()))
                indexes1.add(index);
        }
        if(indexes1.size()==1)
            return indexes1.get(0);
        return new ParticipantIndex(-2,-2);
    }
    public ParticipantIndex findParticipant(UUID ID)
    {
        for(int x = 0; x < BTTeams.size();x++)
        {
            for(int y = 0; y < BTTeams.get(x).getMemberCount();y++)
            {
                if(BTTeams.get(x).getMember(y).getID().equals(ID))
                    return new ParticipantIndex(x,y);
            }
        }
        return new ParticipantIndex(-1,-1);
    }
    private BTPlayer getParticipant(ParticipantIndex result)
    {
        return BTTeams.get(result.getTeamIndex()).getMember(result.getPlayerIndex());
    }
    public BTPlayer getParticipant(UUID ID)
    {
        ParticipantIndex searchResult = findParticipant(ID);
        if(searchResult.searchSuccess())
            return getParticipant(searchResult);
        return null;
    }

    /**
     * niekada nenaudokit sito method maldauju jis sulauzo serveri
     */
    public BTPlayer getParticipant(String name)
    {
        ParticipantIndex searchResult = findParticipant(name);
        if(searchResult.searchSuccess())
            return getParticipant(searchResult);
        return null;
    }
    public void removeTeam(int index)
    {
        BTTeams.remove(index);
        Logger.warning("A team was removed. The members will become spectators.");
        //TODO:make people with no teams into spectators(either manually here or implement this as a part of a timer that checks if there are people in the server that are not registered within the manager)
    }
    public void removeNonParticipant(int index)
    {
        nonParticipants.remove(index);
        Logger.warning("A non-participant was removed. Doing this might cause issues and a role change is recommended instead.");
    }

    /**
     * Checks if all teams have a valid number of players
     * @return true if all teams have a valid number of players, false otherwise
     */
    public boolean validateTeamsSize()
    {
        List<BTTeam> invalidTeams = new ArrayList<BTTeam>();
        for(BTTeam team : BTTeams)
        {
            if(!team.isSizeValid())
            {
                invalidTeams.add(team);
            }
        }
        if(invalidTeams.size() == 0) {
            Logger.success(String.format("[TEAM VALIDATION] Successfully validated %d teams",BTTeams.size()));
            return true;
        }
        StringBuilder message = new StringBuilder(String.format("[TEAM VALIDATION] %d teams failed validation. Invalid teams:\n",invalidTeams.size()));
        for(BTTeam team : invalidTeams)
            message.append(team.toString()).append('\n');
        Logger.error(message.toString().trim());
        return false;
    }

    /**
     * Checks if a player with the given UUID exists in this manager
     * @param ID UUID of the player to check
     * @return true if a player is found, false otherwise
     */
    public boolean exists(UUID ID)
    {
        return getParticipant(ID) != null || getNonParticipant(ID) != null;
    }
    public boolean addNonParticipant(BTPlayer player, AddModeType addMode)//TODO:implement functionality for AddMode.REPLACE
    {
        if(addMode.equals(AddModeType.CHECK))
        {
           if(exists(player.getID()))
               return false;
           nonParticipants.add(player);
           Logger.info(String.format("A new non-participant has been registered: %s",player.toString()));
           configManager.setNonParticipants(nonParticipants);
           return true;
        }
        else if(addMode.equals(AddModeType.FORCE)) {
            nonParticipants.add(player);
            Logger.warning(String.format("A new non-participant has been registered without checking if he already exists: %s", player.toString()));
            configManager.setNonParticipants(nonParticipants);
            return true;
        }
        else if(addMode.equals(AddModeType.REPLACE)){

        }
        return false;
    }

    /**
     * Removes all occurrences of the player with the associated ID
     * @param id UUID of the player to remove
     */
    public void removeAll(UUID id)
    {
        int nonParticipantIndex = findNonParticipant(id);
        while(nonParticipantIndex != -1)
        {
            removeNonParticipant(nonParticipantIndex);
            nonParticipantIndex = findNonParticipant(id);
        }
        //unfinished, remove from teams as well
    }
    public void broadcastDebug(String message, int level)
    {
        for(BTPlayer player : nonParticipants)
        {
            if(!(player instanceof Administrator))
                continue;
            if(!player.isOnline())
                continue;
            if(((Administrator)player).getDebugMessagesLevel() <= level)
                player.getOfflinePlayer().getPlayer().sendMessage(message);
        }
    }
}

class ParticipantIndex//cia taip blogai
{
    private final int teamIndex;//index of the team that the player is in
    private final int playerIndex;//index of the player within the team
    public boolean searchSuccess()
    {
        return teamIndex>-1 && playerIndex>-1;
    }
    public ParticipantIndex(int teamIndex, int playerIndex)
    {
        this.teamIndex = teamIndex;
        this.playerIndex = playerIndex;
    }
    public int getTeamIndex()
    {
        return teamIndex;
    }
    public int getPlayerIndex()
    {
        return playerIndex;
    }
}