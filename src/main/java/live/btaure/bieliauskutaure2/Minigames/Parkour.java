package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Parkour extends Minigame{
    private static World world = Bukkit.getServer().createWorld(new WorldCreator("PARKOUR"));
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
    public void applySettings(BTPlayer player)
    {

    }

    @Override
    public void teleportParticipant(BTPlayer player)
    {

    }

    @Override
    public void teleportSpectator(BTPlayer player)
    {

    }
}
