package live.btaure.bieliauskutaure2.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class GlobalWeatherChangeListener implements Listener
{
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e)
    {
        if (e.getCause().equals(WeatherChangeEvent.Cause.COMMAND) || e.getCause().equals(WeatherChangeEvent.Cause.PLUGIN))
            return;
        e.setCancelled(true);
    }
}
