package live.btaure.bieliauskutaure2.Listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import live.btaure.bieliauskutaure2.Participants.BTPlayer;
import live.btaure.bieliauskutaure2.Participants.PermissionType;
import live.btaure.bieliauskutaure2.Participants.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Singleton that handles everything associated with chat.
 */
public class GlobalChatListener implements Listener
{
    private static GlobalChatListener globalChatListenerInstance = null;
    private GlobalChatListener()
    {
    }
    public GlobalChatListener getInstance()
    {
        if(globalChatListenerInstance == null)
            globalChatListenerInstance = new GlobalChatListener();
        return globalChatListenerInstance;
    }
    private boolean chatEnabled = true;
    @EventHandler
    public void onPlayerChat(AsyncChatEvent e)
    {
        if(chatEnabled)
            return;
        BTPlayer player = PlayerManager.getInstance().getBTPlayer(e.getPlayer());
        if(player.getPermissions().get(PermissionType.BYPASS_CHAT))
            return;
        e.setCancelled(true);
    }
}
