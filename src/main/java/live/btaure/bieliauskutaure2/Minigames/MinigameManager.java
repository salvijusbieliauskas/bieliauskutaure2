package live.btaure.bieliauskutaure2.Minigames;

public class MinigameManager
{
    private Minigame activeGame = null;
    private static MinigameManager minigameManagerInstance = null;
    private MinigameManager()
    {

    }
    public Minigame getActiveGame()
    {
        return activeGame;
    }
    public static MinigameManager getInstance()
    {
        if(minigameManagerInstance == null)
            minigameManagerInstance = new MinigameManager();
        return minigameManagerInstance;
    }

    //TODO:clean up comments that are unnecessary
    //most of these methods will be calling the activeGame initialization, begin and end methods instead of doing everything here
    /**
     * Initializes the minigame, teleporting all players to the appropriate world, setting an appropriate gamemode, applying permanent potion effects, resource pack, scoreboard etc.
     * if this method returns false something has gone horribly wrong
     * @param minigame the minigame to start
     * @return true if initialization was successful, otherwise false
     */
    public boolean Init(Minigame minigame)
    {
        return true;
    }
    //begin should only be called manually, once all players are ready and have loaded in to avoid blockparty incident
    //portion of players who have loaded in could be (approximately) measured by listening to their movement(moving their mouse slightly or moving should call the PlayerMove event). the implementation of this has potential to destroy performance, so it must be as well optimized as possible
    /**
     * begins the minigame by starting needed listeners, releasing players from a confined space(
     * @return true if starting the minigame was successful, otherwise false
     */
    public boolean Begin()
    {
        return true;
    }
//TODO:implement methods for setting gamemodes and other things in order to exclude admins and spectators
//TODO:i will probably add a class that extends Player in order to contain participants, however i should make an abstract  custom player class and make separate classes for participants, spectators, administrators and streamers?(could be mered with spectators, though they should have admin rights?)
    /**
     * Ends the minigame, setting everyone's gamemode to spectator, calculating scores, displaying title on screen (and in chat) that shows the winners and stopping any active listeners or other handlers. Marks the minigame as inactive.
     * @return true if the minigame was ended successfully, false otherwise
     */
    public boolean End()
    {
        return true;
    }
//TODO:the reset method should check if the minigame is active(in case end was not called before) and call End in order to remove any issues that could arise from destroying a class instance that is listening for events
    //TODO:fix this ass javadoc
    //TODO:the reset method should act on its own and not call anything within the minigame
    /**
     * Resets the manager to its original state by setting activeGame to null(or lobby?), teleports all players to the lobby and resets their potionEffects, scoreboards, gamemodes etc.
     * @return true if the minigame was reset successfully, false otherwise
     */
    public boolean Reset()
    {
        activeGame = null;
        return true;
    }
}
