package fr.synchroneyes.mapbuilder.Events;

import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerChatEvent implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer() instanceof Player) {
            ActionManager manager = Main.getInstance().getActionManager();
            if(manager.shouldFireEvent(event.getClass(), event.getPlayer())){
                manager.getCurrentAction().onOptionSelected(event);
            }
        }
    }
}
