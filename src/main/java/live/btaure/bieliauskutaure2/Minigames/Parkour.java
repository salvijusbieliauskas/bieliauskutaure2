package live.btaure.bieliauskutaure2.Minigames;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import live.btaure.bieliauskutaure2.BieliauskuTaure2;
import live.btaure.bieliauskutaure2.Chat.ChatMessageManager;
import live.btaure.bieliauskutaure2.Chat.ChatPattern;
import live.btaure.bieliauskutaure2.Chat.LocationType;
import live.btaure.bieliauskutaure2.ConfigManager;
import live.btaure.bieliauskutaure2.Minigames.Data.ParkourCheckpoint;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PermissionType;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.*;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Parkour extends Minigame
{
    private static final World world = Bukkit.getServer().createWorld(new WorldCreator("PARKOUR"));
    private static final World worldNether = Bukkit.getServer().createWorld(new WorldCreator("world_nether"));
    private static final World worldEnd = Bukkit.getServer().createWorld(new WorldCreator("world_the_end"));
    private static final ChatPattern parkourPatternLeft = new ChatPattern(new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0}
    }, LocationType.LEFT);
    private static final ChatPattern parkourPatternRight = new ChatPattern(new int[][]{
            {0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 1, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 1},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0}
    }, LocationType.RIGHT);
    private static final String[][] startupMessages = new String[][]{
            {ChatColor.GREEN + "" + ChatColor.BOLD + "SIMAS BĖGA NUO ŠAUKIMO", "(parkour)"},
            {ChatColor.GREEN + "" + ChatColor.BOLD + "SIMAS BĖGA NUO ŠAUKIMO", "", ChatColor.GREEN + "Šioje rungtyje jūs turėsite greitai bėgti"},
            {ChatColor.GREEN + "" + ChatColor.BOLD + "SIMAS BĖGA NUO ŠAUKIMO", "", ChatColor.GREEN + "Nepamirškite užlipti ant švyturių", ChatColor.GREEN + "Jie išsaugo jūsų vietą ir suteikia taškų"}
    };
    private final HashMap<UUID, ParkourPlayerInfo> playerParkourInfo = new HashMap<UUID, ParkourPlayerInfo>();
    private final List<ParkourCheckpoint> parkourCheckpoints;

    public Parkour()
    {
        super(true, "simas bega", GameMode.SURVIVAL);
        ConfigurationSerialization.registerClass(ParkourCheckpoint.class);
        parkourCheckpoints = loadParkourCheckpoints();

        //parkourCheckpoints = new ArrayList<ParkourCheckpoint>();
        //parkourCheckpoints.add(new ParkourCheckpoint(0, null, getParticipantSpawnLocation(), Arrays.asList(new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 22), new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 22)}), null));
        //parkourCheckpoints.add(new ParkourCheckpoint(1, getParticipantSpawnLocation(), getParticipantSpawnLocation(), Arrays.asList(new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 22), new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 22)}), Arrays.asList(Material.LAVA, Material.RED_STAINED_GLASS)));
    }

    public HashMap<UUID, ParkourPlayerInfo> getPlayerParkourInfo()
    {
        return playerParkourInfo;
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public Location getSpectatorSpawnLocation()
    {
        return new Location(world, 36.5, 44.0, -235.5);
    }

    private Location getParticipantSpawnLocation()
    {
        return new Location(world, 36.4, 42.0, -235.5);
    }

    private List<ParkourCheckpoint> loadParkourCheckpoints()
    {
        Object parkourCheckpoints = BieliauskuTaure2.getPlugin(BieliauskuTaure2.class).getConfig().get("parkourCheckpoints");
        if (parkourCheckpoints == null)
            return new ArrayList<>();
        if (!(parkourCheckpoints instanceof ArrayList<?>))
            return new ArrayList<>();
        return (List<ParkourCheckpoint>) BieliauskuTaure2.getPlugin(BieliauskuTaure2.class).getConfig().get("parkourCheckpoints");
    }

    private void saveParkourCheckpoints()
    {
        BieliauskuTaure2.getPlugin(BieliauskuTaure2.class).getConfig().set("parkourCheckpoints", parkourCheckpoints);
        ConfigManager.getInstance().save();
    }

    private ParkourCheckpoint getCheckpointByIndex(int index)
    {
        for (ParkourCheckpoint checkpoint : parkourCheckpoints)
        {
            if (checkpoint.index == index)
                return checkpoint;
        }
        return null;
    }

    @Override
    public void applySettings(BTPlayer player)
    {
        if (!super.performChecks(player))
            return;
        Player bukkitPlayer = player.getPlayer();
        bukkitPlayer.setGameMode(super.getGameMode());
        player.clearPotionEffects();
        if (!playerParkourInfo.containsKey(player.getID()))
            playerParkourInfo.put(player.getID(), new ParkourPlayerInfo(getCheckpointByIndex(0)));
        bukkitPlayer.addPotionEffects(playerParkourInfo.get(player.getID()).getPotionEffects());
    }

    @Override
    public void teleportParticipant(BTPlayer player)
    {
        if (!playerParkourInfo.containsKey(player.getID()))
            playerParkourInfo.put(player.getID(), new ParkourPlayerInfo(getCheckpointByIndex(0)));
        Location tpLocation = new Location(playerParkourInfo.get(player.getID()).getCheckpointLocation().getWorld(), playerParkourInfo.get(player.getID()).getCheckpointLocation().getBlockX() + 0.5, playerParkourInfo.get(player.getID()).getCheckpointLocation().getBlockY(), playerParkourInfo.get(player.getID()).getCheckpointLocation().getBlockZ() + 0.5);
        tpLocation = tpLocation.setDirection(player.getPlayer().getLocation().getDirection());
        player.teleport(tpLocation);
    }

    @Override
    public boolean init()
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
        PlayerManager.getInstance().teleportParticipants(getParticipantSpawnLocation(), false);
        ChatMessageManager.getInstance().broadcastMessagesWithDelay(startupMessages, Sound.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, new ChatPattern[]{parkourPatternLeft, parkourPatternRight});
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
        if (!playerParkourInfo.containsKey(playerID))
            return new ArrayList<String>();
        return new ArrayList<String>()
        {{
            add(ChatColor.GOLD + "" + ChatColor.BOLD + "CHECKPOINT: " + ChatColor.WHITE + ChatColor.BOLD + playerParkourInfo.get(playerID).getCheckpointIndex() + "/" + (parkourCheckpoints.size() - 1));
        }};
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())
            return;
        if (e.getTo().getBlockX() < -4 && e.getTo().getBlockY() >= 53 && e.getTo().getBlockZ() > 25)
        {
            e.setTo(new Location(worldNether, 195.5, 43.1, -405.5, e.getTo().getYaw() + 90, e.getTo().getPitch()));
            return;
        }
        if (!e.getPlayer().getGameMode().equals(GameMode.SURVIVAL))
            return;
        if (e.getTo().getBlockY() < 34)
        {
            teleportParticipant(PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()));
            return;
        }
        if (MinigameManager.getInstance().getStage() == 0 && e.getTo().getBlockZ() > -232)
        {
            e.setTo(getParticipantSpawnLocation());
            return;
        }
        if (!playerParkourInfo.containsKey(e.getPlayer().getUniqueId()))
            return;
        ParkourCheckpoint standingCheckpoint = GetStandingCheckpoint(e.getPlayer());
        if (standingCheckpoint == null)
            return;
        ParkourPlayerInfo playerInfo = playerParkourInfo.get(e.getPlayer().getUniqueId());
        if (playerInfo.currentCheckpoint.index >= standingCheckpoint.index)
            return;
        playerInfo.currentCheckpoint = standingCheckpoint;
        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
        PlayerManager.getInstance().getBTPlayer(e.getPlayer()).updateScoreboard();

        /*if(CustomBlockData.hasCustomBlockData(e.getTo().clone().subtract(0,1,0).getBlock(),BieliauskuTaure2.getPlugin(BieliauskuTaure2.class)))
        {
            CustomBlockData data = new CustomBlockData(e.getTo().clone().subtract(0,1,0).getBlock(),BieliauskuTaure2.getPlugin(BieliauskuTaure2.class));
            int checkpointNum = data.get(new NamespacedKey(BieliauskuTaure2.getPlugin(BieliauskuTaure2.class),"checkpoint"), PersistentDataType.INTEGER);
            if(playerParkourInfo.get(e.getPlayer().getUniqueId()).getCheckpointIndex() == checkpointNum-1) {
                playerParkourInfo.get(e.getPlayer().getUniqueId()).setCheckpointIndex(checkpointNum);
                playerParkourInfo.get(e.getPlayer().getUniqueId()).setCheckpointLocation(e.getTo());
                SoundManager.getInstance().playSound(PlayerManager.getInstance().getBTPlayer(e.getPlayer()), SoundManager.getInstance().parkourCheckpointSound);
                PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()).updateScoreboard();
                applySettings(e.getPlayer());
                return;
            }
        }*/

    }

    private ParkourCheckpoint GetStandingCheckpoint(Player player)
    {
        Location playerLocation = player.getLocation();
        for (ParkourCheckpoint checkpoint : parkourCheckpoints)
        {
            Location checkpointBlockLocation = checkpoint.checkpointLocation;
            if (checkpointBlockLocation.getBlockX() == playerLocation.getBlockX() &&
                    checkpointBlockLocation.getBlockY() == playerLocation.getBlockY() &&
                    checkpointBlockLocation.getBlockZ() == playerLocation.getBlockZ())
                return checkpoint;
        }

        return null;
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
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player damager))
        {
            e.setCancelled(true);
            return;
        }
        if (PlayerManager.getInstance().getBTPlayer(damager.getUniqueId()).getPermissions().get(PermissionType.DAMAGE_ENTITIES))
        {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {
        if (PlayerManager.getInstance().getBTPlayer(e.getPlayer().getUniqueId()).getPermissions().get(PermissionType.PLACE_BLOCKS))
            return;
        e.setCancelled(true);
    }

    class ParkourPlayerInfo
    {
        private ParkourCheckpoint currentCheckpoint;

        public ParkourPlayerInfo(ParkourCheckpoint currentCheckpoint)
        {
            this.currentCheckpoint = currentCheckpoint;
        }

        public Location getCheckpointLocation()
        {
            return currentCheckpoint.checkpointLocation;
        }

        public int getCheckpointIndex()
        {
            return currentCheckpoint.index;
        }

        public List<PotionEffect> getPotionEffects()
        {
            return this.currentCheckpoint.activePotionEffects;
            /*if(checkpointIndex<3)
            {
                return new ArrayList<PotionEffect>()
                {
                    {
                        add(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,22));
                        add(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,22));
                    }
                };
            }
            if(checkpointIndex<6)
            {
                return new ArrayList<PotionEffect>()
                {
                    {
                        add(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,22));
                    }
                };
            }
            if(checkpointIndex<8)
            {
                return new ArrayList<PotionEffect>()
                {
                    {
                        add(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,22));
                        add(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,4));
                    }
                };
            }
            if(checkpointIndex<20)
            {
                return new ArrayList<PotionEffect>()
                {
                    {
                        add(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,21));
                        add(new PotionEffect(PotionEffectType.JUMP,Integer.MAX_VALUE,3));
                    }
                };
            }
            return new ArrayList<PotionEffect>();*/
        }
    }
}