package live.btaure.bieliauskutaure2.Participants;

import live.btaure.bieliauskutaure2.Minigames.Lobby;
import live.btaure.bieliauskutaure2.Minigames.MinigameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;


public abstract class BTPlayer implements ConfigurationSerializable {

    private OfflinePlayer player;
    public final String roleName;
    public final ChatColor roleColor;

    public BTPlayer(UUID playerID, BTTeam team, String roleName, ChatColor roleColor)
    {
        this.player = Bukkit.getOfflinePlayer(playerID);
        this.team = team;
        this.roleName = roleName;
        this.roleColor = roleColor;
    }
    public abstract HashMap<PermissionType,Boolean> getPermissions();

    public OfflinePlayer getOfflinePlayer()
    {
        return player;
    }
    public Player getPlayer()
    {
        return Bukkit.getPlayer(this.getID());
    }
    private BTTeam team = null;
    public BTTeam getTeam()
    {
        return team;
    }
    /**
     * @return java.util.UUID of the associated player
     */
    public UUID getID()
    {
        return player.getUniqueId();
    }

    /**
     * @return the name of the associated player
     */
    public String getName()
    {
        return getOfflinePlayer().getName();
    }
    public void updateScoreboard(){//TODO:run this code a million times to check if it doesn't fill up memory
        if(!isOnline()) {
            return;
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("individual","dummy", ChatColor.GOLD+""+ChatColor.BOLD+"BIELIAUSKŲ TAURĖ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score roleName = objective.getScore(this.roleColor+""+ChatColor.BOLD+this.roleName);
        roleName.setScore(15);
        Score newLine = objective.getScore(" ");
        newLine.setScore(14);
        if(getTeam()!=null)
        {
            Score activeMinigameText = objective.getScore(ChatColor.BOLD + "Jūsų komanda: "+ChatColor.RESET+ChatColor.GREEN+getTeam().getName());
            activeMinigameText.setScore(13);
            Score activeMinigameName = objective.getScore(ChatColor.BOLD+ "Taškai: "+ChatColor.GOLD+getTeam().getScore());
            activeMinigameName.setScore(12);
            if(MinigameManager.getInstance().getActiveGame() != null && !(MinigameManager.getInstance().getActiveGame() instanceof Lobby)) {
                Score newLine2 = objective.getScore("  ");
                newLine2.setScore(11);
            }
        }
        if(MinigameManager.getInstance().getActiveGame() != null && !(MinigameManager.getInstance().getActiveGame() instanceof Lobby)) {
            Score activeMinigameText = objective.getScore(ChatColor.BOLD + "Minigame: "+ChatColor.RESET+MinigameManager.getInstance().getActiveGame().name);
            activeMinigameText.setScore(10);
            if(this instanceof Participant) {
                int x = 9;
                for (String s : MinigameManager.getInstance().getActiveGame().getScoreboardContent(getID())) {
                    Score activeMinigameScoreboard = objective.getScore(s);
                    activeMinigameScoreboard.setScore(x);
                    x--;
                }
            }
        }
        getPlayer().setScoreboard(board);
    }

    public void clearPotionEffects()
    {
        if(!isOnline())
            return;
        for(PotionEffect potionEffect : getPlayer().getActivePotionEffects())
            getPlayer().removePotionEffect(potionEffect.getType());
    }


    /**
     * teleports this player to a specific location
     *
     * @return true if successful, false otherwise
     */
    public boolean teleport(Location loc)
    {
        if (!isOnline()) return false;
        getPlayer().teleport(loc);
        return true;
    }

    public boolean isOnline()
    {
        return player.isOnline();
    }

    @Override
    public String toString()
    {
        return String.format("%1$s (%2$s)", getName(), getID().toString());
    }
}
