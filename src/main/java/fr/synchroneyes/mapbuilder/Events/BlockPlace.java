package fr.synchroneyes.mapbuilder.Events;

import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace implements Listener {

    @EventHandler
    public void onBlockAdded(BlockPlaceEvent event) {
        ActionManager manager = Main.getInstance().getActionManager();
        if(manager.shouldFireEvent(event.getClass(), event.getPlayer())){
            manager.getCurrentAction().onOptionSelected(event);
        }
    }

}
