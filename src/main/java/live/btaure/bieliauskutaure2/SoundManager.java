package live.btaure.bieliauskutaure2;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import live.btaure.bieliauskutaure2.Participants.Streamer;
import org.bukkit.Location;
import org.bukkit.Sound;

public class SoundManager
{
    private static SoundManager soundManagerInstance = null;
    public final Sound parkourCheckpointSound = Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR;
    public final Sound chatNotificationSound = Sound.ENTITY_ENDER_DRAGON_DEATH;

    private SoundManager()
    {

    }

    public static SoundManager getInstance()
    {
        if (soundManagerInstance == null)
            soundManagerInstance = new SoundManager();
        return soundManagerInstance;
    }

    public void playSoundGlobally(Sound soundToPlay)
    {
        for (BTPlayer player : PlayerManager.getInstance().getBTPlayers())
        {
            playSound(player, soundToPlay);
        }
    }

    public void playSound(BTPlayer player, Sound soundToPlay)
    {
        if (!player.isOnline())
            return;
        playSound(player, soundToPlay, player.getPlayer().getLocation());
    }

    public void playSound(BTPlayer player, Sound soundToPlay, Location loc)
    {
        if (!player.isOnline())
            return;
        if (player instanceof Streamer && ((Streamer) player).isSoundEffectsMuted())
            return;
        player.getPlayer().playSound(player.getPlayer(), soundToPlay, 1.0f, 1.0f);
    }
}
