package fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe;

import fr.synchroneyes.mapbuilder.Entite.Equipe;
import fr.synchroneyes.mapbuilder.Entite.Item.Menu;
import fr.synchroneyes.mapbuilder.Entite.Item.Menus.Equipe.Modifier.MenuModifierEquipe;
import fr.synchroneyes.mapbuilder.Main;
import org.bukkit.Material;

public class MenuSelectionEquipe extends Menu {

    public MenuSelectionEquipe() {
        // On ajoute les équipes existantes

    }

    @Override
    public void onMenuLoaded() {
        // On ajoute les équipes
        clear();
        for(Equipe equipe : Main.getInstance().getTeamManager().getEquipes())
            ajouterElement(new MenuModifierEquipe(equipe));
    }

    @Override
    public String getNom() {
        return "Gestion d'équipes";
    }

    @Override
    public String getDescription() {
        return "Permet de gérer les équipes";
    }

    @Override
    public Material getIcone() {
        return Material.APPLE;
    }

    @Override
    public int getNombreLigne() {
        return 2;
    }
}
