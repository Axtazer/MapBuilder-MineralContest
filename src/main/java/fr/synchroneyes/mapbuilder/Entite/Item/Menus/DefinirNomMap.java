package fr.synchroneyes.mapbuilder.Entite.Item.Menus;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Entite.Item.Item;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DefinirNomMap extends Element {
    @Override
    public String getNom() {
        return "Définir le nom de map";
    }

    @Override
    public String getDescription() {
        return "Définir le nom de map";
    }

    @Override
    public Material getIcone() {
        return Material.BELL;
    }

    @Override
    public void onSelected(Player cible) {

        ActionManager manager = Main.getInstance().getActionManager();

        manager.setCurrentAction(this, cible, AsyncPlayerChatEvent.class, cible);
        cible.closeInventory();
        cible.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Veuillez entrer dans le chat le nom de la map, sans espace et sans accents");

    }

    @Override
    public void onOptionSelected(Event evenement) {
        AsyncPlayerChatEvent event = (AsyncPlayerChatEvent) evenement;
        event.setCancelled(true);
        String message = event.getMessage();

        if(message.matches("^([a-zA-Z0-9\\_\\-]*)$")){
            event.getPlayer().sendMessage("Le nom de la map a été défini sur: " + event.getMessage());
            Main.getInstance().setNomMap(event.getMessage());
            Main.getInstance().getActionManager().setCurrentActionOver();
        }else {
            event.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Nom de map invalide. Veuillez réessayer");
        }


    }
}
