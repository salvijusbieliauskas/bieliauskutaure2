package live.btaure.bieliauskutaure2.Minigames;

import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;

public class Parkour extends Minigame{
    private World world = Bukkit.getWorld("PARKOUR");
    public Parkour()
    {
        super(true, "simas bega nuo saukimo", GameMode.SURVIVAL);
    }

    @Override
    public World getWorld()
    {
        return null;
    }

    @Override
    public void applySettings(BTPlayer player)
    {

    }
}
