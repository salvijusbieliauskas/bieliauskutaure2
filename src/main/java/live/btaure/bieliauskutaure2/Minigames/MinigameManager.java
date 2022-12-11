package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Participants.Logger;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.Listener;

public class MinigameManager
{
    private Minigame activeGame = null;
    private static MinigameManager minigameManagerInstance = null;
    private int stage;
    private MinigameManager()
    {
        Bukkit.getScheduler().runTask(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class), new Runnable() {
            @Override
            public void run()
            {
                initWithoutChecks(new Lobby());
            }
        });
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
     * if this method returns false something has gone horribly wrong.
     * @param minigame the minigame to start
     * @return true if initialization was successful, otherwise false
     */
    public boolean init(Minigame minigame)
    {
        if(!(activeGame instanceof Lobby))//lobby does not use begin, end or reset.
        {
            Logger.getInstance().warning("A minigame initialization attempt was made, but the current active game was not Lobby.");
            return false;
        }
        if(minigame instanceof Lobby)
        {
            Logger.getInstance().warning("Tried to initialize Lobby while Lobby was active.");
            return false;
        }
        return initWithoutChecks(minigame);
    }
    private boolean initWithoutChecks(Minigame minigame)
    {
        if(activeGame instanceof Lobby)//lobby does not use begin, end or reset.
            endWithoutChecks();
        this.activeGame = minigame;
        this.stage = 0;
        boolean success =  this.activeGame.init();
        if(!success) {
            Logger.getInstance().error(String.format("Minigame %s failed to initialize", minigame.toString()));
            return false;
        }
        PlayerManager.getInstance().teleportSpectators(this.activeGame.getSpectatorSpawnLocation());//participants must be teleported by the minigame itself
        PlayerManager.getInstance().applyMinigameSettings();
        PlayerManager.getInstance().updateScoreboards();
        Logger.getInstance().success(String.format("Minigame %s initialized successfully",minigame.toString()));
        return success;
    }
    //begin should only be called manually, once all players are ready and have loaded in to avoid blockparty incident
    //portion of players who have loaded in could be (approximately) measured by listening to their movement(moving their mouse slightly or moving should call the PlayerMove event). the implementation of this has potential to destroy performance, so it must be as well optimized as possible
    //TODO:this method also does nothing
    /**
     * begins the minigame by starting needed listeners, releasing players from a confined space etc.
     * does nothing if called while lobby is active
     * @return true if starting the minigame was successful, otherwise false
     */
    public boolean begin()
    {
        if(this.stage!=0)
        {
            Logger.getInstance().warning("A minigame start attempt was made, but the minigame was not initialized.");
            return false;
        }
        this.stage = 1;
        return this.activeGame.begin();
    }
//TODO:implement methods for setting gamemodes and other things in order to exclude admins and spectators
//TODO:i will probably add a class that extends Player in order to contain participants, however i should make an abstract  custom player class and make separate classes for participants, spectators, administrators and streamers?(could be mered with spectators, though they should have admin rights?)
    /**
     * Ends the minigame, setting everyone's gamemode to spectator, calculating scores, displaying title on screen (and in chat) that shows the winners and stopping any active listeners or other handlers. Marks the minigame as inactive.
     * @return true if the minigame was ended successfully, false otherwise
     */
    public boolean end()
    {
        if(this.stage!=1)
        {
            Logger.getInstance().warning("A minigame end attempt was made, but the minigame was not yet started.");
            return false;
        }
        return endWithoutChecks();
    }
    private boolean endWithoutChecks()
    {
        this.activeGame.setGameMode(GameMode.SPECTATOR);//TODO:this code should be in the minigame itself
        this.stage = 2;
        return activeGame.end();
    }
//TODO:the reset method should check if the minigame is active(in case end was not called before) and call End in order to remove any issues that could arise from destroying a class instance that is listening for events
    //TODO:fix this ass javadoc
    //TODO:the reset method should act on its own and not call anything within the minigame
    //TODO:this method does not actually do anything
    /**
     * Resets the manager to its original state by setting activeGame to lobby, teleports all players to the lobby and resets their potionEffects, scoreboards, gamemodes etc.
     * @return true if the minigame was reset successfully, false otherwise
     */
    public boolean reset()
    {
        if(this.stage != 2)
        {
            Logger.getInstance().warning("A minigame reset was called, but the previous one has not ended yet. Call end() first.");
            return false;
        }
        initWithoutChecks(new Lobby());
        return true;
    }
    public boolean advance()
    {
        if(activeGame instanceof Lobby)
        {
            Logger.getInstance().warning("A minigame advance was called, but the active minigame was Lobby.");
            return false;
        }
        boolean success;
        switch(this.stage)
        {
            case 0:
                success = begin();
                if(success)
                {
                    Logger.getInstance().success("The minigame was started successfully.");
                    return true;
                }
                Logger.getInstance().error("The minigame was not started due to an unknown error.");
                return false;
            case 1:
                success = end();
                if(success)
                {
                    Logger.getInstance().success("The minigame was ended successfully.");
                    return true;
                }
                Logger.getInstance().error("The minigame was not ended due to an unknown error.");
                return false;
            case 2:
                success = reset();
                if(success) {
                    Logger.getInstance().success("The minigame was reset to lobby due to advancement.");
                    return true;
                }
                Logger.getInstance().error("The minigame was not reset due to an unknown error.");
                return false;
            default:
                Logger.getInstance().error("The minigame manager is not in a valid state for advancement.");
                return false;
        }
    }
    public int getStage()
    {
        return stage;
    }
    @Override
    public String toString()
    {
        return String.format("Active minigame: %1$s; Stage: %2$s",getActiveGame().name,String.valueOf(this.stage));
    }
}
