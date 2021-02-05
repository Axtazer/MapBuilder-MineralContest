package fr.synchroneyes.mapbuilder.Events;

import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClose implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getPlayer() instanceof Player) {
            // On regarde si l'inventaire est celui du menumanager
            BuildMenuManager manager = Main.getInstance().getBuildMenuManager();

            if(manager.equalsInventory(event.getInventory())) {
                manager.closeInventory();
            }
        }
    }
}
