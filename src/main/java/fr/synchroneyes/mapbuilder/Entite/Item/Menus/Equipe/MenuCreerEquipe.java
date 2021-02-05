package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe;

import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

public class MenuCreerEquipe extends Menu {

    public MenuCreerEquipe() {
        super();
        this.ajouterElement(new ItemCreerEquipe(ChatColor.BLUE));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.RED));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.YELLOW));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.GOLD));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.AQUA));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.BLACK));
        this.ajouterElement(new ItemCreerEquipe(ChatColor.GREEN));


    }

    @Override
    public void onMenuLoaded() {
        // rien à faire
    }

    @Override
    public String getNom() {
        return "Créer une équipe";
    }

    @Override
    public String getDescription() {
        return "Menu permettant d'ajouter une nouvelle équipe";
    }

    @Override
    public Material getIcone() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public int getNombreLigne() {
        return 1;
    }
}
