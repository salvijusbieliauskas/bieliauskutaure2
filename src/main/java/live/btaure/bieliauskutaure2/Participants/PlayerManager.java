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
    private List<BTPlayer> BTPlayers = new ArrayList<BTPlayer>();//contains all players
    private List<BTTeam> BTTeams = new ArrayList<BTTeam>();//contains teams which contain participants
    private static PlayerManager playerManagerInstance = null;

    public static PlayerManager getInstance()
    {
        if(playerManagerInstance == null)
        {
            playerManagerInstance = new PlayerManager();
        }
        return playerManagerInstance;
    }
    private PlayerManager()
    {
        this.BTPlayers = loadBTPlayers();
        this.BTTeams = loadTeams();
        Logger.info("Teams and non-participants have been loaded from config");
    }

    /**
     * Gets and returns player from the plugin configuration
     * @return a list of all registered player within the configuration
     */
    private List<BTPlayer> loadBTPlayers()
    {
        return ConfigManager.getInstance().getBTPlayers();
    }

    /**
     * Gets and returns teams from the plugin configuration
     * @return a list of all registered teams within the configuration
     */
    private List<BTTeam> loadTeams()
    {
        return ConfigManager.getInstance().getTeams();
    }

    /**
     * Reloads the config manager, applying changes made to the config file externally and reloads team and player information from it.
     */
    public void reload()
    {
        ConfigManager.getInstance().reload();
        this.BTPlayers = loadBTPlayers();
        this.BTTeams = loadTeams();
        Logger.info("Teams and player have been loaded from config");
    }

    /**
     * Gets and returns a team by its index within the manager
     * @param index index of the team to find
     * @return the team at the index provided
     */
    public BTTeam getTeam(int index)
    {
        return BTTeams.get(index);
    }
    /**
     * Finds and returns a team by its ID
     * @param ID UUID of the team to find
     * @return the matching BTTeam or null if one is not found
     */
    public BTTeam getTeam(UUID ID)
    {
        int index = findTeam(ID);
        if(index == -1)
            return null;
        return BTTeams.get(index);
    }


    /**
     * Gets a team by its name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the team to find
     * @return BTTeam instance reference or null if a team wasn't found
     */
    public BTTeam getTeam(String name)
    {
        int index = findTeam(name);
        if(index > -1)
            return BTTeams.get(index);
        return null;
    }

    /**
     * Gets and returns a player by his ID
     * @param ID UUID of the player to find
     * @return a BTPlayer instance of matching player. null if not found
     */
    public BTPlayer getBTPlayer(UUID ID)
    {
        int index = findBTPlayer(ID);
        if(index == -1)
            return null;
        return getBTPlayer(index);
    }

    /**
     * Gets and returns a player by his index within the manager
     * @param index index of the non-participant to get
     * @return BTPlayer player at the given index
     */
    public BTPlayer getBTPlayer(int index)
    {
        return BTPlayers.get(index);
    }

    /**
     * @return the count of registered players that are not participants
     */
    public int getBTPlayer()
    {
        return BTPlayers.size();
    }

    /**
     * @return the count of active teams
     */
    public int getTeamCount()
    {
        return BTTeams.size();
    }


    /**
     * Finds the index of a player by his UUID.
     * @param ID UUID of the player to find
     * @return the index of the player within the manager or -1 if no player was found
     */
    private int findBTPlayer(UUID ID)
    {
        for(int x = 0; x < BTPlayers.size(); x++)
        {
            if(BTPlayers.get(x).getID().equals(ID))
                return x;
        }
        return -1;
    }

    /**
     * Finds a player by his name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the player to find
     * @return index of the player that was found. -1 if there were no matches,
     * -2 if there were several possible players with this name and the name parameter was not a perfect match with any of them
     */
    private int findBTPlayer(String name)//HAHAHAHAHA SITAS METODAS as netikiu kad nera geresnio budo sita padaryt
    {
        List<Integer> indexes = new ArrayList<Integer>();
        for(int x = 0; x < BTPlayers.size(); x++)//checks if any of the players are an exact match
        {
            if(BTPlayers.get(x).getName().equals(name))
                return x;
            if(BTPlayers.get(x).getName().toLowerCase().contains(name.toLowerCase()))
                indexes.add(x);
        }
        if(indexes.size()==0)
            return -1;
        if(indexes.size()==1)
            return indexes.get(0);
        List<Integer> indexes1 = new ArrayList<Integer>();
        //checks if any of the players that weren't an exact match, are a match if case is ignored
        for (Integer index : indexes) {
            if (BTPlayers.get(index).getName().toLowerCase().equals(name.toLowerCase()))
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
    private int findTeam(String name)//as cba
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
    public void removeTeam(int index)
    {
        BTTeams.remove(index);
        Logger.warning("A team was removed. The members will become spectators.");
        //TODO:make people with no teams into spectators(either manually here or implement this as a part of a timer that checks if there are people in the server that are not registered within the manager)
    }
    public void removePlayer(int index)
    {
        BTPlayers.remove(index);
        Logger.warning("A player was removed. Doing this might cause issues and a role change is recommended instead.");
    }

    /**
     * Checks if all teams have a valid number of players
     * @return true if all teams have a valid number of players, false otherwise
     */
    /*public boolean validateTeamsSize()
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
    }*/

    /**
     * Checks if a player with the given UUID exists in this manager
     * @param ID UUID of the player to check
     * @return true if a player is found, false otherwise
     */
    public boolean exists(UUID ID)
    {
        return getBTPlayer(ID) != null;
    }
    public boolean addBTPlayer(BTPlayer player, AddModeType addMode)//TODO:implement functionality for AddMode.REPLACE
    {
        if(addMode.equals(AddModeType.CHECK))
        {
           if(exists(player.getID()))
               return false;
           BTPlayers.add(player);
           Logger.info(String.format("A new player has been registered: %s",player.toString()));
           ConfigManager.getInstance().setNonParticipants(BTPlayers);
           return true;
        }
        else if(addMode.equals(AddModeType.FORCE)) {
            BTPlayers.add(player);
            Logger.warning(String.format("A new player has been registered without checking if he already exists: %s", player.toString()));
            ConfigManager.getInstance().setNonParticipants(BTPlayers);
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
        int nonParticipantIndex = this.findBTPlayer(id);
        while(nonParticipantIndex != -1)
        {
            removePlayer(nonParticipantIndex);
            nonParticipantIndex = this.findBTPlayer(id);
        }
        //unfinished, remove from teams as well
    }
    public void broadcastDebug(String message, int level)
    {
        for(BTPlayer player : BTPlayers)
        {
            if(!(player instanceof Administrator))
                continue;
            if(!player.isOnline())
                continue;
            if(((Administrator)player).getDebugMessagesLevel() <= level)
                player.getOfflinePlayer().getPlayer().sendMessage(message);
        }
    }
    public void addTeam(BTTeam teamToAdd)
    {
        BTTeams.add(teamToAdd);
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