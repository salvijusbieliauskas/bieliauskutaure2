package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PermissionType;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby extends Minigame implements Listener
{

    public Lobby()
    {
        super(true, "Lobby", GameMode.SURVIVAL);
    }

    private static World createWorld()
    {
        World w = Bukkit.getServer().createWorld(new WorldCreator("NEWLOBBY"));

        w.setSpawnLocation(getSpawnLocation());

        return w;
    }

    private static Location getSpawnLocation()
    {
        return (new Location(world, 13.5f, 33.0f, -24.5f));
    }    private static final World world = createWorld();

    @Override
    public Location getSpectatorSpawnLocation()
    {
        return (new Location(world, 13.5f, 61.0f, -24.5f));
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    /**
     * This method applies the scoreboard, gamemode, potion effects and other settings to the given player.
     * This method can be used as a player reset.
     *
     * @param player player to apply effects to
     */
    @Override
    public void applySettings(BTPlayer player)
    {
        if (!super.performChecks(player))
            return;
        Player bukkitPlayer = player.getPlayer();
        for (PotionEffect pe : bukkitPlayer.getActivePotionEffects())
            bukkitPlayer.removePotionEffect(pe.getType());
        bukkitPlayer.setGameMode(super.getGameMode());
    }

    @Override
    public void teleportParticipant(BTPlayer player)
    {
        player.teleport(getSpawnLocation());
    }

    @Override
    public boolean init()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
        PlayerManager.getInstance().teleportParticipants(getSpawnLocation(), false);
        return true;
    }

    @Override
    public boolean begin()
    {
        return true;
    }

    @Override
    public boolean end()
    {
        BlockDestroyEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
        return true;
    }

    @Override
    public List<String> getScoreboardContent(UUID playerID)
    {
        return new ArrayList<String>();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId());
        if (player.getPermissions().get(PermissionType.BREAK_BLOCKS))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (e.getEntity() instanceof Player)
        {
            e.setCancelled(true);
            e.setDamage(0);
        }
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player damager))
        {
            e.setCancelled(true);
            e.setDamage(0);
            return;
        }
        if (PlayerManager.getInstance().getBTPlayer(damager.getUniqueId()).getPermissions().get(PermissionType.DAMAGE_ENTITIES))
        {
            return;
        }
        e.setDamage(0);
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()).getPermissions().get(PermissionType.PLACE_BLOCKS))
            return;
        e.setCancelled(true);
    }




}
