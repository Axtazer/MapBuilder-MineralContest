package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Element;
import fr.synchroneyes.mapbuilder.Main;
import fr.synchroneyes.mapbuilder.Managers.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class ItemDefinirPorte extends Element {

    private Equipe equipe;

    public ItemDefinirPorte(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override
    public void onSelected(Player cible) {
        ActionManager manager = Main.getInstance().getActionManager();

        manager.setCurrentAction(this, equipe, PlayerInteractEvent.class, cible);
        cible.getInventory().setItemInMainHand(getItemAjoutPorte());


    }

    @Override
    public void onOptionSelected(Event evenement) {
        PlayerInteractEvent event = (PlayerInteractEvent) evenement;
        Player joueur = event.getPlayer();

        // Si le joueur tient l'item de gestion de porte dans sa main
        if(joueur.getInventory().getItemInMainHand().equals(getItemAjoutPorte())) {

            if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
                // Si on clic gauche
                // On récupère le bloc
                Location clickedBloc = event.getClickedBlock().getLocation();
                boolean containsBlock = equipe.getBlocksPorte().contains(clickedBloc);
                // On regarde si il est dans la liste
                if(containsBlock) {
                    equipe.getBlocksPorte().remove(clickedBloc);
                    joueur.sendMessage("[" + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + "]" + ChatColor.RED + "- Bloc supprimé (nb_bloc: " + equipe.getBlocksPorte().size() + ")");
                    event.setCancelled(true);
                    return;
                } else {
                    equipe.getBlocksPorte().add(clickedBloc);
                    joueur.sendMessage("[" + equipe.getCouleur() + equipe.getNomEquipe() + ChatColor.RESET + "]" + ChatColor.GREEN + "+ Bloc ajouté (nb_bloc: " + equipe.getBlocksPorte().size() + ")");
                    event.setCancelled(true);
                    return;
                }
            }
        }


    }

    @Override
    public String getNom() {
        return "Définir les portes";
    }

    @Override
    public String getDescription() {
        return "Définir les portes de l'équipe";
    }

    @Override
    public Material getIcone() {
        return Material.IRON_DOOR;
    }

    public ItemStack getItemAjoutPorte() {
        ItemStack item_ajout = new ItemStack(Material.IRON_DOOR);
        ItemMeta meta = item_ajout.getItemMeta();
        meta.setDisplayName("Ajouter un bloc de porte " + equipe.getCouleur() + equipe.getNomEquipe());

        List<String> description = new LinkedList<>();
        description.add("Cliquez (CLIC GAUCHE) sur un bloc de porte pour l'ajouter au système de porte");
        description.add("Cliquez (CLIC GAUCHE) à nouveau sur un bloc pour le supprimer");

        meta.setLore(description);

        item_ajout.setItemMeta(meta);
        return item_ajout;
    }



}
