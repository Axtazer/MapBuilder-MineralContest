package fr.synchroneyes.mapbuilder.Entite.Item.Menus;

import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.MenuCreerEquipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.MenuSelectionEquipe;
import org.bukkit.Material;

public class MenuEquipe extends Menu {

    public MenuEquipe() {
        ajouterElement(new MenuCreerEquipe());
        ajouterElement(new MenuSelectionEquipe());
    }

    @Override
    public void onMenuLoaded() {
        // nothing
    }

    @Override
    public String getNom() {
        return "Menu d'équipe";
    }

    @Override
    public String getDescription() {
        return "Menu permettant de créer une équipe et de gérer les options d'une équipe";
    }

    @Override
    public Material getIcone() {
        return Material.PLAYER_HEAD;
    }

    @Override
    public int getNombreLigne() {
        return 1;
    }
}
