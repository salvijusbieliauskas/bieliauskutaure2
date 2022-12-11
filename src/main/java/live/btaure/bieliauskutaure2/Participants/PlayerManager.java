package live.btaure.bieliauskutaure2.Participants;

import live.btaure.bieliauskutaure2.ConfigManager;
import live.btaure.bieliauskutaure2.Helpers.Wrappers.Result;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * singleton class that contains static methods in order to manage players and houses a list of all the players in the server.
 */
public class PlayerManager//TOOD:pertvarkyti teamu struktura
{
    private HashMap<UUID, BTPlayer> BTPlayers = new HashMap<UUID,BTPlayer>();//contains all players
    private HashMap<UUID, BTTeam> BTTeams = new HashMap<UUID,BTTeam>();//contains teams which contain participants
    private static PlayerManager playerManagerInstance = null;
    public static final int maxTeamSize = 2;

    public static PlayerManager getInstance()
    {
        if(playerManagerInstance == null)
        {
            playerManagerInstance = new PlayerManager();
            Logger.getInstance().info("Teams and players have been loaded from config");
        }
        return playerManagerInstance;
    }
    private PlayerManager()
    {
        this.BTPlayers = loadBTPlayers();
        this.BTTeams = loadTeams();
    }

    /**
     * Updates the scoreboards of all online players
     */
    public void updateScoreboards()
    {
        for(BTPlayer player : BTPlayers.values())
        {
            player.updateScoreboard();
        }
    }

    /**
     * Teleports all participants to the provided location
     * @param loc location to teleport to
     * @param requireValid if true, only participants with valid teams will be teleported
     */
    public void teleportParticipants(Location loc, boolean requireValid)
    {
        teleportParticipants(new ArrayList<Location>(){{add(loc);}},requireValid);
    }

    /**
     * Teleports all participants to the provided locations, iterating through them
     * @param loc locations to teleport to
     * @param requireValid if true, only participants with valid teams will be teleported
     */
    public void teleportParticipants(List<Location> loc, boolean requireValid)
    {
        int locIndex = 0;
        for(BTPlayer player : BTPlayers.values())
        {
            if(!player.isOnline())
                continue;
            if(!(player instanceof Participant))
                continue;
            if(requireValid && !isTeamSizeValid(player.getTeam()))
                continue;
            player.getPlayer().teleport(loc.get(locIndex));
            locIndex++;
            if(locIndex>=loc.size())
                locIndex=0;
        }
    }

    /**
     * Teleports all players, which are not participants to the provided location
     * @param loc location to teleport to
     */
    public void teleportSpectators(Location loc)
    {
        teleportSpectators(new ArrayList<Location>(){{add(loc);}});
    }
    /**
     * Teleports all players, which are not participants to the provided locations, iterating through them
     * @param loc locations to teleport to
     */
    public void teleportSpectators(List<Location> loc)
    {
        int locIndex = 0;
        for(BTPlayer player : BTPlayers.values())
        {
            if(!player.isOnline())
                continue;
            if(player instanceof Participant)
                continue;
            player.getPlayer().teleport(loc.get(locIndex));
            locIndex++;
            if(locIndex>=loc.size())
                locIndex=0;
        }
    }



    /**
     * Gets and returns player from the plugin configuration
     * @return a list of all registered player within the configuration
     */
    private HashMap<UUID,BTPlayer> loadBTPlayers()
    {
        List<BTPlayer> players = ConfigManager.getInstance().getBTPlayers();
        HashMap<UUID,BTPlayer> toReturn = new HashMap<UUID,BTPlayer>();
        for(BTPlayer player : players)
            toReturn.put(player.getID(),player);
        return toReturn;
    }

    /**
     * Gets and returns teams from the plugin configuration
     * @return a list of all registered teams within the configuration
     */
    private HashMap<UUID,BTTeam> loadTeams()
    {
        List<BTTeam> teams = ConfigManager.getInstance().getTeams();
        HashMap<UUID,BTTeam> toReturn = new HashMap<UUID,BTTeam>();
        for(BTTeam team : teams)
            toReturn.put(team.getID(),team);
        return toReturn;
    }

    /**
     * Reloads the config manager, applying changes made to the config file externally and reloads team and player information from it.
     */
    public void reload()
    {
        ConfigManager.getInstance().reload();
        this.BTPlayers = loadBTPlayers();
        this.BTTeams = loadTeams();
        Logger.getInstance().info("Teams and player have been loaded from config");
    }
    /**
     * Finds and returns a team by its ID
     * @param ID UUID of the team to find
     * @return the matching BTTeam or null if one is not found
     */
    public BTTeam getTeam(UUID ID)
    {
        return BTTeams.get(ID);
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
        Result<UUID> result = findTeam(name);
        if(result.isSuccessful())
            return BTTeams.get(result.getResult());
        else
            return null;
    }

    /**
     * Gets and returns a player by his ID
     * @param ID UUID of the player to find
     * @return a BTPlayer instance of matching player. null if not found
     */
    public BTPlayer getBTPlayer(UUID ID)
    {
        return BTPlayers.get(ID);
    }

    /**
     * Gets a player by his name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the player to find
     * @return BTPlayer instance reference or null if a player wasn't found
     */
    public BTPlayer getBTPlayer(String name)
    {
        Result<UUID> result = findBTPlayer(name);
        if(result.isSuccessful())
            return BTPlayers.get(result.getResult());
        else
            return null;
    }
    /**
     * @return the count of registered teams
     */
    public int getTeamCount()
    {
        return BTTeams.size();
    }

    /**
     * @return the count of registered players
     */
    public int getPlayerCount()
    {
        return BTPlayers.size();
    }


    /**
     * Finds a player by his name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the player to find
     * @return index of the player that was found. -1 if there were no matches,
     * -2 if there were several possible players with this name and the name parameter was not a perfect match with any of them
     */
    private Result<UUID> findBTPlayer(String name)//HAHAHAHAHA SITAS METODAS as netikiu kad nera geresnio budo sita padaryt
    {
        List<UUID> ids = new ArrayList<UUID>();
        for(UUID key : BTPlayers.keySet())
        {
            if(BTPlayers.get(key).getName().equals(name))
                return new Result<UUID>(key,true);
            if(BTPlayers.get(key).getName().toLowerCase().contains(name.toLowerCase()))
                ids.add(key);
        }
        if(ids.size()==0)
            return new Result<UUID>(null,false);
        if(ids.size()==1)
            return new Result<UUID>(ids.get(0),true);
        List<UUID> ids1 = new ArrayList<UUID>();
        for (UUID id : ids) {
            if (BTPlayers.get(id).getName().equalsIgnoreCase(name))
                ids1.add(id);
        }
        if(ids1.size()==1)
            return new Result<UUID>(ids1.get(0),true);
        return new Result<UUID>(null,false);
    }
    /**
     * Finds a team by its name.
     * Should only be used when UUID is unknown.
     * Does not always require an exact name, only a part of it.
     * @param name name of the team to find
     * @return result containing UUID or null
     */
    private Result<UUID> findTeam(String name)//as cba
    {
        List<UUID> ids = new ArrayList<UUID>();
        for(UUID key : BTTeams.keySet())
        {
            if(BTTeams.get(key).getName().equals(name))
                return new Result<UUID>(key,true);
            if(BTTeams.get(key).getName().toLowerCase().contains(name.toLowerCase()))
                ids.add(key);
        }
        if(ids.size()==0)
            return new Result<UUID>(null,false);
        if(ids.size()==1)
            return new Result<UUID>(ids.get(0),true);
        List<UUID> ids1 = new ArrayList<UUID>();
        for (UUID id : ids) {
            if (BTTeams.get(id).getName().equalsIgnoreCase(name))
                ids1.add(id);
        }
        if(ids1.size()==1)
            return new Result<UUID>(ids1.get(0),true);
        return new Result<UUID>(null,false);
    }

    /**
     * Removes a team from the manager that matches the UUID provided
     * @param id UUID of the team to remove
     */
    public void removeTeam(UUID id)
    {
        BTTeams.remove(id);
        Logger.getInstance().warning("A team was removed. The members will become spectators.");
        //TODO:make people with no teams into spectators(either manually here or implement this as a part of a timer that checks if there are people in the server that are not registered within the manager)
    }

    /**
     * Removes a player with the given UUID from the manager
     * @param id UUID of the player to remove
     */
    public void removePlayer(UUID id)
    {
        BTPlayers.remove(id);
        Logger.getInstance().warning("A player was removed. Doing this might cause issues and a role change is recommended instead.");
    }

    /**
     * Finds and gets all team members of the provided team
     * @param team team to get the members of
     * @return a new list, containing members of the given team
     */
    public List<BTPlayer> getTeamMembers(BTTeam team)
    {
        List<BTPlayer> toReturn = new ArrayList<BTPlayer>();
        for(BTPlayer player : BTPlayers.values())
            if(player.getTeam().equals(team))
                toReturn.add(player);
        return toReturn;
    }

    /**
     * Gets size of the provided team.
     * Should not be used if getTeamMembers gets called in the same method.
     * @param team Team to get the size of
     * @return the size of the team provided
     */
    public int getTeamSize(BTTeam team)
    {
        return getTeamMembers(team).size();
    }

    /**
     * Checks if the size of the provided team matches the maximum team size.
     * Should not be used if getTeamMembers gets called in the same method.
     * @param team Team to validate
     * @return true if the provided team's size is valid, false otherwise
     */
    public boolean isTeamSizeValid(BTTeam team)
    {
        return getTeamSize(team) == maxTeamSize;
    }
    /**
     * Checks if all teams have a valid number of players
     * @return a list of valid teams
     */
    public List<BTTeam> validateTeamsSize()
    {
        List<BTTeam> invalidTeams = new ArrayList<BTTeam>();
        for(BTTeam team : BTTeams.values())
        {
            if(!isTeamSizeValid(team))
            {
                invalidTeams.add(team);
            }
        }
        if(invalidTeams.size() == 0) {
            Logger.getInstance().success(String.format("[TEAM VALIDATION] Successfully validated %d teams",BTTeams.size()));
            return invalidTeams;
        }
        StringBuilder message = new StringBuilder(String.format("[TEAM VALIDATION] %d teams failed validation. Invalid teams:\n",invalidTeams.size()));
        for(BTTeam team : invalidTeams)
            message.append(team.toString()).append('\n');
        Logger.getInstance().error(message.toString().trim());
        return invalidTeams;
    }

    /**
     * Checks if a player with the given UUID exists in this manager
     * @param ID UUID of the player to check
     * @return true if a player is found, false otherwise
     */
    public boolean playerExists(UUID ID)
    {
        return BTPlayers.containsKey(ID);
    }
    /**
     * Checks if a team with the given UUID exists in this manager
     * @param ID UUID of the team to check
     * @return true if a team is found, false otherwise
     */
    public boolean teamExists(UUID ID)
    {
        return BTTeams.containsKey(ID);
    }

    /**
     * Adds a BTPlayer to the manager
     * @param player BTPlayer to add
     * @param addMode enum which specifies in what way the player should be added
     * @return true if successful, false otherwise
     */
    public boolean addBTPlayer(BTPlayer player, AddModeType addMode)
    {
        switch(addMode) {
            case CHECK:
                if(BTPlayers.putIfAbsent(player.getID(),player) == null) {
                    Logger.getInstance().info(String.format("A new player has been registered: %s", player.toString()));
                    ConfigManager.getInstance().setNonParticipants(BTPlayers.values());
                    return true;
                }
                return false;
            case FORCE:
            case REPLACE:
                if(BTPlayers.put(player.getID(),player) == null)
                    Logger.getInstance().warning(String.format("A new player has been registered: %s", player.toString()));
                else
                    Logger.getInstance().info(String.format("Player %1$s was changed to %2$s", player.toString(), player.getClass().getName()));
                ConfigManager.getInstance().setNonParticipants(BTPlayers.values());
                return true;
            default:
                return false;
        }
    }

    /**
     * Sends a message out to all Administrators which have their personal debug value set to a lesser or equal value than the provided level.
     * Level 0 - success
     * Level 1 - info
     * Level 2 - warning
     * Level 3 - error
     * @param message Message to broadcast
     * @param level level of the message to broadcast
     */
    public void broadcastDebug(String message, int level)
    {
        for(BTPlayer player : BTPlayers.values())
        {
            if(!(player instanceof Administrator))
                continue;
            if(!player.isOnline())
                continue;
            if(((Administrator)player).getDebugMessagesLevel() <= level)
                player.getPlayer().sendMessage(message);
        }
    }
    public void applyMinigameSettings()
    {
        for(BTPlayer player : BTPlayers.values())
            MinigameManager.getInstance().getActiveGame().applySettings(player);
    }

    /**
     * Adds the provided team in the given fashion to the manager
     * @param teamToAdd The team to add
     * @param addMode Method to use while adding the team
     * @return true if successful, false otherwise
     */
    public boolean addTeam(BTTeam teamToAdd, AddModeType addMode)
    {
        switch(addMode) {
            case CHECK:
                if(BTTeams.putIfAbsent(teamToAdd.getID(),teamToAdd) == null) {
                    Logger.getInstance().info(String.format("A new team has been registered: %s", teamToAdd.toString()));
                    ConfigManager.getInstance().setTeams(BTTeams.values());
                    return true;
                }
                return false;
            case FORCE:
            case REPLACE:
                BTTeam previousValue = BTTeams.put(teamToAdd.getID(),teamToAdd);
                if(previousValue == null)
                    Logger.getInstance().warning(String.format("A new team has been registered: %s", teamToAdd.toString()));
                else
                    Logger.getInstance().info(String.format("Team %1$s was changed to %2$s", previousValue.toString(), teamToAdd.toString()));
                ConfigManager.getInstance().setTeams(BTTeams.values());
                return true;
            default:
                return false;
        }
    }

}