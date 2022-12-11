package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.jeff_media.customblockdata.CustomBlockData;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Participants.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

public class Parkour extends Minigame{
    private static final World world = Bukkit.getServer().createWorld(new WorldCreator("PARKOUR"));
    private static final World worldNether = Bukkit.getServer().createWorld(new WorldCreator("world_nether"));
    private static final World worldEnd = Bukkit.getServer().createWorld(new WorldCreator("world_the_end"));
    private final HashMap<UUID,Integer> playerCheckpointIndexes = new HashMap<UUID,Integer>();
    private final HashMap<UUID,Location> playerCheckpointLocations = new HashMap<UUID,Location>();
    public Parkour()
    {
        super(true, "simas bega", GameMode.SURVIVAL);
    }
    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public Location getSpectatorSpawnLocation()
    {
        return new Location(world,36.5,44.0,-235.5);
    }
    private Location getParticipantSpawnLocation()
    {
        return new Location(world, 36.4,42.0,-235.5);
    }

    @Override
    public void applySettings(BTPlayer player)
    {
        if(!super.performChecks(player))
            return;
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,22));
        bukkitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,22));
        bukkitPlayer.setGameMode(super.getGameMode());
        if(!playerCheckpointIndexes.containsKey(player.getID()))
            playerCheckpointIndexes.put(player.getID(),0);
        if(!playerCheckpointLocations.containsKey(player.getID()))
            playerCheckpointLocations.put(player.getID(),getParticipantSpawnLocation());

    }

    @Override
    public void teleportParticipant(BTPlayer player)
    {
        if(!playerCheckpointIndexes.containsKey(player.getID()))
        {
            playerCheckpointIndexes.put(player.getID(),0);
            playerCheckpointLocations.put(player.getID(),getParticipantSpawnLocation());
        }
        player.teleport(playerCheckpointLocations.get(player.getID()));
    }

    @Override
    public boolean init()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
        PlayerManager.getInstance().teleportParticipants(getParticipantSpawnLocation(),false);
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
        PlayerMoveEvent.getHandlerList().unregister(this);
        BlockDestroyEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
        EntityDamageEvent.getHandlerList().unregister(this);
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
        return true;
    }

    @Override
    public List<String> getScoreboardContent(UUID playerID)
    {
        return new ArrayList<String>(){{
           add(ChatColor.GOLD+""+ChatColor.BOLD+"CHECKPOINT: "+ChatColor.WHITE+""+ChatColor.BOLD+playerCheckpointIndexes.get(playerID)+"/"+15);
        }};
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
            return;
        if(!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
            return;
        if (e.getTo().getBlockY() < 34) {
            teleportParticipant(PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()));
            return;
        }
        if(MinigameManager.getInstance().getStage() == 0 && e.getTo().getBlockZ()>-232)
        {
            e.setTo(getParticipantSpawnLocation());
            return;
        }
        if(CustomBlockData.hasCustomBlockData(e.getTo().clone().subtract(0,1,0).getBlock(),BieliauskuTaure2.getPlugin(BieliauskuTaure2.class)))
        {
            CustomBlockData data = new CustomBlockData(e.getTo().clone().subtract(0,1,0).getBlock(),BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            int checkpointNum = data.get(new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint"), PersistentDataType.INTEGER);
            if(!playerCheckpointIndexes.containsKey(e.getPlayer().getUniqueId()))
            {
                playerCheckpointIndexes.put(e.getPlayer().getUniqueId(),0);
            }
            if(playerCheckpointIndexes.get(e.getPlayer().getUniqueId()) == checkpointNum-1) {
                playerCheckpointIndexes.put(e.getPlayer().getUniqueId(), checkpointNum);
                playerCheckpointLocations.put(e.getPlayer().getUniqueId(),e.getTo());
                e.getPlayer().playSound(e.getPlayer().getLocation(),Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR,1.0f,1.0f);
                PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()).updateScoreboard();
                return;
            }
        }
        if(!playerCheckpointIndexes.containsKey(e.getPlayer().getUniqueId()))
            return;
        if(e.getTo().getBlockX() < -4 && playerCheckpointIndexes.get(e.getPlayer().getUniqueId())==3) {
            e.setTo(new Location(worldNether, 195.5, 43.1, -405.5,180,0));
            return;
        }

    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId());
        if(player.getPermissions().get(PermissionType.BREAK_BLOCKS))
            return;
        e.setCancelled(true);
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player)
            e.setCancelled(true);
    }
    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e)
    {
        if(!(e.getDamager() instanceof Player damager))
        {
            e.setCancelled(true);
            return;
        }
        if(PlayerManager.getInstance().getBTPlayer(damager.getUniqueId()).getPermissions().get(PermissionType.DAMAGE_ENTITIES))
        {
            return;
        }
        e.setCancelled(true);
        return;
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if(PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()).getPermissions().get(PermissionType.PLACE_BLOCKS))
            return;
        e.setCancelled(true);
    }
}