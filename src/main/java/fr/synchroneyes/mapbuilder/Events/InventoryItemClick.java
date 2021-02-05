package fr.synchroneyes.mapbuilder.Events;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryItemClick implements Listener {

    @EventHandler
    public void onItemClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null && event.getWhoClicked() instanceof Player) {

            ItemStack clickedItem = event.getCurrentItem();

            // ON récupère le manager
            BuildMenuManager manager = Main.getInstance().getBuildMenuManager();

            // On regarde si l'item appartient au menu ou non
            if(manager.contains(clickedItem)) {
                Item element_menu = manager.getFromItemStack(clickedItem);
                //Bukkit.broadcastMessage("[" + element_menu.getNom() + "] Item appartenant à un menu !");

                // On applique la logique
                if(element_menu instanceof Menu) {
                    manager.switchMenu((Menu) element_menu, true);
                } else {
                    ((Element)element_menu).onSelected((Player)event.getWhoClicked());
                }

                event.setCancelled(true);
            }


        }
    }
}
