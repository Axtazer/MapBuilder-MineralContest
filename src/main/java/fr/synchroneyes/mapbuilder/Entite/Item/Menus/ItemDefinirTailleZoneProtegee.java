package fr.synchroneyes.mapbuilder.Entite.Item.Menus;

import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import fr.synchroneyes.mapbuilder.Managers.AreneManager;
import fr.synchroneyes.mapbuilder.Managers.BuildMenuManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class ItemDefinirTailleZoneProtegee extends Element {

    private boolean displayZone = true;

    @Override
    public void onSelected(Player cible) {
        ActionManager manager = Main.getInstance().getActionManager();
        manager.setCurrentAction(this, null, PlayerInteractEvent.class, cible);
        cible.sendMessage(ChatColor.GREEN + "Pour savoir comment utiliser l'outil; regarder la description de l'objet");
        BuildMenuManager manager1 = Main.getInstance().getBuildMenuManager();
        manager1.closeInventory();
        cible.closeInventory();
        cible.getInventory().setItemInMainHand(getItem());
    }

    private ItemStack getItem() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName("Définir taille zone jouable");
        List<String> description = new LinkedList<>();
        description.add("Pour définir la taille de la zone jouable");
        description.add("Cliquez (GAUCHE) sur un bloc au sol pour définir la taille de la zone");
        description.add("Cliquez (GAUCHE) dans les air pour afficher/cacher la limite");
        description.add("Cliquez (DROIT) pour valider la zone");
        meta.setLore(description);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public void onOptionSelected(Event evenement) {
        PlayerInteractEvent event = (PlayerInteractEvent) evenement;
        // On regarde si le coffre a été définit
        AreneManager manager = Main.getInstance().getAreneManager();
        if(getItem().equals(event.getPlayer().getInventory().getItemInMainHand())){
            if(manager.getArene().getChestLocation() == null) {
                event.getPlayer().sendMessage("Vous devez d'abord définir la position du coffre d'arène");
                event.setCancelled(true);
                Main.getInstance().getActionManager().setCurrentActionOver();
                event.getPlayer().getInventory().getItemInMainHand().setAmount(0);
                return;
            }


            if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Location clickedBlock = event.getClickedBlock().getLocation();
                Main.getInstance().setTaille_zone((int) clickedBlock.distance(manager.getArene().getChestLocation())*2);
                event.getPlayer().sendMessage("Taille de la zone protégée définit!");
                event.setCancelled(true);
            } else if(event.getAction() == Action.LEFT_CLICK_AIR) {
                World monde = event.getPlayer().getWorld();
                monde.getWorldBorder().setCenter(manager.getArene().getChestLocation());
                if(displayZone){
                    monde.getWorldBorder().setSize(Main.getInstance().getTaille_zone()*2);
                    Bukkit.broadcastMessage("Affichage de la zone protegée " + ChatColor.GREEN + "activée");
                } else {
                    monde.getWorldBorder().setSize(10000000);
                    Bukkit.broadcastMessage("Affichage de la zone protegée " + ChatColor.RED + "désactivée");
                }

                displayZone = !displayZone;
            }
        }


    }

    @Override
    public String getNom() {
        return "Définir la taille de la zone jouable";
    }

    @Override
    public String getDescription() {
        return "Permet de définir la taille de la zone jouable";
    }

    @Override
    public Material getIcone() {
        return Material.BARRIER;
    }
}
