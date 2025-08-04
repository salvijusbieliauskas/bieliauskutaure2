package live.btaure.bieliauskutaure2.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class GlobalWorldLoadListener implements Listener
{
    @EventHandler
    public void onWorldLoad(WorldLoadEvent event)
    {
        event.getWorld().setClearWeatherDuration(Integer.MAX_VALUE);
    }
}
